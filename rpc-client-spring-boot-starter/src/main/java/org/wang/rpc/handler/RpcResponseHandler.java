package org.wang.rpc.handler;

import com.wang.rpc.core.domain.response.TinyRpcResponse;
import com.wang.rpc.core.protocol.MessageHeader;
import com.wang.rpc.core.protocol.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * netty 入站处理器
 *
 * @author wangjiabao
 */
public class RpcResponseHandler extends SimpleChannelInboundHandler<MessageProtocol<TinyRpcResponse>> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol<TinyRpcResponse> messageProtocol) throws Exception {
        if (messageProtocol == null || messageProtocol.getHeader() == null) {
            return;
        }
        // todo 将响应数据补充到 rpc response
    }
}
