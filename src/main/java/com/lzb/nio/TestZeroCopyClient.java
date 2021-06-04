package com.lzb.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;

/**
 * 零拷贝测试<br/>
 * Created on : 2021-06-03 22:59
 * @author lizebin
 */
public class TestZeroCopyClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8080));
        socketChannel.configureBlocking(true);

        String fileName = "/Users/lizebin/Desktop/WeCom_3.1.6.90148.dmg";

        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long start = System.currentTimeMillis();
        long transferCount = fileChannel.transferTo(0, fileChannel.size(),
                                           socketChannel);
        long end = System.currentTimeMillis();

        System.out.println("总字节数：" + transferCount + ",耗时："+ (end - start));

        fileChannel.close();


    }

}
