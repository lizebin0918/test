package com.lzb.concurrent.threadpool;

import java.util.concurrent.*;

/**
 * 测试线程池拒绝策略<br/>
 * Created on : 2021-08-01 23:14
 *
 * @author lizebin
 */
public class TestThreadPoolReject {

    public static void main(String[] args) {
        //最大处理的任务数为20，超过20之后，采用Caller execute会产生背压效应，生产者的生产速度会因为消费速度变慢而变慢
        ExecutorService threadPool = new ThreadPoolExecutor(10, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(10), Executors.defaultThreadFactory(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                if (!executor.isShutdown()) {
                    r.run();
                }
                System.out.println("拒绝策略执行...");
            }
        });

        for (int i = 0; i < 23; i++) {
            threadPool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " start sleep");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " end sleep");
            });
        }
    }
}
