package com.lzb.nio;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 内存映射<br/>
 * Created on : 2021-05-29 16:54
 * @author lizebin
 */
public class TestMappedByteBuffer {

    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("input.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        MappedByteBuffer mappedByteBuffer =
            fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        //直接操作内存
        mappedByteBuffer.put(0, (byte)'a');
        mappedByteBuffer.put(1, (byte)'b');
        mappedByteBuffer.put(2, (byte)'c');

        randomAccessFile.close();

    }

}
