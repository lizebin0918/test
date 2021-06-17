package com.lzb.netty.handler;

import lombok.Data;

/**
 * 消息实体<br/>
 * Created on : 2021-06-17 17:33
 *
 * @author chenpi
 */
@Data
public class MyMessage {

    public static final int MAGIC_NUMBER = 0x12345678;

    //头部
    private int magicNumber = MAGIC_NUMBER;
    private Integer timestamp;
    private Integer msgId;

    //长度
    private Integer length;

    //消息体

}
