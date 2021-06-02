package com.lzb.reference;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试对外内存回收<br/>
 * -XX:MaxDirectMemorySize=20m -verbose:gc -XX:+PrintGCDetails
 * -XX:+DisableExplicitGC
 * Created on : 2021-06-02 06:45
 * @author lizebin
 */
public class TestDirectByteBuffer {

    public static void main(String[] args) {
        testDirectByteBuffer();
    }

    public static void testDirectByteBuffer() {
        List<ByteBuffer> list = new ArrayList<ByteBuffer>();
        while(true) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(1 * 1024);
            //list.add(buffer);
        }
    }

}
