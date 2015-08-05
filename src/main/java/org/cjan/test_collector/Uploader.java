/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 Bruno P. Kinoshita
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.cjan.test_collector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.maven.plugins.surefire.report.ReportTestCase;
import org.apache.maven.plugins.surefire.report.ReportTestSuite;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * <p>
 * Uploads results to CJAN.org.
 * </p>
 *
 * <p>
 * Results are uploaded as JSON, via a HTTP POST.
 * </p>
 *
 * @author Bruno P. Kinoshita
 * @since 0.1
 */
public class Uploader {

    /**
     * URL used to upload results to CJAN.org.
     */
    private final String cjanUrl;
    /**
     * Proxy host.
     */
    private String proxyHost;
    /**
     * Proxy port.
     */
    private String proxyPort;
    /**
     * CJAN.org user access token.
     */
    private final String accessToken;

    private final static Logger LOGGER = Logger.getLogger(Uploader.class.getName());

    /**
     * Constructor with parameters.
     *
     * @param cjanUrl CJAN.org URL
     * @param proxyHost proxy host
     * @param proxyPort proxy port
     * @param proxyPort access token
     */
    public Uploader(String cjanUrl, String proxyHost, String proxyPort, String accessToken) {
        this.cjanUrl = cjanUrl;
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.accessToken = accessToken;
    }

    /**
     * Gets the URL.
     * 
     * @return the URL used to upload results
     */
    public String getUrl() {
        return cjanUrl;
    }

    /**
     * @return the proxyHost
     */
    public String getProxyHost() {
        return proxyHost;
    }

    /**
     * @param proxyHost the proxyHost to set
     */
    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    /**
     * @return the proxyPort
     */
    public String getProxyPort() {
        return proxyPort;
    }

    /**
     * @param proxyPort the proxyPort to set
     */
    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }

    /**
     * 
     * @return
     */
    public String getAccessToken() {
        return accessToken;
    }

    public boolean isProxyEnabled() {
        return getProxyHost() != null && getProxyHost().trim().equals("") == false && getProxyPort() != null
                && getProxyPort().trim().equals("") == false;
    }

    /**
     * Upload test run and tests to CJAN.org.
     *
     * @param groupId groupId
     * @param artifactId artifactId
     * @param version version
     * @param envProps environment properties
     * @param testSuites test suites
     * @throws UploadException if it fails to upload
     */
    public String upload(String groupId, String artifactId, String version, EnvironmentProperties envProps,
            List<ReportTestSuite> testSuites) throws UploadException {
        validate(groupId, "Missing groupId");
        validate(artifactId, "Missing artifactId");
        validate(version, "Missing version");
        validate(envProps, "Missing environment properties");
        validate(testSuites, "Empty test suite");

        LOGGER.info("Uploading test results to CJAN.org");

        boolean result = false;
        final List<Test> tests = new ArrayList<Test>(testSuites.size());
        for (ReportTestSuite suite : testSuites) {
            if (suite.getNumberOfFailures() > 0) {
                result = false;
            }
            for (ReportTestCase tc : suite.getTestCases()) {
                // TBD: investigate where is the SKIP status
                Status status = Status.SUCCESS;
                Map<String, Object> failure = tc.getFailure();
                if (failure != null) {
                    status = Status.FAILURE;
                }
                // TBD: fix metadata
                final String metadata = tc.getFullName();
                final Test test = new Test(tc.getName(), status, metadata);
                tests.add(test);
            }
        }

        final TestRun testRun = new TestRun(groupId, artifactId, version, envProps, result == true ? Status.SUCCESS
                : Status.FAILURE);
        testRun.addTests(tests);

        if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(Level.FINEST, "Test Run: " + testRun.toString());
        }

        final ObjectMapper mapper = new ObjectMapper();
        final String json;
        try {
            json = mapper.writeValueAsString(testRun);
            if (LOGGER.isLoggable(Level.FINEST)) {
                LOGGER.log(Level.FINEST, "Test Run: " + testRun.toString());
            }
        } catch (JsonGenerationException e) {
            throw new UploadException("Error converting test run to JSON: " + e.getMessage(), e);
        } catch (JsonMappingException e) {
            throw new UploadException("Error mapping object fields: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new UploadException("IO error: " + e.getMessage(), e);
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            RequestConfig config = null;
            if (isProxyEnabled()) {
                LOGGER.fine("Using HTTP proxy!");
                HttpHost proxy = new HttpHost(getProxyHost(), Integer.parseInt(getProxyPort()));
                config = RequestConfig.custom().setProxy(proxy).build();
            }

            HttpPost post = new HttpPost(getUrl());
            if (config != null) {
                post.setConfig(config);
            }
            // add access token to headers
            post.addHeader("x-access-token", getAccessToken());
            // post testrun, get ID back from server, show to user
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(1);
            pairs.add(new BasicNameValuePair("json", json));
            post.setEntity(new UrlEncodedFormEntity(pairs));
            CloseableHttpResponse response = httpclient.execute(post);
            try {
                HttpEntity entity = response.getEntity();
                String body = EntityUtils.toString(entity);
                return body;
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            throw new UploadException("Client protocol error: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new UploadException("IO error: " + e.getMessage(), e);
        } finally {
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    LOGGER.log(Level.WARNING, "Error closing HTTP client: " + e.getMessage(), e);
                }
            }
        }

    }

    /**
     * Validate parameters.
     *
     * @param value String parameter
     * @param message Message if parameter is null or empty
     */
    private void validate(String value, String message) {
        if (null == value || "".equals(value.trim())) {
            throw new RuntimeException("Validation error: " + message);
        }
    }

    /**
     * Validate objects.
     *
     * @param value Object parameter
     * @param message Message if parameter is null or empty
     */
    private void validate(Object value, String message) {
        if (null == value) {
            throw new RuntimeException("Validation error: " + message);
        }
    }

    /**
     * Validate collections.
     *
     * @param value Collection parameter
     * @param message Message if parameter is null or empty
     */
    private void validate(Collection<?> value, String message) {
        if (null == value || value.isEmpty()) {
            throw new RuntimeException("Validation error: " + message);
        }
    }

}
