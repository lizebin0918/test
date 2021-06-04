package com.lzb.nio;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 零拷贝测试<br/>
 * Created on : 2021-06-03 22:59
 * @author lizebin
 */
public class TestZeroCopyServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 表示等待第四次断开连接的端口，可以被重新利用？
        // TCP关闭过程中的TIME_WAIT状态就是client端的2MSL状态，
        // 其作用是确保server端可以收到client端发送的确认报文：
        // 最后一个确认报文可能没被server端收到，此时server端会重发fin报文，
        // client端等待2MSL时间使得可以收到server端重发的fin报文。
        serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        serverSocketChannel.bind(new InetSocketAddress(8080), 1024);
        ServerSocket serverSocket = serverSocketChannel.socket();

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(true);

            int readCount = 0;
            while (readCount != -1) {
                try {
                    readCount = socketChannel.read(byteBuffer);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                byteBuffer.rewind();
            }
        }



    }

}
