package com.wang.rpc.core.invoker;

import com.wang.rpc.core.enums.InvokerType;
import com.wang.rpc.core.exchange.ClientExchange;
import com.wang.rpc.core.exchange.domain.ClientInvokeResult;
import com.wang.rpc.core.exchange.domain.Invocation;
import com.wang.rpc.core.exchange.domain.InvokeResult;
import com.wang.rpc.core.exchange.domain.TinyRequest;
import com.wang.rpc.core.exchange.future.TinyFuture;
import com.wang.rpc.core.mapping.InvocationMapping;
import com.wang.rpc.core.utils.RequestIdGenUtil;

/**
 * invoker common processing logic
 *
 * @author wangjiabao
 */
public abstract class AbstractInvoker implements Invoker{

    protected InvokerType invokerType;

    protected ClientExchange clientExchange;

    public AbstractInvoker(InvokerType invokerType) {
        if (invokerType == InvokerType.SERVER) {
            // TODO is server invoker
        } else if(invokerType == InvokerType.CLIENT) {
            // is client invoker
            this.clientExchange = ClientExchange.INSTANCE;
        } else {
            throw new IllegalArgumentException("invokerType is not supported");
        }
    }

    protected boolean isClientInvoker() {
        return InvokerType.CLIENT.equals(this.invokerType);
    }

    protected boolean isServerInvoker() {
        return InvokerType.SERVER.equals(this.invokerType);
    }

    @Override
    public InvokeResult invoke(Invocation invocation) {
        InvokeResult result;
        if (isClientInvoker()) {
            try {
                result = new ClientInvokeResult(invocation.getInvokeMode());

                TinyRequest request = InvocationMapping.convertToTinyRequest(RequestIdGenUtil.gen(), invocation);
                TinyFuture tinyFuture = this.clientExchange.sendRequest(request);
                result.setResult();
            } catch (Exception e) {

            }
        } else if (isServerInvoker()) {
            // TODO
        }
        return result;
    }
}
