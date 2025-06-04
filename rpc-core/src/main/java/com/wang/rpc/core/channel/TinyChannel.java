package com.wang.rpc.core.channel;

/**
 * holder transport channel,
 *
 * @author wangjiabao
 */
public interface TinyChannel {

    /**
     * send msg
     *
     * @param msg
     * @throws RuntimeException
     */
    void send(Object msg) throws RuntimeException;
}
