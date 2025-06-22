package com.wang.rpc.core.exchange;

import com.wang.rpc.core.exchange.domain.TinyRequest;
import com.wang.rpc.core.exchange.future.TinyFuture;
import com.wang.rpc.core.transport.channel.ChannelWrapper;

/**
 * @author wangjiabao
 */
public class ClientExchange implements Exchange{

    public static final ClientExchange INSTANCE = new ClientExchange();

    private final ChannelWrapper channelWrapper;

    public ClientExchange() {
        this.channelWrapper = new ChannelWrapper();
    }

    /**
     * send request to channel
     *
     * @param request
     */
    public TinyFuture sendRequest(TinyRequest request) {
        return TinyFuture.addTinyFuture(request.getId(), () -> {
            // send msg
            this.channelWrapper.getWithLoadBalance().send(request);
            return null;
        });
    }
}
