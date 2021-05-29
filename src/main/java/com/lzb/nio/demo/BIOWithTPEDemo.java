package com.lzb.nio.demo;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

import static com.lzb.nio.demo.Utils.*;

/**
 * @author hj
 * @version 1.0
 * @description: 采用BIO和线程池 QPS 130.0
 * @date 2021/5/17 21:07
 */
public class BIOWithTPEDemo {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT, BACK_LOG, null);
        System.out.println("启动服务器");
        ThreadPoolExecutor threadPoolExecutor = buildThreadPoolExecutor();
        for (; ; ) {
            Socket socket = serverSocket.accept();
            threadPoolExecutor.execute(() -> {
                OutputStream outputStream;
                BufferedWriter bufferedWriter;
                try {
                    System.out.println(socket.getRemoteSocketAddress());
                    outputStream = socket.getOutputStream();
                    bufferedWriter = buildBufferedWriter(outputStream);
                    doSomeWork();
                    bufferedWriter.write(buildHttpResp());
                    bufferedWriter.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
