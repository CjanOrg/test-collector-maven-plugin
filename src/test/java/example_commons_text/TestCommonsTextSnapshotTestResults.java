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
package example_commons_text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.maven.plugins.surefire.report.ReportTestSuite;
import org.apache.maven.plugins.surefire.report.SurefireReportParser;
import org.apache.maven.reporting.MavenReportException;
import org.cjan.test_collector.Status;
import org.cjan.test_collector.TestResults;
import org.junit.Test;

/**
 * Tests general status for test results.
 *
 * @author Bruno P. Kinoshita
 * @since 0.2
 */
public class TestCommonsTextSnapshotTestResults {

    @Test
	public void testGeneralTestResult() throws IOException, MavenReportException {
		File reportsDirectory = new File(TestCommonsTextSnapshotTestResults.class.getResource(".").getFile());
		Locale lc = Locale.getDefault();
		SurefireReportParser parser = new SurefireReportParser(Arrays.asList(reportsDirectory), lc);
		List<ReportTestSuite> testSuites = parser.parseXMLReportFiles();
		TestResults testResults = new TestResults(testSuites);
		
		assertEquals("Wrong general status for test results.", Status.SUCCESS, testResults.getGeneralStatus());
		
		assertTrue("Wrong number of tests", testResults.getTests().size() > 0);
	}

}
