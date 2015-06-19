/*
 * TODO: MIT License header
 */
package org.cjan.test_collector;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Collects test results to upload to CJAN.org.
 */
@Mojo(name="upload")
public class TestCollectMojo extends AbstractMojo {

	@Parameter(defaultValue="${project.build.directory}", property="reports", required=true)
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
		// TODO get reports directory
		// TODO parse results
		// TODO upload results
		// TODO get summary and show to user!
		getLog().info(String.format("%d tests uploaded!", 0));
	}

}
