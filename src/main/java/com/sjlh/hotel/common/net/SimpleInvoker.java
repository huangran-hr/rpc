/**
 * 
 */
package com.sjlh.hotel.common.net;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.jackson.JacksonFeature;

import com.sjlh.hotel.common.annotation.DIPrimary;
import com.sjlh.hotel.common.exception.MethodNotSupportException;
import com.sjlh.hotel.common.exception.ReturnTypeNotDefinedException;
import com.sjlh.hotel.common.logging.LoggingFeature;

/**
 * @author user
 *
 */
@DIPrimary
public class SimpleInvoker implements Invoker {
	private Client client;
	@Resource
	private InvokerParamBuilder invokerParamBuilder;
	
	@PostConstruct
	public void init() {
		if(client!=null)return;
		ClientBuilder builder = ClientBuilder.newBuilder();
		initSSLContext(builder);
		client = builder.build();
		initClient(client);
	}
	
	protected void initClient(Client client) {
		client.register(JacksonFeature.class);
		client.register(LoggingFeature.class);
	}
	
	protected void initSSLContext(ClientBuilder builder) {
		try {
		SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
		builder.sslContext(sslContext);
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}
			
			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
		};
		HostnameVerifier verifier = new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		builder.hostnameVerifier(verifier);
		sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void setClient(Client client) {
		this.client = client;
	}
	
	@Override
	public <T> T invokeForm(InvokerParam<T> param) {
		return invokeForm(param,response->{
				GenericType<T> generic = param.getGenericType();
				Class<T> clazz = param.getClazz();
				if(generic!=null)
					return response.readEntity(generic);
				if(clazz!=null)
					return response.readEntity(clazz);
				return null;
			}
		);
	}
	
	@Override
	public <T, R> R invokeForm(InvokerParam<T> param, ResponseParser<R> parser) {
		//initTarget(param);
		String target = param.getTarget();
		String method = param.getMethod().toUpperCase();
		if(HttpMethod.GET.equals(method) || HttpMethod.OPTIONS.equals(method) || HttpMethod.HEAD.equals(method) || HttpMethod.DELETE.equals(method)) {
			target = buildQuery(target, param.getParam());
		}
		Builder builder = client.target(buildTarget(target, param.getFormat())).request(param.getRequestMediaType()).accept(param.getAcceptMediaType());
		Response response = null;
		if(HttpMethod.GET.equals(method)) {
			response = builder.get();
		}else if(HttpMethod.POST.equals(method)) {
			Form form = buildForm(param.getParam());
			response = builder.post(Entity.form(form));
		}else if(HttpMethod.HEAD.equals(method)) {
			response = builder.head();
		}else if(HttpMethod.OPTIONS.equals(method)) {
			response = builder.options();
		}else if(HttpMethod.PATCH.equals(method)) {
			throw new MethodNotSupportException(HttpMethod.PATCH + " not supported");
		}else if(HttpMethod.PUT.equals(method)) {
			Form form = buildForm(param.getParam());
			response = builder.put(Entity.form(form));
		}else if(HttpMethod.DELETE.equals(method)){
			response = builder.delete();
		}else {
			throw new MethodNotSupportException(method + " not supported");
		}
		if(parser!=null) {
			R r = parser.parse(response);
			return r;
		}
		return null;
	}
	
	@Override
	public <T> T invoke(InvokerParam<T> param) {
		initTarget(param);
		String target = param.getTarget();
		Builder builder = client.target(buildTarget(target, param.getFormat())).request(param.getRequestMediaType()).accept(param.getAcceptMediaType());
		String method = param.getMethod().toUpperCase();
		GenericType<T> generic = param.getGenericType();
		Class<T> clazz = param.getClazz();
		if(generic==null && clazz==null)throw new ReturnTypeNotDefinedException("generic or clazz is not defined");
		build(param.getParam());
		Entity<?> entity = Entity.entity(param.getParam(), param.getRequestMediaType());
		if(HttpMethod.GET.equals(method)) {
			if(generic!=null)
				return builder.get(generic);
			else if(clazz!=null)
				return builder.get(clazz);
		}else if(HttpMethod.POST.equals(method)) {
			if(generic!=null) {
				return builder.post(entity, generic);
			}else if(clazz!=null) {
				return builder.post(entity, clazz);
			}
		}else if(HttpMethod.DELETE.equals(method))
			if(generic!=null) {
				return builder.delete(generic);
			}else if(clazz!=null) {
				return builder.delete(clazz);
		}else if(HttpMethod.PUT.equals(method)) {
			if(generic!=null) {
				return builder.put(entity, generic);
			}else if(clazz!=null) {
				return builder.put(entity, clazz);
			}
		}else {
			throw new MethodNotSupportException(method + " not supported");
		}
		return null;
	}
	
	private String buildQuery(String target, Object param) {
		StringBuilder builder = new StringBuilder();
		if(!(target==null || target.trim().equals("")))builder.append(target);
		builder.append(buildQuery(param));
		return builder.toString();
	}
	
	private StringBuilder buildQuery(Object param) {
		List<KeyValue> list = buildKeyValues(param);
		StringBuilder builder = new StringBuilder();
		list.stream().forEach((k)->{if(builder.length()>0)builder.append("&");builder.append(k.key).append("=").append(k.value);});
		if(builder.length()>0)builder.insert(0, "?");
		return builder;
	}

	private Form buildForm(Object param) {
		Form form = new Form();
		List<KeyValue> list = buildKeyValues(param);
		list.stream().forEach((k)->form.param(k.key, k.value));
		return form;
	}
	
	private List<KeyValue> buildKeyValues(Object param){
		List<KeyValue> list = new ArrayList<>();
		if(param==null)return list;
		if(Map.class.isAssignableFrom(param.getClass())) {
			Map<?, ?> map = (Map<?, ?>)param;
			Iterator<?> ite = map.keySet().iterator();
			while(ite.hasNext()) {
				Object key = ite.next();
				Object v = map.get(key);
				if(v==null)continue;
				list.add(new KeyValue(key.toString(), v.toString()));
			}
		}else if(param.getClass().isArray())return list;
		else{
			Method[] methods = param.getClass().getDeclaredMethods();
			for(Method m: methods) {
				String methodName = m.getName();
				if(methodName.startsWith("get") && methodName.length()>3) {
					String key = methodName.substring(3);
					key = Character.toLowerCase(key.charAt(0))+key.substring(1);
					String value = null;
					try {
						Object v = m.invoke(param, new Object[] {});
						if(v!=null) {
							if(v.getClass().isPrimitive()) {
								value = String.valueOf(v);
							}else {
								value = v.toString();
							}
						}
						if(value!=null) {
							list.add(new KeyValue(key, value));
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return list;
	}
	
	protected void build(Object param) {
		//
	}
	
	protected String buildTarget(String target, String format) {
		return target;
	}
	
	protected <T> void initTarget(InvokerParam<T> param) {
		//
	}

	protected String getMediaType(String format) {
		String mediaType = MediaType.APPLICATION_JSON;
		if(format!=null) {
			if(format.toLowerCase().equals("xml")) {
				mediaType=MediaType.APPLICATION_XML;
			}
		}
		return mediaType;
	}
	
	class KeyValue{
		String key;
		String value;
		
		KeyValue(String key, String value){
			this.key = key;
			this.value = value;
		}
	}
}