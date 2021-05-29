package com.lzb.nio.demo;

import java.nio.ByteBuffer;

public class BufferDemo {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println(byteBuffer);
        byteBuffer.putInt(1);
        byteBuffer.putInt(2);
        byteBuffer.putInt(3);
        System.out.println(byteBuffer);
        byteBuffer.flip();
        System.out.println(byteBuffer.remaining());
        System.out.println(byteBuffer);
        int anInt = byteBuffer.getInt();
        byteBuffer.mark();
        int anInt1 = byteBuffer.getInt();
        int anInt2 = byteBuffer.getInt();
        System.out.println(anInt);
        System.out.println(anInt1);
        System.out.println(anInt2);
        byteBuffer.reset();
        int anInt3 = byteBuffer.getInt();
        int anInt4 = byteBuffer.getInt();
        System.out.println(anInt3);
        System.out.println(anInt4);
        System.out.println(byteBuffer);
        System.out.println(byteBuffer.remaining());
        byteBuffer.clear();
        System.out.println(byteBuffer);
        System.out.println(byteBuffer.remaining());
    }
}
