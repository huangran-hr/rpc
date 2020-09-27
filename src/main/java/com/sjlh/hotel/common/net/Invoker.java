package com.sjlh.hotel.common.net;

public interface Invoker {
	<T> T invoke(InvokerParam<T> param);

	<T, R> R invokeForm(InvokerParam<T> param, ResponseParser<R> parser);

	<T> T invokeForm(InvokerParam<T> param);
}