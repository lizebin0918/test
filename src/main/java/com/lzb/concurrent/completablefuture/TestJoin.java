package com.lzb.concurrent.completablefuture;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <br/>
 * Created on : 2021-10-25 10:24
 *
 * @author lizebin
 */
public class TestJoin {

    public static void main(String[] args) {

        int taskCount = 3;
        List<CompletableFuture<Void>> tasks = new ArrayList<>();

        for (int i = 1; i <= taskCount; i++) {
            tasks.add(taskRun(i));
        }

        tasks.stream().map(CompletableFuture::join).collect(Collectors.toList());

        System.out.println("done:" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

    }

    public static CompletableFuture<Void> taskRun(int sleepSeconds) {
        return CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "-开始睡眠(" + sleepSeconds + "秒):" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            try {
                TimeUnit.SECONDS.sleep(sleepSeconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "-结束睡眠:" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        });
    }

}
