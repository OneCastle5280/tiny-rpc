package com.wang.rpc.core.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangjiabao
 */
@Data
public class MessageProtocol<T> implements Serializable {

    /**
     * 消息头【固定长度】
     */
    private MessageHeader header;
    /**
     * 消息体
     */
    private T body;

}
