package com.wang.rpc.core.handler;

import com.wang.rpc.core.domain.request.TinyRpcRequest;
import com.wang.rpc.core.protocol.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wangjiabao
 */
public class RpcRequestHandler extends SimpleChannelInboundHandler<MessageProtocol<TinyRpcRequest>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol<TinyRpcRequest> msg) throws Exception {

    }
}
