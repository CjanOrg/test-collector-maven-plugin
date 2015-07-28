package org.cjan.test_collector;

import java.util.ArrayList;
import java.util.List;

public final class TestRun {

	private final String groupId;
	private final String artifactId;
	private final String version;

	private final EnvironmentProperties envProps;
	private final Status status;
	private final List<Test> tests;

	public TestRun(String groupId, String artifactId, String version,
			EnvironmentProperties envProps, Status status) {
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
		this.envProps = envProps;
		this.status = status;
		this.tests = new ArrayList<Test>();
	}

	public void addTests(List<Test> tests) {
		this.tests.addAll(tests);
	}

	public String getGroupId() {
		return groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public String getVersion() {
		return version;
	}

	public EnvironmentProperties getEnvProps() {
		return envProps;
	}

	public Status getStatus() {
		return status;
	}

	public List<Test> getTests() {
		return tests;
	}

}
