/**
 * 
 */
package com.sjlh.hotel.common.exception;

/**
 * @author user
 *
 */
public class ReturnTypeNotDefinedException extends BusinessException {
	private static final long serialVersionUID = -5956884298001960486L;

	public ReturnTypeNotDefinedException() {
		super();
	}

	public ReturnTypeNotDefinedException(String message, String code) {
		super(message, code);
	}

	public ReturnTypeNotDefinedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReturnTypeNotDefinedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReturnTypeNotDefinedException(String message) {
		super(message);
	}

	public ReturnTypeNotDefinedException(Throwable cause) {
		super(cause);
	}
}