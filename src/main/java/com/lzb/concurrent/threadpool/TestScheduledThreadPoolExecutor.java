package com.lzb.concurrent.threadpool;

import com.lzb.singleton.EnumSingleton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 定时线城池<br/>
 * Created on : 2021-08-08 16:57
 *
 * @author lizebin
 */
public class TestScheduledThreadPoolExecutor {

    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(16);
        System.out.println(Thread.currentThread().getName() + "current time:" + LocalDateTime.now());
        // 延迟5秒后执行
        scheduledExecutorService.schedule(() -> System.out.println("delay execute :" + LocalDateTime.now()), 5, TimeUnit.SECONDS);

        // 按照时间点执行
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println(Thread.currentThread().getName() + " execute at fixed rate : " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);

        // 按照任务执行 + 固定时间执行
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            System.out.println(Thread.currentThread().getName() + " execute with fixed delay : " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);

        Thread.sleep(10000);
        System.out.println(Thread.currentThread().getName() + "done");

        scheduledExecutorService.shutdown();

    }

}
