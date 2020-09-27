/**
 * 
 */
package com.sjlh.hotel.common.exception;

/**
 * @author user
 *
 */
public class MethodNotSupportException extends BusinessException {
	private static final long serialVersionUID = -7534440368078907965L;

	public MethodNotSupportException() {
		super();
	}

	public MethodNotSupportException(String message, String code) {
		super(message, code);
	}

	public MethodNotSupportException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MethodNotSupportException(String message, Throwable cause) {
		super(message, cause);
	}

	public MethodNotSupportException(String message) {
		super(message);
	}

	public MethodNotSupportException(Throwable cause) {
		super(cause);
	}
}