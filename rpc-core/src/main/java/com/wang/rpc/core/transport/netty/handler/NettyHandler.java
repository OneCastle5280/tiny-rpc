package com.wang.rpc.core.transport.netty.handler;

import com.wang.rpc.core.exchange.handler.ExchangeHandler;
import com.wang.rpc.core.transport.netty.NettyChannel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

/**
 * @author wangjiabao
 */
public class NettyHandler extends ChannelDuplexHandler {

    private final ExchangeHandler handler;

    public NettyHandler() {
        this.handler = new ExchangeHandler();
    }

    /**
     * handle Inbound msg
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyChannel channel = NettyChannel.getOrAddChannel(ctx.channel());
        handler.receive(channel, msg);
        ctx.fireChannelRead(msg);
    }

    /**
     * handle Outbound msg
     *
     * @param ctx
     * @param msg
     * @param promise
     * @throws Exception
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        // TODO
    }
}
