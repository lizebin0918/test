package com.lzb.concurrent.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ScheduledTask implements Runnable {

    public void run() {
        System.out.println("开始做任务了，好开心。。。。");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("如果有bug(error/exception)出现话，这个定时任务还能不能继续执行呢? ");
    }

    public static void main(String[] args) throws InterruptedException {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new ScheduledTask(), 1, 1, TimeUnit.SECONDS);
        Thread.sleep(10000);
    }

}