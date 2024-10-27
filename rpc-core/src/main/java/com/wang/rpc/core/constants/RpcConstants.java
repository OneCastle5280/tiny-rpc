package com.wang.rpc.core.constants;

/**
 * @author wangjiabao
 */
public interface RpcConstants {

    /**
     * ZK base path
     */
    String BASE_PATH = "/tiny-rpc";

    /**
     * 消息魔数
     */
    short MAGIC = 0x10;
    /**
     * 消息版本
     */
    byte VERSION = 0x11;
    /**
     * 消息头总长度
     */
    int HEADER_TOTAL_LEN = 42;
    /**
     * 请求 ID 长度 4*8 byte
     */
    int REQUEST_ID_LEN = 32;

}
