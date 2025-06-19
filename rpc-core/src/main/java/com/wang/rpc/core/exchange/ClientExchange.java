package com.wang.rpc.core.exchange;

import com.wang.rpc.core.exchange.domain.Invocation;
import com.wang.rpc.core.mapping.InvocationMapping;
import com.wang.rpc.core.transport.channel.ChannelWrapper;
import com.wang.rpc.core.transport.channel.TinyChannel;

/**
 * @author wangjiabao
 */
public class ClientExchange implements Exchange{

    private final ChannelWrapper channelWrapper;

    public ClientExchange() {
        this.channelWrapper = new ChannelWrapper();
    }

    /**
     * send request to channel
     *
     * @param invocation
     */
    public void sendRequest(Invocation invocation) {
        TinyChannel channel = this.channelWrapper.getWithLoadBalance();

        channel.send(InvocationMapping.convertToTinyRequest(invocation));
    }
}
