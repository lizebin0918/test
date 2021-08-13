package com.lzb.jdk;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * -Xmx10M
 * 线程销毁，ThreadLocal是否会内存溢出？<br/>
 * Created on : 2021-08-13 13:49
 *
 * @author lizebin
 */
public class TestThreadLocal {

    public static final ThreadLocal<byte[]> LOCAL = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        int times = 10;

        /*{
            List<byte[]> list = new ArrayList<>();
            for (int i = 0; i < times; i++) {
                list.add(new byte[1024 * 1024]);
            }
        }*/

        /*{
            for (int i = 0; i < times * 2; i++) {
                new Thread(() -> {
                    LOCAL.set(new byte[1024 * 1024]);
                }).start();
                gc();
            }
        }*/

        /*{
            ExecutorService threadPool = Executors.newCachedThreadPool();

            for (int i = 0; i < times; i++) {
                threadPool.execute(() -> {
                    LOCAL.set(new byte[1024 * 1024]);
                    // 没有remove会导致内存溢出
                    //LOCAL.remove();
                });
                gc();
            }

            Thread.sleep(1000);
            threadPool.shutdown();
        }*/

        System.out.println("done");
        Thread.sleep(5000);
    }

    public static void gc() {
        //尽量保证GC
        System.gc();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
