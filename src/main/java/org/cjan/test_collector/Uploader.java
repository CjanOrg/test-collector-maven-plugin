package org.cjan.test_collector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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

	// TODO: fix docs
	private final String cjanUrl;
	private final String accessToken;

	public Uploader(String cjanUrl, String accessToken) {
		// TBD: validate parameters
		this.cjanUrl = cjanUrl;
		this.accessToken = accessToken;
	}

	public String getUrl() {
		return cjanUrl;
	}

	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * Upload test run and tests to CJAN.org.
	 *
	 * @param groupId
	 *            groupId
	 * @param artifactId
	 *            artifactId
	 * @param version
	 *            version
	 * @param envProps
	 *            environment properties
	 * @param testSuites
	 *            test suites
	 * @throws UploadException
	 *             if it fails to upload
	 */
	public String upload(String groupId, String artifactId, String version,
			EnvironmentProperties envProps, List<ReportTestSuite> testSuites)
			throws UploadException {
		validate(groupId, "Missing groupId");
		validate(artifactId, "Missing artifactId");
		validate(version, "Missing version");
		validate(envProps, "Missing environment properties");
		validate(testSuites, "Empty test suite");

		boolean result = false;
		final List<Test> tests = new ArrayList<Test>(testSuites.size());
		for (ReportTestSuite suite : testSuites) {
			if (suite.getNumberOfFailures() > 0) {
				result = false;
			}
			for (ReportTestCase tc : suite.getTestCases()) {
				// TODO: investigate where is the SKIP status
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

		final TestRun testRun = new TestRun(groupId, artifactId, version,
				envProps, result == true ? Status.SUCCESS : Status.FAILURE);
		testRun.addTests(tests);

		ObjectMapper mapper = new ObjectMapper();
		String json;
		try {
			json = mapper.writeValueAsString(testRun);
		} catch (JsonGenerationException e) {
			throw new UploadException("Error converting test run to JSON: "
					+ e.getMessage(), e);
		} catch (JsonMappingException e) {
			throw new UploadException("Error mapping object fields: "
					+ e.getMessage(), e);
		} catch (IOException e) {
			throw new UploadException("IO error: " + e.getMessage(), e);
		}

		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {
//			HttpHost target = new HttpHost("localhost", 443, "https");
//			HttpHost proxy = new HttpHost("127.0.0.1", 8080, "http");
//			
//			RequestConfig config = RequestConfig.custom().setProxy(proxy)
//					.build();

			HttpPost post = new HttpPost(getUrl());
//			post.setConfig(config);
			// TODO: add access token to headers
			post.addHeader("token", getAccessToken());
			// TODO: post testrun, get ID back from server, show to user
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
					// TBD: log
					e.printStackTrace(System.err);
				}
			}
		}

	}

	/**
	 * Validate parameters.
	 *
	 * @param value
	 *            String parameter
	 * @param message
	 *            Message if parameter is null or empty
	 */
	private void validate(String value, String message) {
		if (null == value || "".equals(value.trim())) {
			throw new RuntimeException("Validation error: " + message);
		}
	}

	/**
	 * Validate objects.
	 *
	 * @param value
	 *            Object parameter
	 * @param message
	 *            Message if parameter is null or empty
	 */
	private void validate(Object value, String message) {
		if (null == value) {
			throw new RuntimeException("Validation error: " + message);
		}
	}

	/**
	 * Validate collections.
	 *
	 * @param value
	 *            Collection parameter
	 * @param message
	 *            Message if parameter is null or empty
	 */
	private void validate(Collection<?> value, String message) {
		if (null == value || value.isEmpty()) {
			throw new RuntimeException("Validation error: " + message);
		}
	}

}
