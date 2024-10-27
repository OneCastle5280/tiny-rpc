package com.wang.rpc.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangjiabao
 */
public class ServiceCache {

    public static final Map<String, Object> SERVICE_CACHE = new ConcurrentHashMap<>();

    public static void put(String serverName, Object server) {
        SERVICE_CACHE.put(serverName, server);
    }

    public static Object get(String serverName) {
        return SERVICE_CACHE.get(serverName);
    }
}
