package com.lzb.netty.handler.util;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

public class GZIPJSONSerializer implements Serializer{

    @Override
    public byte[] serialize(Object object){
        try {
            return GZIPUtils.compress(JSON.toJSONBytes(object));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        try {
            byte[] gzip = GZIPUtils.decompress(bytes);
            return JSON.parseObject(gzip, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
