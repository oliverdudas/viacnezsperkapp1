package sk.dudas.appengine.viacnezsperk.service.cache;

import javax.cache.Cache;

/**
 * Holds an instance of a JCache cache to enable @Autowire injection.
 * javax.cache.Cache extends java.util.Map, but is no generic,
 * and Spring does not seem to be able to resolve Map dependencies without
 * generic type information.
 */
public class CacheHolder {

    Cache cache;

    public CacheHolder(Cache cache) {
        this.cache = cache;
    }

    public Cache getCache() {
        return cache;
    }

}
