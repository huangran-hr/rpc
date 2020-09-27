/**
 * 
 */
package com.sjlh.hotel.common.net;

/**
 * @author user
 *
 */
public interface InvokerFactory {
	Invoker getInvoker(String name);
}