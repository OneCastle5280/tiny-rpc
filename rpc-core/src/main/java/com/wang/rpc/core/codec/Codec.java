package com.wang.rpc.core.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author wangjiabao
 */
public interface Codec {
    ByteBuf encode(Object obj);

    /**
     * decode TinyChannel readable bytes
     *
     * @param ctx TinyChannel ctx
     * @param in
     * @return {@link DecodeResult} or object
     */
    Object decode(ChannelHandlerContext ctx, ByteBuf in);

    enum DecodeResult {
        /**
         * may be half packet, need more input
         */
        NEED_MORE_INPUT,
        /**
         * unknown_msg
         */
        UNKNOWN_MSG,
    }
}
