package org.cjan.test_collector;


/**
 * Environment properties.
 *
 * @see org.apache.maven.cli.MavenCli
 * @see org.apache.maven.cli.CliRequest
 * @see org.apache.maven.cli.CliReportingUtils
 */
public class EnvironmentProperties {

	private String javaVersion;
	private String javaVendor;
	private String locale;
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
		result = prime * result
				+ ((javaVendor == null) ? 0 : javaVendor.hashCode());
		result = prime * result
				+ ((javaVersion == null) ? 0 : javaVersion.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((osArch == null) ? 0 : osArch.hashCode());
		result = prime * result
				+ ((osFamily == null) ? 0 : osFamily.hashCode());
		result = prime * result + ((osName == null) ? 0 : osName.hashCode());
		result = prime * result
				+ ((osVersion == null) ? 0 : osVersion.hashCode());
		result = prime
				* result
				+ ((platformEncoding == null) ? 0 : platformEncoding.hashCode());
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
		return true;
	}

	@Override
	public String toString() {
		return "EnvironmentProperties [javaVersion=" + javaVersion + ", javaVendor=" + javaVendor
				+ ", locale=" + locale + ", platformEncoding="
				+ platformEncoding + ", osName=" + osName + ", osVersion="
				+ osVersion + ", osArch=" + osArch + ", osFamily=" + osFamily
				+ "]";
	}

}
