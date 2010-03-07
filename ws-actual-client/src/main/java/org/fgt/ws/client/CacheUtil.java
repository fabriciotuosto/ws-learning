/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fgt.ws.client;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 *
 * @author Fabricio Tuosto
 */
class CacheUtil {
    private final String cacheName;
    private final CacheManager manager;

    public CacheUtil(String cacheName, CacheManager manager) {
        this.cacheName = cacheName;
        this.manager = manager;
    }


    public Cache getCache(){
        return manager.getCache(cacheName);
    }

}
