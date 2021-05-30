package com.lzb.nio;

import com.alibaba.fastjson.JSON;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 网络socket示例<br/>
 * Created on : 2021-05-30 08:45
 * @author lizebin
 */
public class TestServerSocket {

    public static void main(String[] args) {
        int[] ports = new int[5];

        ports[0] = 0xFFFF;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;

        try (Selector selector = Selector.open()) {

            for (int port : ports) {
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false);
                ServerSocket serverSocket = serverSocketChannel.socket();
                serverSocket.bind(new InetSocketAddress(port), 1024);

                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

                System.out.println("监听端口:" + port);
            }

            while (true) {
                int numbers = selector.select();
                System.out.println("numbers:" + numbers);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> iter = selectionKeys.iterator();

                while (iter.hasNext()) {
                    SelectionKey selectionKey = iter.next();

                    //accept -> read
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel serverSocketChannel =
                            (ServerSocketChannel) selectionKey.channel();
                        SocketChannel socketChannel =
                            serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        //获取客户端连接，并把对应事件注册到Selector
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        System.out.println("获得客户端链接:" + JSON.toJSONString(socketChannel));
                    }
                    //read 事件，返回SocketChannle
                    else if (selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                        int br = 0;
                        while (true) {
                            byteBuffer.clear();
                            int read = socketChannel.read(byteBuffer);
                            if (read <= 0) {
                                break;
                            }
                            byteBuffer.flip();
                            socketChannel.write(byteBuffer);
                            br += read;
                        }
                    }
                    iter.remove();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }




    }

}
