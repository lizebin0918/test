package com.lzb.jmm;

import java.util.concurrent.CountDownLatch;

/**
 * 重排序现象，偶发<br/>
 * Created on : 2021-05-20 23:04
 *
 * @author lizebin
 */
public class OutOfOrderExecution {

    public static int x = 0, y = 0;
    public static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        Thread threadA = new Thread(() -> {

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            a = 1;
            x = b;
        });

        Thread threadB = new Thread(() -> {

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            b = 1;
            y = a;
        });

        threadA.start();
        threadB.start();
        latch.countDown();

        threadA.join();
        threadB.join();

        //不可能情况:x = y = 0
        System.out.println("x = " + x + " y = " + y);

    }

}
