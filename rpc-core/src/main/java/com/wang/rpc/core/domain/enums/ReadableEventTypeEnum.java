package com.wang.rpc.core.domain.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * readable event type
 *
 * @author wangjiabao
 */
public enum ReadableEventTypeEnum {
    /**
     * unknown type
     */
    UNKNOWN_TYPE((byte) 0),
    /**
     * heart beat
     */
    HEARTBEAT((byte) 1),
    /**
     * request
     */
    REQUEST((byte) 2),
    /**
     * response
     */
    RESPONSE((byte) 3),
    ;

    private final byte type;

    ReadableEventTypeEnum(byte type) {
        this.type = type;
    }

    public byte getType() {
        return type;
    }

    public static byte findByType(byte type) {
        return Arrays.stream(ReadableEventTypeEnum.values())
                .filter(item -> item.type == type).findFirst()
                .orElse(UNKNOWN_TYPE).type;
    }
}
