package com.lzb.netty.sinoxk.common;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

public class GZIPJSONSerializer {

    private static final GZIPJSONSerializer serializer = new GZIPJSONSerializer();

    private GZIPJSONSerializer() {}

    public static GZIPJSONSerializer getInstance() {
        return serializer;
    }

    public byte[] serialize(Object object){
        try {
            return GZIPUtils.compress(JSON.toJSONBytes(object));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

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
