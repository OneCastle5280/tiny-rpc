package com.wang.rpc.core.exchange.handler;

import com.wang.rpc.core.exception.InvokerNotFound;
import com.wang.rpc.core.exchange.domain.Invocation;
import com.wang.rpc.core.exchange.domain.InvokeResult;
import com.wang.rpc.core.exchange.domain.TinyRequest;
import com.wang.rpc.core.exchange.future.TinyFuture;
import com.wang.rpc.core.exchange.invoker.InvokerFactory;
import com.wang.rpc.core.utils.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.*;

/**
 *
 * handle request msg
 *
 * @author wangjiabao
 */
@Slf4j
public class RequestHandler {

    public RequestHandler() {
    }

    /**
     * async handle request
     *
     * @param request  tiny request
     * @return
     */
    public TinyFuture handleRequest(TinyRequest request) {
        if (request == null) {
            throw new NullPointerException();
        }

        // async handle request
        return TinyFuture.supplyAsync(() -> doHandleRequest(request));
    }

    /**
     * do handle request
     *
     * @param request
     * @return
     */
    private Object doHandleRequest(TinyRequest request) {
        InvokeResult result = new InvokeResult();
        try {
            result = InvokerFactory.getInvoker(request.getServiceName(), request.getMethodName(), request.getVersion())
                    .invoke(this.convertToInvocation(request));
        } catch (InvokerNotFound e) {
            log.error("[RequestHandler] doHandleRequest err", e);
            result.setException(e);
            result.setErrMessage(ThrowableUtil.toString(e));
        } catch (Exception e) {
            result.setException(e);
            result.setErrMessage(ThrowableUtil.toString(e));
        }
        return result;
    }

    private Invocation convertToInvocation(TinyRequest request) {
        if (request == null) {
            return null;
        }

        return new Invocation()
                .setServiceName(request.getServiceName())
                .setMethodName(request.getMethodName())
                .setVersion(request.getVersion())
                .setParamTypes(request.getParamTypes())
                .setParams(request.getParams())
                ;

    }

}
