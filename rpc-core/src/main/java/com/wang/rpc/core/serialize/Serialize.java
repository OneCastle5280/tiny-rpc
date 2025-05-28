package com.wang.rpc.core.serialize;

import java.io.IOException;

/**
 * TODO SPI
 *
 * @author wangjiabao
 */
public interface Serialize {
    /**
     * serialize
     *
     * @param obj
     * @param <T>
     * @return
     * @throws IOException
     */
    <T> byte[] serialize(T obj) throws IOException;

    /**
     * deserialize
     *
     * @param data
     * @param clz
     * @param <T>
     * @return
     * @throws IOException
     */
    <T> T deserialize(byte[] data, Class<T> clz) throws IOException;
}