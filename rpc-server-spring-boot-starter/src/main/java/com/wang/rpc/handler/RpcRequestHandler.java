package com.wang.rpc.handler;

import com.wang.rpc.core.domain.enums.MessageStatusEnum;
import com.wang.rpc.core.domain.enums.MessageTypeEnum;
import com.wang.rpc.core.domain.request.TinyRpcRequest;
import com.wang.rpc.core.domain.response.TinyRpcResponse;
import com.wang.rpc.core.protocol.MessageHeader;
import com.wang.rpc.core.protocol.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.wang.rpc.cache.ServiceCache.SERVICE_CACHE;

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

            TinyRpcRequest request = requestMsgProtocol.getBody();
            try {
                Object service = SERVICE_CACHE.get(request.getServiceName());
                if (service == null) {
                    // service
                    throw new IllegalArgumentException("service is not exist, " + request.getServiceName());
                }
                Method method = service.getClass().getMethod(request.getMethod(), request.getParameterTypes());
                Object result = method.invoke(service, request.getParameters());
                header.setStatus(MessageStatusEnum.SUCCESS.getCode());
                response.setData(result);
            } catch (Exception e) {
                header.setStatus(MessageStatusEnum.FAIL.getCode());
                response.setMessage(e.getMessage());
            }

            // 最后将消息写回
            ctx.writeAndFlush(responseMsgProtocol);
        });
    }
}
