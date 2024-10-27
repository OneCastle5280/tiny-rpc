package com.wang.rpc.core.codecs;

import com.wang.rpc.core.constants.RpcConstants;
import com.wang.rpc.core.domain.enums.MessageTypeEnum;
import com.wang.rpc.core.domain.enums.SerializationTypeEnum;
import com.wang.rpc.core.domain.request.TinyRpcRequest;
import com.wang.rpc.core.domain.response.TinyRpcResponse;
import com.wang.rpc.core.protocol.MessageHeader;
import com.wang.rpc.core.protocol.MessageProtocol;
import com.wang.rpc.core.serialization.RpcSerialization;
import com.wang.rpc.core.serialization.SerializationFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author 没有头发的二次元
 */
public class RpcDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < RpcConstants.HEADER_TOTAL_LEN) {
            // 可读的数据长度小于消息请求头大小，暂不处理
            return;
        }
        // mark readIndex，如果发现收到的消息体长度小于请求头里的数据长度的时候，就 reset readIndex
        in.markReaderIndex();

        // 按照 encode 的顺序来解析数据
        // 1. 魔数
        short magic = in.readShort();
        if (RpcConstants.MAGIC != magic) {
            return;
        }
        // 2. 版本
        byte version = in.readByte();
        // 3. 序列化方式
        byte serialization = in.readByte();
        // 4. 类型
        byte type = in.readByte();
        MessageTypeEnum typeEnum = MessageTypeEnum.findByType(type);
        if (typeEnum == null) {
            return;
        }
        // 5. 状态
        byte status = in.readByte();
        // 6. 请求 ID
        CharSequence requestId = in.readCharSequence(RpcConstants.REQUEST_ID_LEN, StandardCharsets.UTF_8);

        // 获取数据长度
        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            // 消息体还没有全部收到，先不处理，重置读指针位置
            in.resetReaderIndex();
            return;
        }

        // 获取到数据
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        // 将从流获取的数据组装成 protocal 形式的数据
        MessageHeader header = new MessageHeader()
                .setMagic(magic)
                .setVersion(version)
                .setSerialization(serialization)
                .setType(type)
                .setStatus(status)
                .setRequestId((String) requestId)
                .setMsgLen(dataLength)
                ;

        RpcSerialization rpcSerialization = SerializationFactory.getRpcSerialization(SerializationTypeEnum.parseByType(serialization));
        switch (typeEnum) {
            case REQUEST:
                // 请求
                TinyRpcRequest rpcRequest = rpcSerialization.deserialize(data, TinyRpcRequest.class);
                if (rpcRequest != null) {
                    MessageProtocol<TinyRpcRequest> protocol = new MessageProtocol<>();
                    protocol.setHeader(header);
                    protocol.setBody(rpcRequest);
                    out.add(protocol);
                }
                break;
            case RESPONSE:
                // 响应
                TinyRpcResponse rpcResponse = rpcSerialization.deserialize(data, TinyRpcResponse.class);
                if (rpcResponse != null) {
                    MessageProtocol<TinyRpcResponse> protocol = new MessageProtocol<>();
                    protocol.setHeader(header);
                    protocol.setBody(rpcResponse);
                    out.add(protocol);
                }
                break;
            default:
        }

    }
}
