package org.fgt.ws.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogInvocationHandler implements InvocationHandler{

	private static final Logger LOG = LoggerFactory.getLogger(LogInvocationHandler.class);
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		long start = System.currentTimeMillis();
		if(LOG.isDebugEnabled()){
			LOG.debug("Invoking method= {}(), with args= {}",method.toString(),Arrays.toString(args));
		}
		try{
			return actualInvoke(method,args);
		}finally{
			if(LOG.isDebugEnabled()){
				long end = System.currentTimeMillis();
				LOG.debug("Method {}() took {}ms",Long.valueOf((end-start)));
			}			
		}
	}

	private Object actualInvoke(Method method, Object[] args) throws Throwable{
		return method.invoke(null, args);
	}

}
