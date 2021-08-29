package com.lzb.jdk;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * -Xmx10M
 * 线程销毁，ThreadLocal是否会内存泄露？究竟是哪个对象泄露？？？<br/>
 * 一、采用线程池会有可能会导致内存泄露，这里有几个点需要注意：
 *   如果直接线程消亡，ThreadLocalMap会自动被回收，只讨论线程池的情况
 *   我们接收的HTTP请求，用的是Tomcat的线程池
 *   我们自定义的线程池
 * 二、内存泄露？遗漏的是啥？
 *   漏的是TL被回收了，但是Entry的value没有被回收（因为线程还活着），就算同一个线程对同一个TL做多次set()方法，旧的value会被覆盖，旧的value会被回收掉
 *   如果是强引用，因为线程池长时间存在，Thread->ThreadLocalMap->Entry->Entry.key->ThreadLocal，这样会导致Entry无法回收，value就会被遗忘在内存中。
 *   如果是弱引用，TL被回收了，只有弱引用，每次GC会把key的引用断掉，ThreadLocalMap在get/set的时候会把对应的Entry清空，这样也释放了value
 * 三、什么情况下，TL会被回收？？？？这个点有点迷.....情况估计是有的，但是我们没遇到而已
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

        // 非线程池的线程结束后会自动回收ThreadLocalMap
        /*{
            for (int i = 0; i < times * 2; i++) {
                new Thread(() -> {
                    LOCAL.set(new byte[1024 * 1024]);
                }).start();
                gc();
            }
        }*/

        {
            ExecutorService threadPool = Executors.newSingleThreadExecutor();

            for (int i = 0; i < 100; i++) {
                threadPool.execute(() -> {
                    LOCAL.set(new byte[2 * 1024 * 1024]);
                    // 没有remove会导致内存溢出
                    // LOCAL.remove();
                });
                // gc();
            }
            Thread.sleep(1000);
            threadPool.shutdown();
        }

        /*InheritableThreadLocal<String> stringInheritableThreadLocal = new InheritableThreadLocal<>();
        stringInheritableThreadLocal.set("InheritableThreadLocal string");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + ":" + stringInheritableThreadLocal.get());
                gc();
            }).start();
        }

        Thread.sleep(5000);
        System.out.println("done");*/

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
