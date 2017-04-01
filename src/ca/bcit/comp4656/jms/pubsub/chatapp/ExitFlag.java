/**
 * Project: lab10_ChatApp
 * File: ExitFlag.java
 * Date: Mar 17, 2017
 * Time: 2:47:43 PM
 */
package ca.bcit.comp4656.jms.pubsub.chatapp;

/**
 * @author Siamak Pourian
 *
 * ExitFlag Class - Application exception
 */
public class ExitFlag extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ExitFlag() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ExitFlag(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ExitFlag(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ExitFlag(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ExitFlag(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
