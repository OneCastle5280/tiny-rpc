package com.wang.rpc.core.codecs;

import com.wang.rpc.core.constants.RpcConstants;
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
            // todo 说明是非我们需要处理的数据
            return;
        }
        // 2. 版本
        byte version = in.readByte();
        // 3. 序列化方式
        byte serialization = in.readByte();
        // 4. 类型
        byte type = in.readByte();
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

        // 根据不同类型的消息，分别进行处理消息
        // todo

    }
}
