package com.lzb.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

/**
 * 客户端<br/>
 * Created on : 2021-05-30 19:31
 * @author lizebin
 */
public class NioClient {

    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        while (true) {
            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                if (key.isConnectable()) {
                    SocketChannel socket = (SocketChannel) key.channel();
                    if (socket.isConnectionPending()) {
                        socket.finishConnect();

                        ByteBuffer wb = ByteBuffer.allocate(1024);

                        wb.put("连接成功".getBytes());
                        wb.flip();

                        socket.write(wb);
                    }
                    socket.register(selector, SelectionKey.OP_READ);
                }
            }

        }

    }

}
