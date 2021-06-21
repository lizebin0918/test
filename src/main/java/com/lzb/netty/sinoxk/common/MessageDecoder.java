package com.lzb.netty.sinoxk.common;

import io.netty.buffer.ByteBuf;
import org.apache.commons.lang3.StringUtils;

public class MessageDecoder {

    public static Object decode(int length, String className, ByteBuf byteBuf) {
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        Class requestType = className(className);
        GZIPJSONSerializer serializer = GZIPJSONSerializer.getInstance();
        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }

    private static Class className(String className) {
        if (StringUtils.isEmpty(className)) {
            return null;
        }
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
