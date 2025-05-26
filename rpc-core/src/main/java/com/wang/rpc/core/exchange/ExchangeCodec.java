package com.wang.rpc.core.exchange;

import com.wang.rpc.core.codec.Codec;
import com.wang.rpc.core.domain.enums.ReadableEventTypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import static com.wang.rpc.core.codec.Codec.DecodeResult.NEED_MORE_INPUT;
import static com.wang.rpc.core.codec.Codec.DecodeResult.UNKNOWN_MSG;

/**
 * @author wangjiabao
 */
public class ExchangeCodec implements Codec {
    /**
     * header length
     */
    public static final int HEADER_LENGTH = 16;
    /**
     * magic
     */
    public static final short MAGIC = 0xaab;

    public static final byte heartbeat_flag = 0;
    public static final byte request_flag = 1;
    public static final byte response_flag = 2;

    @Override
    public ByteBuf encode(Object obj) {
        return null;
    }


    @Override
    public Object decode(ChannelHandlerContext ctx, ByteBuf in) {
        int readableBytes = in.readableBytes();
        if (readableBytes < HEADER_LENGTH) {
            // need more input
            return NEED_MORE_INPUT;
        }

        // mark index
        in.markReaderIndex();
        // check magic
        short magic = in.readShort();
        if (MAGIC != magic) {
            // unknown msg
            return UNKNOWN_MSG;
        }
        // version
        byte version = in.readByte();
        // serialize type
        byte serializeType = in.readByte();
        // msg type
        byte type = in.readByte();
        // msg status
        byte status = in.readByte();
        // request id
        long reqId = in.readLong();
        // data Length
        int dataLength = in.readInt();
        if (readableBytes < HEADER_LENGTH + dataLength) {
            // reset reader index for next readable
            in.resetReaderIndex();
            return NEED_MORE_INPUT;
        }

        // dispatch by type
        byte eventType = ReadableEventTypeEnum.findByType(type);
        if (eventType == ReadableEventTypeEnum.REQUEST.getType()) {
            // decode request

        } else if (eventType == ReadableEventTypeEnum.RESPONSE.getType()) {
            // decode response
        }

        return null;
    }
}
