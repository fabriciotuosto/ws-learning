package org.fgt.ws.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

public class CacheInvocationHandler implements InvocationHandler{

	private final Cache cache;
	private final Object instance;

	public CacheInvocationHandler(Cache cache,Object instance) {
		this.cache = cache;
		this.instance = instance;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = retrieveFromCache(method,args);
		if(result == null){
			result = invokeAndCache(method, args); 
		}
		return result;
	}
	
	private Object invokeAndCache(Method method,Object[] args) throws Throwable{
		Object result = method.invoke(instance, args);
		Element element = new Element("", result);
		cache.put(element);
		return result;
	}
	
	private Object retrieveFromCache(Method method, Object[] args) {
		String key = "";
		return cache.get(key);
	}

}
