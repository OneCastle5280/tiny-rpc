package com.wang.rpc.core.transport.channel;

/**
 * @author wangjiabao
 */
public interface TinyChannel {

    /**
     * channel is active
     *
     * @return true or false
     */
    boolean isActive();

    /**
     * send msg to channel
     *
     * @param msg to send
     */
    void send(Object msg) throws RuntimeException;
}
