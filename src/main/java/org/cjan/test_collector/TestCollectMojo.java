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

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.surefire.report.ReportTestSuite;
import org.apache.maven.plugins.surefire.report.SurefireReportParser;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.MavenReportException;
import org.apache.maven.settings.Server;
import org.apache.maven.settings.Settings;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Collects test results to upload to CJAN.org.
 *
 * @author Bruno P. Kinoshita
 * @since 0.1
 */
@Mojo(name = "upload")
public class TestCollectMojo extends AbstractMojo {

    @Parameter(defaultValue = "http://cjan.org/upload/results", property = "cjan.url", required = false)
    private String url;

    @Parameter(property = "cjan.proxy.host", required = false)
    private String host;

    @Parameter(property = "cjan.proxy.port", required = false)
    private String port;

    @Parameter(defaultValue = "${project.build.directory}/surefire-reports", property = "cjan.reports", required = true)
    private File reports;

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    MavenProject project;

    // See also SettingsDecrypter
    @Component
    Settings settings;
    /*
     * (non-Javadoc)
     * 
     * @see org.apache.maven.plugin.AbstractMojo#execute()
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Collecting tests for CJAN.org...");

        if (!reports.exists() || !reports.isDirectory()) {
            throw new MojoExecutionException(String.format("Invalid reports directory [%s]", reports));
        }

        final Server server = settings.getServer("cjan");
        if (null == server) {
            throw new MojoExecutionException("Missing CJAN server");
        }

        String token = server.getUsername();
        if (null == token || "".equals(token.trim())) {
            throw new MojoExecutionException("Missing CJAN token. Create a <server> and set your token in <username>");
        }
        
        // parse results
        Locale lc = Locale.getDefault();
        getLog().debug("Locale set: " + lc);

        SurefireReportParser parser = new SurefireReportParser(Arrays.asList(reports), lc);
        final TestResults testResults;
        getLog().debug("Parsing results...");
        // Result flag, true is all good, false is failures/bad.
        try {
            final List<ReportTestSuite> testSuites = parser.parseXMLReportFiles();
            // get summary and show to user!
            getLog().info(String.format("%d test suites found!", testSuites.size()));
            testResults = new TestResults(testSuites);
            getLog().info(String.format("%d tests found!", testResults.getNumberOfTests()));
            getLog().debug("General status found: " + testResults.getGeneralStatus());
        } catch (MavenReportException e) {
            throw new MojoExecutionException("Failed to parse test reports: " + e.getMessage(), e);
        }

        // get environment properties
        EnvironmentProperties envProps = Utils.getEnvironmentProperties();
        getLog().debug(envProps.toString());

        // get project information
        String groupId = project.getGroupId();
        String artifactId = project.getArtifactId();
        String version = project.getVersion();
        getLog().debug(
                String.format("Project info: [groupId => %s] [artifactId => %s] [version => %s]", groupId, artifactId,
                        version));

        getLog().debug(String.format("Creating uploader to %s", url));
        getLog().debug(String.format("Proxy settings: [%s]:[%s]", host, port));
        Uploader uploader = new Uploader(url, host, port, token);

        final ObjectMapper mapper = new ObjectMapper();
        String response = "";
        try {
            response = uploader.upload(groupId, artifactId, version, envProps, testResults);
            ServerResponse serverResponse = mapper.readValue(response, ServerResponse.class);
            getLog().debug(String.format("Server response: %s", response));
            
            getLog().info(serverResponse.getMessage());
        } catch (UploadException ue) {
            getLog().info(String.format("Failed upload tests to CJAN.org: %s", response), ue);
            throw new MojoExecutionException("Failed uploading test results: " + ue.getMessage(), ue);
        } catch (JsonParseException e) {
            throw new MojoExecutionException(String.format("Failed to parse server response: %s", response), e);
        } catch (IOException e) {
            throw new MojoExecutionException(String.format("IO Error parsing server response: %s", response), e);
        }
    }

}
