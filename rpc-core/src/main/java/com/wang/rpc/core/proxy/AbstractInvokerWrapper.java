package com.wang.rpc.core.proxy;

import com.wang.rpc.core.proxy.domain.InvokeRequest;
import com.wang.rpc.core.proxy.domain.InvokeResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * common
 *
 * @author wangjiabao
 */
public abstract class AbstractInvokerWrapper implements InvokerWrapper {

    /**
     * invoker map
     */
    public static final Map<String, InvokerWrapper> INVOKER_MAP = new ConcurrentHashMap<>();

    /**
     * register invokerWrapper
     *
     * @param serviceName
     * @param methodName
     * @param version
     * @param invokerWrapper
     */
    public void registerInvoker(String serviceName, String methodName, String version, InvokerWrapper invokerWrapper) {
        InvokeRequest invokeRequest = new InvokeRequest();
        invokeRequest.setServiceName(serviceName);
        invokeRequest.setMethodName(methodName);
        invokeRequest.setVersion(version);

        String invokerKey = invokerWrapper.uniqueKey(invokeRequest);
        if (INVOKER_MAP.containsKey(invokerKey)) {
            throw new IllegalArgumentException(invokerKey + "had existed");
        }

        INVOKER_MAP.putIfAbsent(invokerKey, invokerWrapper);
    }

    /**
     * return unique invoke key
     *
     * @param request invoke request
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public abstract String uniqueKey(InvokeRequest request) throws IllegalArgumentException;

    @Override
    public InvokeResult invoke(InvokeRequest request) {
        String uniqueKey = uniqueKey(request);
        InvokerWrapper invokerWrapper = INVOKER_MAP.get(uniqueKey);
        if (invokerWrapper == null) {
            throw new IllegalArgumentException(uniqueKey + "is not exist");
        }
        return invokerWrapper.invoke(request);
    }
}
