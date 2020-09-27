/**
 * 
 */
package com.sjlh.hotel.common.net;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 * @author user
 *
 */
public class InvokerParamBuilder {
	static public InvokerParamBuilder newInstance() {
		return new InvokerParamBuilder();
	}
	
	public <T> InvokerParam<T> build(Class<T> clazz) {
		InvokerParam<T> param = build();
		param.setClazz(clazz);
		return param;
	}
	
	public <T> InvokerParam<T> build(GenericType<T> generic){
		InvokerParam<T> param = build();
		param.setGenericType(generic);
		return param;
	}
	
	public <T> InvokerParam<T> build(Class<T> clazz, String method) {
		InvokerParam<T> param = build();
		param.setClazz(clazz);
		param.setMethod(method);
		return param;
	}
	
	public <T> InvokerParam<T> build(GenericType<T> generic, String method){
		InvokerParam<T> param = build();
		param.setGenericType(generic);
		param.setMethod(method);
		return param;
	}
	
	public <T> InvokerParam<T> buildPost(Class<T> clazz){
		return build(clazz, HttpMethod.POST);
	}
	
	public <T> InvokerParam<T> buildPost(GenericType<T> generic){
		return build(generic, HttpMethod.POST);
	}
	
	public <T> InvokerParam<T> buildPut(Class<T> clazz){
		return build(clazz, HttpMethod.PUT);
	}
	
	public <T> InvokerParam<T> buildPut(GenericType<T> generic){
		return build(generic, HttpMethod.PUT);
	}
	
	public <T> InvokerParam<T> buildDelete(Class<T> clazz){
		return build(clazz, HttpMethod.DELETE);
	}
	
	public <T> InvokerParam<T> buildDelete(GenericType<T> generic){
		return build(generic, HttpMethod.DELETE);
	}
	
	public <T> InvokerParam<T> buildGet(Class<T> clazz){
		return build(clazz, HttpMethod.GET);
	}
	
	public <T> InvokerParam<T> buildGet(GenericType<T> generic){
		return build(generic, HttpMethod.GET);
	}
	
	public <T> InvokerParam<T> buildPatch(Class<T> clazz){
		return build(clazz, HttpMethod.PATCH);
	}
	
	public <T> InvokerParam<T> buildPatch(GenericType<T> generic){
		return build(generic, HttpMethod.PATCH);
	}
	
	public <T> InvokerParam<T> build(){
		InvokerParam<T> param = new InvokerParam<>();
		param.setAcceptMediaType(MediaType.APPLICATION_JSON);
		param.setRequestMediaType(MediaType.APPLICATION_JSON);
		param.setFormat("json");
		param.setMethod(HttpMethod.POST);
		return param;
	}
	public <T> InvokerParam<T> buildForm(Class<T> clazz) {
		InvokerParam<T> param = build();
		param.setClazz(clazz);
		return param;
	}
	
	public <T> InvokerParam<T> buildForm(GenericType<T> generic){
		InvokerParam<T> param = build();
		param.setGenericType(generic);
		return param;
	}
	
	public <T> InvokerParam<T> buildForm(Class<T> clazz, String method) {
		InvokerParam<T> param = build();
		param.setClazz(clazz);
		param.setMethod(method);
		return param;
	}
	
	public <T> InvokerParam<T> buildForm(GenericType<T> generic, String method){
		InvokerParam<T> param = build();
		param.setGenericType(generic);
		param.setMethod(method);
		return param;
	}
	
	public <T> InvokerParam<T> buildFormPost(Class<T> clazz){
		return buildForm(clazz, HttpMethod.POST);
	}
	
	public <T> InvokerParam<T> buildFormPost(GenericType<T> generic){
		return buildForm(generic, HttpMethod.POST);
	}
	
	public <T> InvokerParam<T> buildFormPut(Class<T> clazz){
		return buildForm(clazz, HttpMethod.PUT);
	}
	
	public <T> InvokerParam<T> buildFormPut(GenericType<T> generic){
		return buildForm(generic, HttpMethod.PUT);
	}
	
	public <T> InvokerParam<T> buildFormDelete(Class<T> clazz){
		return buildForm(clazz, HttpMethod.DELETE);
	}
	
	public <T> InvokerParam<T> buildFormDelete(GenericType<T> generic){
		return buildForm(generic, HttpMethod.DELETE);
	}
	
	public <T> InvokerParam<T> buildFormGet(Class<T> clazz){
		return buildForm(clazz, HttpMethod.GET);
	}
	
	public <T> InvokerParam<T> buildFormGet(GenericType<T> generic){
		return buildForm(generic, HttpMethod.GET);
	}
	
	public <T> InvokerParam<T> buildFormPatch(Class<T> clazz){
		return buildForm(clazz, HttpMethod.PATCH);
	}
	
	public <T> InvokerParam<T> buildFormPatch(GenericType<T> generic){
		return buildForm(generic, HttpMethod.PATCH);
	}
	
	public <T> InvokerParam<T> buildForm(){
		InvokerParam<T> param = build();
		param.setRequestMediaType(MediaType.TEXT_PLAIN);
		return param;
	}
	
	public <T> InvokerParam<T> buildForm(String method){
		InvokerParam<T> param = build();
		param.setRequestMediaType(MediaType.TEXT_PLAIN);
		param.setMethod(method);
		return param;
	}
	
	public <T> InvokerParam<T> buildFormGet(){
		return buildForm(HttpMethod.GET);
	}
	
	public <T> InvokerParam<T> buildFormPost(){
		return buildForm(HttpMethod.POST);
	}
	
	public <T> InvokerParam<T> buildFormPut(){
		return buildForm(HttpMethod.PUT);
	}
	
	public <T> InvokerParam<T> buildFormDelete(){
		return buildForm(HttpMethod.DELETE);
	}
	
	public <T> InvokerParam<T> buildFormPatch(){
		return buildForm(HttpMethod.PATCH);
	}
}