package com.lzb.nio.chat;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 聊天室服务端<br/>
 * Created on : 2021-05-30 16:49
 * @author lizebin
 */
public class NioServer {

    private static final Map<String, SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8080), 1024);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                final SocketChannel client;
                try {
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel server =
                            (ServerSocketChannel)selectionKey.channel();
                        client = server.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                        String uuid = UUID.randomUUID().toString();
                        clientMap.put(uuid, client);
                        System.out.println("客户端连接:");
                        System.out.println(uuid + "---->" + JSON.toJSONString(client));
                    } else if (selectionKey.isReadable()) {
                        client = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int count = client.read(buffer);
                        if (count > 0) {
                            buffer.flip();
                            Charset charset = StandardCharsets.UTF_8;
                            String receiverMsg = String.valueOf(charset.decode(buffer).array());
                            System.out.println(JSON.toJSONString(client) +
                                                   ":" + receiverMsg);

                            String senderKey = null;
                            for (Map.Entry<String, SocketChannel> entry :
                                clientMap.entrySet()) {
                                if (client == entry.getValue()) {
                                    senderKey = entry.getKey();
                                    break;
                                }
                            }

                            for (Map.Entry<String, SocketChannel> entry :
                                clientMap.entrySet()) {
                                SocketChannel value = entry.getValue();
                                ByteBuffer wb = ByteBuffer.allocate(1024);
                                wb.put((senderKey + ":" + receiverMsg).getBytes());

                                wb.flip();

                                value.write(wb);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                iterator.remove();
            }

            /*
            SocketChannel socketChannel = serverSocketChannel.accept();

            System.out.println("客户端连接:" + JSON.toJSONString(socketChannel));

            socketChannel.register(selector, SelectionKey.OP_READ);
            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iter = selectionKeys.iterator();

            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int read = sc.read(buffer);
                    if (read <= 0) {
                        break;
                    }
                    buffer.flip();
                    Charset charset = Charset.forName("utf-8");
                    System.out.println(JSON.toJSONString(sc) + ":" + String.valueOf(charset.decode(buffer).array()));
                }
            }*/
        }

    }

}
