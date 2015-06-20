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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.surefire.report.ReportTestSuite;
import org.apache.maven.plugins.surefire.report.SurefireReportParser;
import org.apache.maven.reporting.MavenReportException;

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
