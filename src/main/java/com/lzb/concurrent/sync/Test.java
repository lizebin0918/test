package com.lzb.concurrent.sync;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 只用synchronized实现生产者消费者模式：A线程打印奇数，B线程打印偶数<br/>
 * Created on : 2021-05-19 09:24
 * @author chenpi 
 */
public class Test {

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println(3 & 0);

        AtomicInteger count = new AtomicInteger();
        Object lock = new Object();
        int max = 100;
        Thread a = new Thread(() -> {
            while (count.intValue() < max) {
                synchronized (lock) {
                    if ((count.intValue() & 1) == 0) {
                        System.out.println("a----->" + count.getAndIncrement());
                    }
                }
            }
        });
        Thread b = new Thread(() -> {
            while (count.intValue() < max) {
                synchronized (lock) {
                    if ((count.intValue() & 1) == 1) {
                        System.out.println("b----->" + count.getAndIncrement());
                    }
                }
            }
        });
        a.start();
        b.start();
        a.join();
        b.join();

        System.out.println("done");
    }


}
