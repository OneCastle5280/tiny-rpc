package com.wang.rpc.core.protocol;

import com.wang.rpc.core.domain.enums.MessageStatusEnum;
import com.wang.rpc.core.domain.enums.MessageTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangjiabao
 */
@Data
public class MessageHeader implements Serializable {

    /**
     * 魔数 2byte
     */
    private short magic;
    /**
     * 版本 1byte
     */
    private byte version;

    /**
     * 序列化算法 1byte
     */
    private byte serialization;

    /**
     * 报文类型 1byte {@link MessageTypeEnum}
     */
    private byte type;

    /**
     * 状态 1byte {@link MessageStatusEnum}
     */
    private byte status;

    /**
     * 消息 ID 32byte
     */
    private String requestId;

    /**
     * 数据长度 4byte
     */
    private int msgLen;

}
