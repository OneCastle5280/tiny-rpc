package com.wang.rpc.core.handler;

import com.wang.rpc.core.domain.response.TinyRpcResponse;
import com.wang.rpc.core.protocol.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端接收到 read 事件之后，对响应体的解析处理 【入站操作】
 *
 * @author wangjiabao
 */
public class RpcResponseHandler extends SimpleChannelInboundHandler<MessageProtocol<TinyRpcResponse>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol<TinyRpcResponse> msg) throws Exception {

    }
}
