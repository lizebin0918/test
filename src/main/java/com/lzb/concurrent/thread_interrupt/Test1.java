package com.lzb.concurrent.thread_interrupt;

import java.util.concurrent.TimeUnit;

/**
 * 测试线程中断<br/>
 * Created on : 2021-06-28 23:47
 *
 * @author lizebin
 */
public class Test1 {

    public static void main(String[] args) throws InterruptedException {
        OneThread t = new OneThread();
        t.start();
        TimeUnit.SECONDS.sleep(5);
        t.interrupt();
        System.out.println("main done");
    }

    static class OneThread extends Thread {
        @Override
        public void run() {
            //Thread.interrupted():这是重置 interrupted 标识
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("i am running");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("线程退出");
        }
    }

}
