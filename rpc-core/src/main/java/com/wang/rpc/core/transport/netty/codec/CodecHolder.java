package com.wang.rpc.core.transport.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.Data;

import java.util.List;

/**
 * holder decoder/encoder and codec, proxy codec
 *
 * @author wangjiabao
 */
public class CodecHolder {

    private Decoder decoder;
    private Encoder encoder;
    private Codec codec;

    public CodecHolder(Codec codec) {
        this.decoder = new Decoder();
        this.encoder = new Encoder();
        this.codec = codec;
    }

    /**
     * byte ==> msg
     * */
    public class Decoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            // TODO
        }
    }

    /**
     * msg ==> byte
     */
    public class Encoder extends MessageToByteEncoder {

        @Override
        protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
            // TODO
        }
    }

    public Encoder getEncoder() {
        return this.encoder;
    }

    public Decoder getDecoder() {
        return this.decoder;
    }
}
