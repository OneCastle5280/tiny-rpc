package com.wang.rpc.core.serialize;

import com.wang.rpc.core.domain.enums.SerializationTypeEnum;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangjiabao
 */
public class SerializeSupport {

    /**
     * type -> serializeMap
     */
    private static Map<Byte, Serialize> serializeMap = new HashMap<>();

    public static Serialize getRpcSerialization(SerializationTypeEnum typeEnum) {
        switch (typeEnum) {
            case HESSIAN:
                return new Hessian2();
            default:
                throw new IllegalArgumentException("serialization type is illegal");
        }
    }

    public static <T> T deserialize(byte[] dataBytes, byte serializeType, Class<T> clazz) throws Exception{
        Serialize serialize = serializeMap.get(serializeType);
        if (serialize == null) {
            // TODO exception
        }
        return serialize.deserialize(dataBytes, clazz);

    }
}
