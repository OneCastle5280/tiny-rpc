package com.wang.rpc.core.exchange.handler;

import com.wang.rpc.core.channel.TinyChannel;
import com.wang.rpc.core.exchange.domain.TinyRequest;
import com.wang.rpc.core.exchange.domain.TinyResponse;

/**
 * @author wangjiabao
 */
public class ExchangeHandler {

    public void receive(TinyChannel channel, Object msg) {
        if (msg instanceof TinyRequest) {
            // request
            handleRequest((TinyRequest) msg);
        } else if (msg instanceof TinyResponse) {
            // response
        }
    }

    private void handleRequest(TinyRequest request) {
        // async handle
    }

}
