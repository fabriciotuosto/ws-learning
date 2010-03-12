package org.fgt.ws.client;

import java.lang.reflect.Method;
import java.util.Arrays;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheMethodInterceptor implements MethodInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(CacheMethodInterceptor.class);
    private final Cache cache;

    public CacheMethodInterceptor(Cache cache) {
       super();
       this.cache = Precondition.checkNotNull(cache);
    }

    private Object invokeAndCache(MethodInvocation methodInvocation) throws Throwable {
        Object value = methodInvocation.proceed();
        String key = generateCacheKey(methodInvocation);
        Element element = new Element(key, value);
        cache.put(element);
        return value;
    }

    private Object retrieveFromCache(MethodInvocation methodInvocation) {
        String key = generateCacheKey(methodInvocation);
        Element element = cache.get(key);
        return  element != null ? element.getValue() : null;
    }

    String generateCacheKey(MethodInvocation methodInvocation) {
        Object[] args = methodInvocation.getArguments();
        Method method = methodInvocation.getMethod();
        int hashCode = methodInvocation.getThis().hashCode();
        String key = String.format("%s.%s()[%s] %s",
                method.getDeclaringClass().getName(),
                method.getName(),
                hashCode,
                Arrays.toString(args));
        return key;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object result = retrieveFromCache(methodInvocation);
        if (result == null) {
            LOG.debug("Item not found in cache executing method");
            result = invokeAndCache(methodInvocation);
        }
        return result;
    }
}
