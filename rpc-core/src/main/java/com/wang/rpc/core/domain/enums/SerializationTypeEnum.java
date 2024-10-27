package com.wang.rpc.core.domain.enums;

import lombok.Getter;

/**
 * @author wangjiabao
 */
@Getter
public enum SerializationTypeEnum {
    /**
     * hessian2
     */
    HESSIAN((byte) 0);

    private byte type;

    SerializationTypeEnum(byte type) {
        this.type = type;
    }

    public static SerializationTypeEnum parseByName(String typeName) {
        for (SerializationTypeEnum typeEnum : SerializationTypeEnum.values()) {
            if (typeEnum.name().equalsIgnoreCase(typeName)) {
                return typeEnum;
            }
        }
        return HESSIAN;
    }

    public static SerializationTypeEnum parseByType(byte type) {
        for (SerializationTypeEnum typeEnum : SerializationTypeEnum.values()) {
            if (typeEnum.getType() == type) {
                return typeEnum;
            }
        }
        return HESSIAN;
    }

}
