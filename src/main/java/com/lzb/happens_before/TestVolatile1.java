package com.lzb.happens_before;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 模拟一个线程读取volatile，再读取另一个非volatile变量，当前线程是否能读取到其他变量的最新值，下面程序怎么修改，才不会被x==0 && y==0满足退出？<br/>
 * Created on : 2021-05-23 22:31
 *
 * @author lizebin
 */
public class TestVolatile1 {

    public static int x = 0, y = 0;
    public static int a = 0;
    public static volatile int b = 0;

    public static void main(String[] args) throws Exception {
        int counter = 0;

        while (true) {

            x = y = a = b = 0;

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
            System.out.println((counter++) + " x = " + x + " y = " + y);
            if (x == 0 && y == 0) {
                break;
            }
        }
    }

}
