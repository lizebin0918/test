package com.lzb.concurrent;

import java.util.concurrent.Semaphore;

/**
 * 随机唤醒一个<br/>
 * Created on : 2021-08-02 23:08
 *
 * @author lizebin
 */
public class TestSemaphore {

    public static void main(String[] args) throws InterruptedException {
        //设置成0，全部阻塞
        int count = 10;
        Semaphore semaphore = new Semaphore(0);
        for (int i = 0; i < count; i++) {
            new Thread(() -> {

                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + ":exit");
            }).start();
        }

        System.out.println("main start sleep");
        Thread.sleep(1000);
        System.out.println("main end sleep");
        semaphore.release(count);

    }

}
