/**
 * 
 */
package com.sjlh.hotel.common.net;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.sjlh.hotel.common.annotation.InvokerMeta;

/**
 * @author user
 *
 */
public class SimpleInvokerFactory implements InvokerFactory {
	private Map<String, Invoker> map;
	@Resource
	private List<Invoker> invokers;
	@Resource
	private Invoker defaultPushInvoker;
	
	public void setInvokers(List<Invoker> invokers) {
		this.invokers = invokers;
	}

	@PostConstruct
	public void init() {
		map = new HashMap<>();
		if(invokers==null)return;
		for(Invoker v: invokers) {
			InvokerMeta m = v.getClass().getAnnotation(InvokerMeta.class);
			if(m==null)continue;
			map.put(m.id(), v);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.shop.push.InvokerFactory#getInvoker(java.lang.String)
	 */
	@Override
	public Invoker getInvoker(String name) {
		return map.getOrDefault(name, defaultPushInvoker);
	}
}