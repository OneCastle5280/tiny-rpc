package com.wang.rpc.core.domain.enums;

import lombok.Getter;

/**
 * @author wangjiabao
 */
@Getter
public enum MessageTypeEnum {
    /**
     * 请求
     */
    REQUEST((byte) 1),
    /**
     * 响应
     */
    RESPONSE((byte) 2);

    private final byte type;

    MessageTypeEnum(byte type) {
        this.type = type;
    }

    public static MessageTypeEnum findByType(byte type) {
        for (MessageTypeEnum msgType : MessageTypeEnum.values()) {
            if (msgType.getType() == type) {
                return msgType;
            }
        }
        return null;
    }
}
