package com.wang.rpc.core.exchange.handler;

import com.wang.rpc.core.exception.InvokerNotFound;
import com.wang.rpc.core.exchange.domain.Invocation;
import com.wang.rpc.core.exchange.domain.InvokeResult;
import com.wang.rpc.core.exchange.domain.TinyRequest;
import com.wang.rpc.core.exchange.future.TinyFuture;
import com.wang.rpc.core.invoker.InvokerFactory;
import com.wang.rpc.core.mapping.InvocationMapping;
import com.wang.rpc.core.utils.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;

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
            result = InvokerFactory.getInvoker(request.getInterfaceName(), request.getMethodName(), request.getVersion())
                    .invoke(InvocationMapping.convertToInvocation(request));
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



}
