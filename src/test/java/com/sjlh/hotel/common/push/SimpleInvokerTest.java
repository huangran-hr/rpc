/**
 * 
 */
package com.sjlh.hotel.common.push;

import static org.junit.Assert.*;

import java.util.ServiceLoader;

import org.junit.Test;

import com.sjlh.hotel.common.net.InvokerParam;
import com.sjlh.hotel.common.net.InvokerParamBuilder;
import com.sjlh.hotel.common.net.SimpleInvoker;

/**
 * @author Administrator
 *
 */
public class SimpleInvokerTest {
	//@Test
	public void test() {
		SimpleInvoker invoker = new SimpleInvoker();
		invoker.init();
		InvokerParamBuilder builder = new InvokerParamBuilder();
		InvokerParam<Person> param = builder.buildGet(Person.class);
		param.setTarget("http://localhost:6888/person/1");
		Person p = new Person();
		p.setId(1L);
		param.setParam(p);
		Person person = invoker.invoke(param);
		System.out.println(person);
		net.logstash.logback.appender.LogstashTcpSocketAppender a;
	}
	
	@Test
	public void testLoader() {
		ServiceLoader<InvokerParamBuilder> ipb = ServiceLoader.load(InvokerParamBuilder.class);
		InvokerParamBuilder builder = ipb.findFirst().get();
		System.out.println(ipb);
	}
}