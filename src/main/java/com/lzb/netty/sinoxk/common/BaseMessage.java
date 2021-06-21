package com.lzb.netty.sinoxk.common;

import lombok.Data;

import java.util.UUID;

/**
 * <br/>
 * Created on : 2021-06-21 16:31
 *
 * @author chenpi
 */
@Data
public class BaseMessage {

    public static final int MAGIC_NUMBER = 0x12345678;

    //头部
    private int magicNumber = MAGIC_NUMBER;
    /**
     * 版本号
     */
    private int version = 1;
    /**
     * 时间戳
     */
    private int timestamp = (int)(System.currentTimeMillis() / 1000L);
    /**
     * msgId
     */
    private long msgId = Math.abs(UUID.randomUUID().getLeastSignificantBits());

}
