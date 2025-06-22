package com.wang.rpc.core.exchange.domain;

import com.wang.rpc.core.enums.InvokeMode;

/**
 * @author wangjiabao
 */
public abstract class AbstractInvokeResult implements InvokeResult{

    protected InvokeMode invokeMode;
    protected Object result;
    protected Throwable exception;

    public AbstractInvokeResult(InvokeMode invokeMode) {
        this.invokeMode = invokeMode;
    }

    @Override
    public void setException(Throwable t) {
        this.exception = t;
    }

    @Override
    public Object getResult() {
        if (this.exception != null) {
            return this.exception;
        }
        return createResult();
    }

    @Override
    public void setResult(Object result) {
        this.result = result;
    }

    /**
     * base on invokeMode create result
     */
    protected Object createResult() {
        if (InvokeMode.SYNC.equals(this.invokeMode)) {
            return this.result;
        } else if (InvokeMode.ASYNC.equals(this.invokeMode)) {

        }
        // TODO async
        return null;
    }
}
