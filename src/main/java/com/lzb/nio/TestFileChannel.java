package com.lzb.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 测试FileChannel输入输出<br/>
 * Created on : 2021-05-27 23:05
 * @author lizebin
 */
public class TestFileChannel {

    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("input.txt");
        FileOutputStream outputStream = new FileOutputStream("output.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true) {

            //必须clear，把之前的游标重置
            //如果不重置，会一直写文件，因为read=0，死循环了，为什么？
            //1.假设数据是一次性写回channel，这个时候 position = limit
            //2.不调用clear，这个时候read=0，因为inputChannel不能再往buffer写数据了
            //3.inputChannel还没到结尾，但是数据没写进去，read就等于0了
            buffer.clear();

            System.out.println(buffer.limit());
            System.out.println(buffer.position());

            int read = inputChannel.read(buffer);
            System.out.println(buffer.limit());
            System.out.println(buffer.position());

            System.out.println(read);

            if (read == -1) {
                break;
            }

            buffer.flip();

            System.out.println("flip = " + buffer.limit());
            System.out.println("flip = " + buffer.position());

            outputChannel.write(buffer);

            System.out.println(buffer.limit());
            System.out.println(buffer.position());
        }

        inputChannel.close();
        outputChannel.close();

    }

}
