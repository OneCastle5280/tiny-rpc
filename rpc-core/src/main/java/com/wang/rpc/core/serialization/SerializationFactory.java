package com.wang.rpc.core.serialization;

import com.wang.rpc.core.domain.enums.SerializationTypeEnum;

/**
 * @author wangjiabao
 */
public class SerializationFactory {

    public static RpcSerialization getRpcSerialization(SerializationTypeEnum typeEnum) {
        switch (typeEnum) {
            case HESSIAN:
                return new Hessian2();
            default:
                throw new IllegalArgumentException("serialization type is illegal");
        }
    }
}
