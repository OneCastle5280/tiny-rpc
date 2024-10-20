package com.wang.rpc.core.serialization;

import java.io.IOException;

/**
 * @author wangjiabao
 */
public interface RpcSerialization {
    <T> byte[] serialize(T obj) throws IOException;

    <T> T deserialize(byte[] data, Class<T> clz) throws IOException;
}