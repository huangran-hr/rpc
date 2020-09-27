/**
 * 
 */
package com.sjlh.hotel.common.net;

import javax.ws.rs.core.GenericType;

import lombok.Getter;

/**
 * @author user
 *
 */
@Getter
public class InvokerParam<T> {
	private String target;
	private String requestMediaType;
	private String acceptMediaType;
	private String format;
	private String method;
	private Object param;
	private Class<T> clazz;
	private GenericType<T> genericType;

	public InvokerParam<T> setTarget(String target) {
		this.target = target;
		return this;
	}

	public InvokerParam<T> setRequestMediaType(String requestMediaType) {
		this.requestMediaType = requestMediaType;
		return this;
	}

	public InvokerParam<T> setAcceptMediaType(String acceptMediaType) {
		this.acceptMediaType = acceptMediaType;
		return this;
	}

	public InvokerParam<T> setFormat(String format) {
		this.format = format;
		return this;
	}

	public InvokerParam<T> setMethod(String method) {
		this.method = method;
		return this;
	}

	public InvokerParam<T> setParam(Object param) {
		this.param = param;
		return this;
	}

	public InvokerParam<T> setClazz(Class<T> clazz) {
		this.clazz = clazz;
		return this;
	}

	public InvokerParam<T> setGenericType(GenericType<T> genericType) {
		this.genericType = genericType;
		return this;
	}
}