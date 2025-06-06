package com.wang.rpc.core.exchange.service;

import com.wang.rpc.core.exception.RpcServiceNotFound;
import com.wang.rpc.core.exchange.domain.TinyRequest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangjiabao
 */
public class RpcServiceFactory {

    private RpcServiceFactory() {
    }

    /**
     * rpcService key connector
     */
    private static final String CONNECTOR = "-";

    /**
     * rpcService map
     */
    private static final Map<String, RpcService> rpcServiceMap = new ConcurrentHashMap<>();

    /**
     * register rpcService to map
     *
     * @param rpcService
     */
    public static void register(RpcService rpcService) {
        rpcServiceMap.putIfAbsent(
                serviceKey(rpcService.serviceName(), rpcService.methodName(), rpcService.version()),
                rpcService
        );
    }

    /**
     * get rpcService
     *
     * @param request
     * @return
     */
    public static RpcService getRpcService(TinyRequest request) throws RpcServiceNotFound{
        String serviceKey = serviceKey(request.getServiceName(), request.getMethodName(), request.getVersion());
        RpcService rpcService = rpcServiceMap.get(serviceKey);
        if (rpcService == null) {
            throw new RpcServiceNotFound(serviceKey + " is not exist");
        }
        return rpcService;

    }

    /**
     * build service key
     *
     * @param serviceName service name
     * @param methodName method name
     * @param version version
     * @return
     */
    public static String serviceKey(String serviceName, String methodName, String version) {
        return serviceName + CONNECTOR + methodName + CONNECTOR + version;
    }
}
