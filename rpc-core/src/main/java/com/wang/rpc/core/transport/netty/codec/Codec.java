package com.wang.rpc.core.transport.netty.codec;

import io.netty.buffer.ByteBuf;

/**
 * @author wangjiabao
 */
public interface Codec {

    ByteBuf encode(Object obj);

    Object decode(ByteBuf byteBuf);

}
