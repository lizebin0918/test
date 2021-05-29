package com.lzb.nio.demo;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static com.lzb.nio.demo.Utils.*;


// 通过BIO的这种单线程处理方式，我们的QPS 2.0
public class BIODemo {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT, BACK_LOG, null);
        System.out.println("启动服务器");
        for (; ; ) {
            Socket socket = serverSocket.accept();
            System.out.println(socket.getRemoteSocketAddress());
            OutputStream outputStream = socket.getOutputStream();
            BufferedWriter bufferedWriter = buildBufferedWriter(outputStream);
            doSomeWork();
            bufferedWriter.write(buildHttpResp());
            bufferedWriter.flush();
        }
    }
}
