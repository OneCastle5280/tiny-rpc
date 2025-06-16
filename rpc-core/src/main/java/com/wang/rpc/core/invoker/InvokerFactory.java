package com.wang.rpc.core.invoker;

import com.wang.rpc.core.exception.InvokerNotFound;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangjiabao
 */
public class InvokerFactory {

    private InvokerFactory(){}

    /**
     * rpcService key connector
     */
    private static final String CONNECTOR = "-";

    /**
     * invokerUniqueKey -> invokerWrapper
     */
    private static final Map<String, InvokerWrapper> INVOKER_MAP = new ConcurrentHashMap<>();


    /**
     * register invoker
     *
     * @param serviceName
     * @param methodName
     * @param version
     * @param invoker
     */
    public void registerInvoker(String serviceName, String methodName, String version, Invoker invoker) {
        String uniqueKey = invokerUniqueKey(serviceName, methodName, version);
        INVOKER_MAP.putIfAbsent(uniqueKey, new InvokerWrapper(invoker));
    }


    /**
     * build invoker unique key
     *
     * @param serviceName service name
     * @param methodName method name
     * @param version version
     * @return
     */
    public static String invokerUniqueKey(String serviceName, String methodName, String version) {
        return serviceName + CONNECTOR + methodName + CONNECTOR + version;
    }

    /**
     * get invoker with cache
     *
     * @throws InvokerNotFound if invoker is not found, will throw InvokerNotFound Exception
     */
    public static Invoker getInvoker(String serviceName, String methodName, String version) throws InvokerNotFound {
        String uniqueKey = invokerUniqueKey(serviceName, methodName, version);
        InvokerWrapper wrapper = INVOKER_MAP.get(uniqueKey);
        if (wrapper == null) {
            throw new InvokerNotFound( uniqueKey + "invoker is not found");
        }
        return wrapper.getInvoker();
    }
}
