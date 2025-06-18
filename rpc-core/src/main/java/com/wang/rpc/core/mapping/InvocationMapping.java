package com.wang.rpc.core.mapping;

import com.wang.rpc.core.exchange.domain.Invocation;
import com.wang.rpc.core.exchange.domain.TinyRequest;

/**
 * @author wangjiabao
 */
public class InvocationMapping {

    /**
     * {@code TinyRequest} convertTo {@code Invocation}
     *
     * @param request
     * @return
     */
    public static Invocation convertToInvocation(TinyRequest request) {
        if (request == null) {
            return null;
        }

        return new Invocation()
                .setInterfaceName(request.getInterfaceName())
                .setMethodName(request.getMethodName())
                .setVersion(request.getVersion())
                .setParamTypes(request.getParamTypes())
                .setParams(request.getParams())
                ;

    }

    /**
     * {@code Invocation} convertTo {@code TinyRequest}
     *
     * @param invocation
     * @return
     */
    public static TinyRequest convertToTinyRequest(Invocation invocation) {
        if (invocation == null) {
            return null;
        }

        return new TinyRequest()
                .setInterfaceName(invocation.getInterfaceName())
                .setMethodName(invocation.getMethodName())
                .setVersion(invocation.getVersion())
                .setParamTypes(invocation.getParamTypes())
                .setParams(invocation.getParams())
                ;
    }
}
