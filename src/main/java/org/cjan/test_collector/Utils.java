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

import java.util.Locale;
import java.util.TimeZone;

import org.codehaus.plexus.util.Os;

/**
 * Based on Maven's CliReportingUtils.
 *
 * @author Bruno P. Kinoshita
 * @since 0.1
 */
public class Utils {

    public static EnvironmentProperties getEnvironmentProperties() {
        EnvironmentProperties envVars = new EnvironmentProperties();
        envVars.setJavaVersion(System.getProperty("java.version", "<unknown java version>"));
        envVars.setJavaVendor(System.getProperty("java.vendor", "<unknown vendor>"));
        envVars.setLocale(Locale.getDefault().toString());
        envVars.setTimezone(TimeZone.getDefault().getID());
        envVars.setPlatformEncoding(System.getProperty("file.encoding", "<unknown encoding>"));
        envVars.setOsName(Os.OS_NAME.toString());
        envVars.setOsVersion(Os.OS_VERSION.toString());
        envVars.setOsArch(Os.OS_ARCH.toString());
        envVars.setOsFamily(Os.OS_FAMILY.toString());
        return envVars;
    }

}
