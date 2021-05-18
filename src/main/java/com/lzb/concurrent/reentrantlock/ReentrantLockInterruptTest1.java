package com.lzb.concurrent.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock中断测试<br/>
 * Created on : 2021-05-17 11:46
 * @author chenpi 
 */
public class ReentrantLockInterruptTest1 {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();

        Thread t = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println("before lock");
                    lock.lockInterruptibly();
                    System.out.println("lock success");
                } catch (InterruptedException e) {
                    System.out.println("lock 线程被打断");
                    Thread.currentThread().interrupt();
                }
            }
        });

        System.out.println("main lock");
        lock.lock();
        t.start();
        Thread.sleep(500);
        System.out.println("打断lock");
        t.interrupt();
        Thread.sleep(500);
        System.out.println("exit");
    }

}
