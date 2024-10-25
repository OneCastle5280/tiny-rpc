package com.wang.rpc.handler;

import com.wang.rpc.core.domain.enums.MessageStatusEnum;
import com.wang.rpc.core.domain.enums.MessageTypeEnum;
import com.wang.rpc.core.domain.request.TinyRpcRequest;
import com.wang.rpc.core.domain.response.TinyRpcResponse;
import com.wang.rpc.core.protocol.MessageHeader;
import com.wang.rpc.core.protocol.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wangjiabao
 */
public class RpcRequestHandler extends SimpleChannelInboundHandler<MessageProtocol<TinyRpcRequest>> {

    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10000));

    /**
     * netty 入站操作
     *
     * @param ctx
     * @param requestMsgProtocol
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol<TinyRpcRequest> requestMsgProtocol) throws Exception {
        // 每个线程处理一个请求
        threadPoolExecutor.execute(() -> {
            // 构建响应消息
            MessageProtocol<TinyRpcResponse> responseMsgProtocol = new MessageProtocol<>();
            TinyRpcResponse response = new TinyRpcResponse();
            responseMsgProtocol.setBody(response);
            // header
            MessageHeader header = new MessageHeader();
            responseMsgProtocol.setHeader(header);
            // 设置消息类型
            header.setType(MessageTypeEnum.RESPONSE.getType());
            try {
                // todo 利用反射调用 service 获得接口响应
                header.setStatus(MessageStatusEnum.SUCCESS.getCode());
            } catch (Exception e) {
                header.setStatus(MessageStatusEnum.FAIL.getCode());
                response.setMessage(e.getMessage());
            }

            // 最后将消息写回
            ctx.writeAndFlush(responseMsgProtocol);
        });
    }
}
