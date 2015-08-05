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

/**
 * Environment properties.
 *
 * @see org.apache.maven.cli.MavenCli
 * @see org.apache.maven.cli.CliRequest
 * @see org.apache.maven.cli.CliReportingUtils
 * @author Bruno P. Kinoshita
 * @since 0.1
 */
public class EnvironmentProperties {

    private String javaVersion;
    private String javaVendor;
    private String locale;
    private String timezone;
    private String platformEncoding;
    private String osName;
    private String osVersion;
    private String osArch;
    private String osFamily;

    public EnvironmentProperties() {
        super();
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public String getJavaVendor() {
        return javaVendor;
    }

    public void setJavaVendor(String javaVendor) {
        this.javaVendor = javaVendor;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getPlatformEncoding() {
        return platformEncoding;
    }

    public void setPlatformEncoding(String platformEncoding) {
        this.platformEncoding = platformEncoding;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getOsArch() {
        return osArch;
    }

    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }

    public String getOsFamily() {
        return osFamily;
    }

    public void setOsFamily(String osFamily) {
        this.osFamily = osFamily;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((javaVendor == null) ? 0 : javaVendor.hashCode());
        result = prime * result + ((javaVersion == null) ? 0 : javaVersion.hashCode());
        result = prime * result + ((locale == null) ? 0 : locale.hashCode());
        result = prime * result + ((osArch == null) ? 0 : osArch.hashCode());
        result = prime * result + ((osFamily == null) ? 0 : osFamily.hashCode());
        result = prime * result + ((osName == null) ? 0 : osName.hashCode());
        result = prime * result + ((osVersion == null) ? 0 : osVersion.hashCode());
        result = prime * result + ((platformEncoding == null) ? 0 : platformEncoding.hashCode());
        result = prime * result + ((timezone == null) ? 0 : timezone.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EnvironmentProperties other = (EnvironmentProperties) obj;
        if (javaVendor == null) {
            if (other.javaVendor != null)
                return false;
        } else if (!javaVendor.equals(other.javaVendor))
            return false;
        if (javaVersion == null) {
            if (other.javaVersion != null)
                return false;
        } else if (!javaVersion.equals(other.javaVersion))
            return false;
        if (locale == null) {
            if (other.locale != null)
                return false;
        } else if (!locale.equals(other.locale))
            return false;
        if (osArch == null) {
            if (other.osArch != null)
                return false;
        } else if (!osArch.equals(other.osArch))
            return false;
        if (osFamily == null) {
            if (other.osFamily != null)
                return false;
        } else if (!osFamily.equals(other.osFamily))
            return false;
        if (osName == null) {
            if (other.osName != null)
                return false;
        } else if (!osName.equals(other.osName))
            return false;
        if (osVersion == null) {
            if (other.osVersion != null)
                return false;
        } else if (!osVersion.equals(other.osVersion))
            return false;
        if (platformEncoding == null) {
            if (other.platformEncoding != null)
                return false;
        } else if (!platformEncoding.equals(other.platformEncoding))
            return false;
        if (timezone == null) {
            if (other.timezone != null)
                return false;
        } else if (!timezone.equals(other.timezone))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "EnvironmentProperties [javaVersion=" + javaVersion + ", javaVendor=" + javaVendor + ", locale="
                + locale + ", timezone=" + timezone + ", platformEncoding=" + platformEncoding + ", osName=" + osName
                + ", osVersion=" + osVersion + ", osArch=" + osArch + ", osFamily=" + osFamily + "]";
    }

}
