package com.lzb.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

/**
 * 字节缓冲区<br/>
 * Created on : 2021-06-25 22:02
 *
 * @author lizebin
 */
public class TestByteBuff {

    public static void main(String[] args) {
        ByteBuf buf = Unpooled.buffer(10);

        ByteBuf buf1 = Unpooled.copiedBuffer("李hello world", StandardCharsets.UTF_8);

        //true:堆上缓冲
        if (buf1.hasArray()) {
            byte[] content = buf1.array();
            System.out.println(new String(content));

            System.out.println(buf1.arrayOffset());//0
            System.out.println(buf1.readerIndex());//0
            System.out.println(buf1.writerIndex());//11

            //writeIndex - readIndex
            int length = buf1.readableBytes();
            System.out.println(length);

            for (int i = 0; i < buf1.readableBytes(); i++) {
                System.out.println((char)buf1.getByte(i));
            }

            //getCharSequence
            System.out.println(buf1.getCharSequence(0, buf1.readableBytes(), StandardCharsets.UTF_8));

            //复合缓冲区
            CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
            ByteBuf heapBuf = Unpooled.buffer(10);
            ByteBuf directBuf = Unpooled.directBuffer(8);
            compositeByteBuf.addComponents(heapBuf, directBuf);

        }


    }

}
