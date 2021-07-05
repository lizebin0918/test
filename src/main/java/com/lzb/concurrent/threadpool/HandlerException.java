package com.lzb.concurrent.threadpool;

import java.util.concurrent.*;

/**
 * 线程池如何处理异常<br/>
 * Created on : 2021-07-05 22:04
 *
 * @author lizebin
 */
public class HandlerException {

    //1.实现一个自己的线程池工厂
    ThreadFactory factory = (Runnable r) -> {
        //创建一个线程
        Thread t = new Thread(r);
        //给创建的线程设置UncaughtExceptionHandler对象 里面实现异常的默认逻辑
        t.setDefaultUncaughtExceptionHandler((Thread thread1, Throwable e) -> {
            System.out.println("线程工厂设置的exceptionHandler" + e.getMessage());
        });
        return t;
    };

    //2.创建一个自己定义的线程池，使用自己定义的线程工厂
    ExecutorService service = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(10), factory);

    //定义线程池
    ExecutorService service1 = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(10)) {

        //重写afterExecute方法
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            if (t != null) { //这个是excute提交的时候
                System.out.println("afterExecute里面获取到异常信息" + t.getMessage());
            }
            //如果r的实际类型是FutureTask 那么是submit提交的，所以可以在里面get到异常
            if (r instanceof FutureTask) {
                try {
                    Future<?> future = (Future<?>) r;
                    future.get();
                } catch (Exception e) {
                    //log.error("future里面取执行异常", e);
                }
            }
        }
    };

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        try {
            threadPool.execute(() -> {
                Thread.currentThread().setUncaughtExceptionHandler((thread, exception) -> {
                    System.out.println("线程执行异常" + Thread.currentThread().getName());
                    exception.printStackTrace();
                });
                int i = 1 / 0;
            });
        } catch (Exception e) {
            System.out.println("------------------");
            e.printStackTrace();
        }

        threadPool.shutdownNow();
    }

}
