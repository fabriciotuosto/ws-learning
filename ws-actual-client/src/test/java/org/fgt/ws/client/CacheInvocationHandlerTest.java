package org.fgt.ws.client;

import org.aopalliance.intercept.MethodInvocation;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Method;

import net.sf.ehcache.Cache;

import org.junit.Before;
import org.junit.Test;

public class CacheInvocationHandlerTest {

    private static final Object[] EMPTY_ARGS = new Object[0];
    private Cache cache;
    private Object instance;
    private MethodInvocation methodInvocation;
    private String key;
    
    @Before
    public void prepareTest() throws Exception {
        instance = new String("pepe");
        key = "java.lang.String.hashCode()[" + instance.hashCode() + "] []";
        cache = new Cache("test", 10, false, false, 0, 0);
        cache.initialise();
        methodInvocation = mock(MethodInvocation.class);
    }

    private CacheMethodInterceptor createCacheInvocationHandler(Cache cache) {
        return new CacheMethodInterceptor(cache);
    }

    @Test
    public void should_create_valid_cacheInvocationHandler() {
        assertNotNull(createCacheInvocationHandler(cache));
    }

    @Test
    public void testGeneratedCacheKey() throws Throwable {
        setpUpMethodInvocation();
        CacheMethodInterceptor handler = createCacheInvocationHandler(cache);
        assertEquals(key, handler.generateCacheKey(methodInvocation));
    }

    @Test
    public void testAddedToCache() throws Throwable {
        setpUpMethodInvocation();
        CacheMethodInterceptor handler = createCacheInvocationHandler(cache);
        handler.invoke(methodInvocation);
        assertEquals(instance.hashCode(), cache.get(key).getValue());
    }

    @Test
    public void should_return_null_from_cache() throws Throwable {
        setpUpMethodInvocation();
        assertNull(cache.get(key));
    }

    @Test
    public void should_execute_method() throws Throwable {
        setpUpMethodInvocation();
        when(methodInvocation.proceed()).thenReturn(instance.hashCode());
        CacheMethodInterceptor handler = createCacheInvocationHandler(cache);
        assertEquals(instance.hashCode(), handler.invoke(methodInvocation));
    }

    private void setpUpMethodInvocation() throws Throwable {
        methodInvocation = mock(MethodInvocation.class);
        Method method = String.class.getMethod("hashCode", new Class<?>[0]);
        when(methodInvocation.getMethod()).thenReturn(method);
        when(methodInvocation.getArguments()).thenReturn(EMPTY_ARGS);
        when(methodInvocation.getThis()).thenReturn(instance);
        when(methodInvocation.proceed()).thenReturn(instance.hashCode());
    }
}
