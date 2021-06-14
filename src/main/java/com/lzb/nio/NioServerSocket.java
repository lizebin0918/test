package com.lzb.nio;

import com.alibaba.fastjson.JSON;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 非阻塞服务端<br/>
 * Created on : 2021-06-14 19:23
 *
 * @author lizebin
 */
public class NioServerSocket {

    private static final List<SocketChannel> clients = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(true);
        serverSocketChannel.bind(new InetSocketAddress(8080), 1024);

        while (true) {
            SocketChannel client = serverSocketChannel.accept();
            if (Objects.nonNull(client)) {
                System.out.println("client connect:" + client.getRemoteAddress());
                client.configureBlocking(false);
                clients.add(client);
            }
            for (SocketChannel c : clients) {
                StringBuilder sb = new StringBuilder();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                boolean hasText = false;
                while (true) {
                    if (c.read(buffer) <= 0) {
                        break;
                    }
                    hasText = true;
                    buffer.flip();
                    sb.append(String.valueOf(StandardCharsets.UTF_8.decode(buffer).array()));
                    buffer.clear();
                }
                if (hasText) {
                    System.out.println("client(" + c.getRemoteAddress() + ") read:" + sb.toString());
                    c.write(ByteBuffer.wrap(sb.toString().getBytes()));
                    hasText = false;
                }
            }
        }
    }
}
