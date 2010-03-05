package org.fgt.ws.client;

import static org.junit.Assert.assertNotNull;
import net.sf.ehcache.Cache;

import org.junit.Before;
import org.junit.Test;

public class CacheInvocationHandlerTest{

	
	private Cache cache;
	private Object instance;
	
	@Before
	public void prepareCache() throws Exception {
		cache = new Cache("test", 10, false, false, 0, 0);
	}
	
	
	private CacheInvocationHandler createCacheInvocationHandler(Cache cache, Object instance) {
		return new CacheInvocationHandler(cache, instance);		
	}
	
	@Test
	public void should_create_valid_cacheInvocationHandler(){
		instance = new String("pepe");
		assertNotNull(createCacheInvocationHandler(cache, instance));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidCreationNullCache(){
		createCacheInvocationHandler(null,"pepe");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidCreationNullInstance(){
		createCacheInvocationHandler(cache,null);
	}
}
