package org.cjan.test_collector;
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.util.Locale;

import org.codehaus.plexus.util.Os;

/**
 * Based on Maven's CliReportingUtils.
 */
public class Utils {

	public static EnvironmentProperties getEnvironmentProperties()
    {
        EnvironmentProperties version = new EnvironmentProperties();
        version.setJavaVersion(System.getProperty("java.version", "<unknown java version>"));
        version.setJavaVendor(System.getProperty("java.vendor", "<unknown vendor>"));
        version.setLocale(Locale.getDefault().toString());
        version.setPlatformEncoding(System.getProperty("file.encoding", "<unknown encoding>"));
        version.setOsName(Os.OS_NAME.toString());
        version.setOsVersion(Os.OS_VERSION.toString());
        version.setOsArch(Os.OS_ARCH.toString());
        version.setOsFamily(Os.OS_FAMILY.toString());
        return version;
    }
	
}
