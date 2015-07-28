/**
 * 
 */
package org.cjan.test_collector;

/**
 * @author Bruno P. Kinoshita
 * @since 0.1
 */
public class UploadException extends Exception {

	private static final long serialVersionUID = 8436938574075943349L;

	/**
	 * 
	 */
	public UploadException() {
	}

	/**
	 * @param message
	 */
	public UploadException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public UploadException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UploadException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public UploadException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
