package com.lzb.netty.sinoxk.common;

import io.netty.buffer.ByteBuf;

public class MessageEncoder {

    private static final int MAGIC_NUMBER = 0x12345678;

    public static void encode(ByteBuf byteBuf, BaseMessage message) {
        // 1. 创建 ByteBuf 对象
        GZIPJSONSerializer serializer = GZIPJSONSerializer.getInstance();
        // 2. 序列化 Java 对象
        byte[] bytes = serializer.serialize(message);
        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);//4字节
        byteBuf.writeInt(message.getVersion());//1字节
        byteBuf.writeInt(message.getTimestamp());//1字节(时间戳)
        byteBuf.writeLong(message.getMsgId());//8字节(msgId)
        byte[] className = message.getClass().getName().getBytes();
        int length = className.length;
        byteBuf.writeInt(length);//类长度4
        byteBuf.writeBytes(className);//全类名
        byteBuf.writeInt(bytes.length);//4字节，类大小
        byteBuf.writeBytes(bytes);//消息体

        //System.out.println("message encode:");
        //System.out.println(new String(byteBuf.array()));
    }
}
