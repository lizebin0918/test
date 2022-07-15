package com.lzb.concurrent.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import jodd.system.SystemUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * <br/>
 * Created on : 2022-03-18 22:51
 *
 * @author cidervisioncase
 */
@Slf4j
public class TestThreadName {

    protected static final ExecutorService threadPool1 = Executors.newCachedThreadPool(
            new ThreadFactoryBuilder().setNameFormat("order-detail-%d").build());

    protected static final ExecutorService threadPool2 = Executors.newCachedThreadPool(
            new ThreadFactoryBuilder().setNameFormat("order-detail-%d").build());

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            threadPool1.shutdown();
            threadPool2.shutdown();
            System.out.println("线程池停止");
        }));
    }

    public static void main(String[] args) throws InterruptedException {
        threadPool1.execute(() -> {
            log.info("test");
        });
        threadPool2.execute(() -> {
            log.info("test");
        });
        Thread.sleep(10000);

        int i = 2;
        if (i == 1)
            System.out.println("a");
            // 会打印....
            System.out.println("b");
        System.out.println("c");

    }

}
