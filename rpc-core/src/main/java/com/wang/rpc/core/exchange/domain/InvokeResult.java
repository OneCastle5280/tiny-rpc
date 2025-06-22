package com.wang.rpc.core.exchange.domain;

/**
 * @author wangjiabao
 */
public interface InvokeResult {
    /**
     * set result
     *
     * @param result
     */
    void setResult(Object result);

    /**
     * set exception
     *
     * @param t
     */
    void setException(Throwable t);

    /**
     * if exception is not null, return exception, else return result
     *
     */
    Object getResult();
}
