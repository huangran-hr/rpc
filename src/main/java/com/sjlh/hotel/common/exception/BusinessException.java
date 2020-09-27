/**
 * 
 */
package com.sjlh.hotel.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author user
 *
 */
@Setter
@Getter
public class BusinessException extends RuntimeException{
	private static final long serialVersionUID = -2061849857414539308L;
	private String code;

	public BusinessException() {
		super();
	}

	public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}
	
	public BusinessException(String code, String message) {
		this(message);
		this.code = code;
	}
}