package com.wang.rpc.core.domain.enums;

import lombok.Getter;

/**
 * @author wangjiabao
 */
@Getter
public enum MessageStatusEnum {
    /**
     * 成功
     */
    SUCCESS((byte) 1),
    /**
     * 失败
     */
    FAIL((byte)0),
    ;

    private final byte code;
    MessageStatusEnum(byte code) {
        this.code = code;
    }

    public static boolean isSuccess(byte code){
        return MessageStatusEnum.SUCCESS.code == code;
    }

}
