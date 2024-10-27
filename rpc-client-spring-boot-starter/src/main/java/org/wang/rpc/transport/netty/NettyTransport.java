package org.wang.rpc.transport.netty;

import com.wang.rpc.core.codecs.RpcDecoder;
import com.wang.rpc.core.codecs.RpcEncoder;
import com.wang.rpc.core.domain.request.TinyRpcRequest;
import com.wang.rpc.core.domain.response.TinyRpcResponse;
import com.wang.rpc.core.protocol.MessageProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.wang.rpc.domain.transport.Request;
import org.wang.rpc.handler.RpcResponseHandler;

/**
 * @author wangjiabao
 */
public class NettyTransport {


    private final Bootstrap bootstrap;

    private final EventLoopGroup eventLoopGroup;

    private RpcResponseHandler rpcResponseHandler;

    public NettyTransport() {
        bootstrap = new Bootstrap();
        eventLoopGroup = new NioEventLoopGroup();

        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new RpcDecoder())
                                .addLast(rpcResponseHandler)
                                .addLast(new RpcEncoder<>());
                    }
                });
    }

    public MessageProtocol<TinyRpcResponse> sendRequest(Request request) throws InterruptedException {
        ChannelFuture channelFuture = bootstrap.connect(request.getAddress(), request.getPort());
        channelFuture.sync().addListener(listener -> {
            if (channelFuture.isSuccess()) {
                // success
            } else {
                channelFuture.cause().printStackTrace();
                eventLoopGroup.shutdownGracefully();
            }
        });
        // 将数据写回
        channelFuture.channel().writeAndFlush(request.getRequestMessageProtocol());
        // todo 返回结果
        return new MessageProtocol<>();
    }
}
