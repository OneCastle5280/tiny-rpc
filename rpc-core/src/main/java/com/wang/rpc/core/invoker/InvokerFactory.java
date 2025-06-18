package com.wang.rpc.core.invoker;

import com.wang.rpc.core.exception.InvokerNotFound;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangjiabao
 */
public class InvokerFactory<T> {

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
     */
    public void registerInvoker(String interfaceName, String methodName, String version, Invoker invoker) {
        String uniqueKey = invokerUniqueKey(interfaceName, methodName, version);
        INVOKER_MAP.putIfAbsent(uniqueKey, new InvokerWrapper(invoker));
    }


    /**
     * build invoker unique key
     *
     * @param interfaceName interface name
     * @param methodName method name
     * @param version getVersion
     * @return
     */
    public static String invokerUniqueKey(String interfaceName, String methodName, String version) {
        return interfaceName + CONNECTOR + methodName + CONNECTOR + version;
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
