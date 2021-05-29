package com.lzb.nio.demo;

import java.io.*;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class Utils {
    private static final String HTTP_SEPARATOR = "\r\n";
    public static final int DEFAULT_PORT = 8888;
    // 如果设置过小，高并发下容易出现connect refuse。原理是影响了：accept 队列的大小
    public static final int BACK_LOG = 1024;

    public static String buildHttpResp() {
        StringBuilder stringBuilder = new StringBuilder();
        String str = "<h1>hello world</h1>";
        stringBuilder.append("HTTP/1.1 200 OK").append(HTTP_SEPARATOR);
        stringBuilder.append("connection: Close").append(HTTP_SEPARATOR);
        stringBuilder.append("content-type: text/html").append(HTTP_SEPARATOR);
        stringBuilder.append("content-length: " + str.length()).append(HTTP_SEPARATOR);
        stringBuilder.append(HTTP_SEPARATOR);
        stringBuilder.append(str);
        return stringBuilder.toString();
    }

    public static ThreadPoolExecutor buildThreadPoolExecutor() {
        return new ThreadPoolExecutor(100, 100, 0,
                TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static BufferedWriter buildBufferedWriter(OutputStream outputStream) {
        return new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    public static BufferedReader buildBufferedReader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    public static void doSomeWork() throws InterruptedException {
        Thread.sleep(500);
    }
}
