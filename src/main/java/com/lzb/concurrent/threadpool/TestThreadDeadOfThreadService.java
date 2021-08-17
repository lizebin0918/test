package com.lzb.concurrent.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 测试线程池的线程消亡时间<br/>
 * Created on : 2021-08-17 08:35
 *
 * @author lizebin
 */
public class TestThreadDeadOfThreadService {

    public static void main(String[] args) {
        int taskCount = 16;

        // keepalive = 0 表示执行完马上消亡
        ExecutorService threadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                0L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        for (int i = 0; i < taskCount; i++) {
            CompletableFuture.runAsync(() -> {
                System.out.println("1");
            }, threadPool);
        }

    }

    /**
     * 打印线程
     */
    public static void printThreads() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        // 遍历线程组树，获取根线程组
        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }
        // 激活的线程数加倍
        int estimatedSize = topGroup.activeCount() * 2;
        Thread[] slackList = new Thread[estimatedSize];
        // 获取根线程组的所有线程
        int actualSize = topGroup.enumerate(slackList);
        // copy into a list that is the exact size
        Thread[] list = new Thread[actualSize];
        System.arraycopy(slackList, 0, list, 0, actualSize);
        System.out.println("Thread list size == " + list.length);
        for (Thread thread : list) {
            System.out.println(thread.getName());
        }
    }

}
