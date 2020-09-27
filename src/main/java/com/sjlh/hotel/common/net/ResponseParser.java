/**
 * 
 */
package com.sjlh.hotel.common.net;

import javax.ws.rs.core.Response;

/**
 * @author user
 *
 */
public interface ResponseParser<T> {
	T parse(Response response);
}