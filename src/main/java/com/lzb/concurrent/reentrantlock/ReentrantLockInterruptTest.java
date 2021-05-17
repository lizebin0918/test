package com.lzb.concurrent.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock中断测试<br/>
 * Created on : 2021-05-17 11:46
 * @author chenpi 
 */
public class ReentrantLockInterruptTest {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();

        Thread t = new Thread(() -> {
            boolean isLock = false;
            try {
                System.out.println("before lock");
                lock.lockInterruptibly();
                //isLock = lock.tryLock(10, TimeUnit.SECONDS);
                /*if (!isLock) {
                    System.out.println("lock failure return");
                    return;
                }*/
                System.out.println("lock success");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                if (isLock) {
                    lock.unlock();
                }
            }
        });

        System.out.println("main lock");
        lock.lock();
        t.start();
        Thread.sleep(500);
        //lock.unlock();
        //System.out.println("main unlock");
        //t.interrupt();
        Thread.sleep(500);
        System.out.println("exit");


    }

}
