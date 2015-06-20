/*
 * TODO: MIT License header
 */
package org.cjan.test_collector;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.surefire.report.ReportTestSuite;
import org.apache.maven.plugins.surefire.report.SurefireReportParser;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.plexus.util.DirectoryScanner;

/**
 * Collects test results to upload to CJAN.org.
 */
@Mojo(name="upload")
public class TestCollectMojo extends AbstractMojo {

    @Parameter(defaultValue="${project.build.directory}/target/surefire-reports", property="reports", required=true)
    private File reportsDirectory;

    /*
     * (non-Javadoc)
     * @see org.apache.maven.plugin.AbstractMojo#execute()
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Collecting tests for CJAN.org...");

        if (!reportsDirectory.exists() || !reportsDirectory.isDirectory()) {
            throw new MojoExecutionException(String.format("Invalid reports directory [%s]", reportsDirectory));
        }
        // parse results
        getLog().debug("Parsing results...");
        Locale locale = Locale.getDefault();
        SurefireReportParser parser = new SurefireReportParser(Arrays.asList(reportsDirectory), locale);
        final List<ReportTestSuite> testSuites;
        try {
			testSuites = parser.parseXMLReportFiles();
		} catch (MavenReportException e) {
			throw new MojoExecutionException("Failed to parse test reports: " + e.getMessage(), e);
		}
        // TODO upload results
        // get summary and show to user!
        getLog().info(String.format("%d tests uploaded!", testSuites.size()));
    }

}
