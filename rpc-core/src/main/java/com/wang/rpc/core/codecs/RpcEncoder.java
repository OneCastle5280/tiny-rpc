package com.wang.rpc.core.codecs;

import com.wang.rpc.core.protocol.MessageHeader;
import com.wang.rpc.core.protocol.MessageProtocol;
import com.wang.rpc.core.serialization.RpcSerialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

/**
 * @author wangjiabao
 */
public class RpcEncoder<T> extends MessageToByteEncoder<MessageProtocol<T>> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageProtocol<T> messageProtocol, ByteBuf byteBuf) throws Exception {
        MessageHeader header = messageProtocol.getHeader();

        // 往 byteBuf 写入数据
        // 1. 魔数
        byteBuf.writeShort(header.getMagic());
        // 2. 版本
        byteBuf.writeShort(header.getVersion());
        // 3. 序列化方式
        byteBuf.writeShort(header.getSerialization());
        // 4. 类型
        byteBuf.writeShort(header.getType());
        // 5. 状态
        byteBuf.writeShort(header.getStatus());
        // 6. 请求 ID
        byteBuf.writeCharSequence(header.getRequestId(), StandardCharsets.UTF_8);

        // 选择序列化类型进行序列化
        // todo
        RpcSerialization serialization = null;
        byte[] data = serialization.serialize(messageProtocol.getBody());

        // 消息体长度
        byteBuf.writeInt(data.length);
        // 消息体内容
        byteBuf.writeBytes(data);
    }
}
