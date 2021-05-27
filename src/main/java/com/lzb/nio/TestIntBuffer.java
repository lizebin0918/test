package com.lzb.nio;

import java.nio.IntBuffer;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Buffer简单示例<br/>
 * Created on : 2021-05-27 21:45
 * @author lizebin
 */
public class TestIntBuffer {

    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        System.out.println("capacity:" + buffer.capacity());
        for (int i = 0; i < buffer.capacity() ; i++) {
            buffer.put(ThreadLocalRandom.current().nextInt(100));
        }

        //等价于:buffer.limit(buffer.position()).position(0);
        buffer.flip();

        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }

    }

}
