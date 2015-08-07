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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugins.surefire.report.ReportTestCase;
import org.apache.maven.plugins.surefire.report.ReportTestSuite;

/**
 * Represents test results, with tests.
 *
 * @author Bruno P. Kinoshita
 * @since 0.2
 */
public class TestResults {

    /**
     * Immutable list of tests, created with a new TestResults.
     */
    private final List<Test> tests = new ArrayList<Test>();

    /**
     * General status, representing a unique result status for a list of tests.
     */
    private final Status generalStatus;

    /**
     * Create a TestResults.
     *
     * @param testSuites list of test suites
     */
    public TestResults(List<ReportTestSuite> testSuites) {
        boolean result = true;
        for (ReportTestSuite suite : testSuites) {
            if (suite.getNumberOfFailures() > 0) {
                result = false;
            }
            for (ReportTestCase tc : suite.getTestCases()) {
                // TBD: investigate where is the SKIP status
                Status status = Status.SUCCESS;
                Map<String, Object> failure = tc.getFailure();
                if (failure != null && failure.size() > 0) {
                    status = Status.FAILURE;
                }
                // TBD: fix metadata
                final String metadata = tc.getFullName();
                final Test test = new Test(tc.getName(), status, metadata);
                tests.add(test);
            }
        }
        generalStatus = result == true ? Status.SUCCESS : Status.FAILURE;
    }

    /**
     * Get the tests.
     *
     * @return list of {@link Test}s
     */
    public List<Test> getTests() {
        return new ArrayList<Test>(tests);
    }

    /**
     * Return the number of tests, without creating an extra copy of the list of tests.
     *
     * @return number of tests
     */
    public int getNumberOfTests() {
        return tests.size();
    }

    /**
     * Get the general status. FAILURE iff one or more tests failed.
     *
     * @return general {@link Status}
     */
    public Status getGeneralStatus() {
        return this.generalStatus;
    }

}
