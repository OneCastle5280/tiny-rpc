package com.wang.rpc.core.exchange.codec;

import com.wang.rpc.core.codec.Codec;
import com.wang.rpc.core.domain.enums.ReadableEventTypeEnum;
import com.wang.rpc.core.exchange.domain.TinyRequest;
import com.wang.rpc.core.exchange.domain.TinyResponse;
import com.wang.rpc.core.serialize.SerializeSupport;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.function.Supplier;

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
        long id = in.readLong();
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
            TinyRequest request = doDecode(in, dataLength, serializeType, TinyRequest.class, TinyRequest::new);
            // TODO NPE
            request.setId(id);
            return request;
        } else if (eventType == ReadableEventTypeEnum.RESPONSE.getType()) {
            // decode response
            TinyResponse response = doDecode(in, dataLength, serializeType, TinyResponse.class, TinyResponse::new);
            // TODO NPE
            response.setId(id);
            response.setStatus(status);
            return response;
        }

        return UNKNOWN_MSG;
    }


    private <T> T doDecode(ByteBuf in, int dataLength, byte serializeType, Class<T> targetClass, Supplier<T> instance) {
        if (dataLength == 0) {
            // TODO
            return instance.get();
        }

        // read data bytes
        byte[] dataBytes = new byte[dataLength];
        in.readBytes(dataBytes);

        // deserialize
        try {
            return SerializeSupport.deserialize(dataBytes, serializeType, targetClass);
        } catch (Exception e) {
            // TODO exception
        }
        // TODO
        return null;
    }
}
