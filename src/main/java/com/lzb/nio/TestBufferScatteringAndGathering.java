package com.lzb.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * buffer拼接、拆解<br/>
 * nc 调试
 * Created on : 2021-05-29 17:11
 * @author lizebin
 */
public class TestBufferScatteringAndGathering {

    public static void main(String[] args) throws IOException {

        //例如一个消息是分消息头（10个字节），消息体（未知长度），这样就可以用一个Buffer接收消息头，
        //一个接收消息体

        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(8080));

        int msgLength = 2 + 3 + 4;

        ByteBuffer[] buffers = new ByteBuffer[3];

        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        SocketChannel socket = serverSocket.accept();

        while (true) {
            int br = 0;

            while (br < msgLength) {
                long r = socket.read(buffers);
                br += r;

                System.out.println("br=" + br);

                Arrays.stream(buffers).map(buffer ->
                    "position:" + buffer.position()
                        + "limit:" + buffer.limit()).forEach(System.out::println);
            }

            Arrays.stream(buffers).forEach(Buffer::flip);

            long bw = 0;
            while (bw < msgLength) {
                long r = socket.write(buffers);
                bw += r;
            }

            Arrays.stream(buffers).forEach(Buffer::clear);

            System.out.println("br=" + br + ";bw=" + bw);

        }

    }

}
