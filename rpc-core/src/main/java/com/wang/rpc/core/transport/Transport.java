package com.wang.rpc.core.transport;

/**
 * @author wangjiabao
 */
public interface Transport {

    /**
     * start transport
     *
     * @param sync
     */
    void start(boolean sync);

    /**
     * close transport
     *
     * @param sync
     */
    void close(boolean sync);
}
