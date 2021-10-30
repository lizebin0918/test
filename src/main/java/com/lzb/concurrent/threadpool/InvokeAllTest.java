package com.lzb.concurrent.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * invokeAll测试<br/>
 * Created on : 2021-10-08 21:41
 *
 * @author lizebin
 */
public class InvokeAllTest {

    public static void main(String[] args) throws InterruptedException {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("start sleep");
                Thread.sleep(10000);
                System.out.println("end sleep");
                return 0;
            }
        };

        int i = 100;
        List<Callable<Integer>> list = new ArrayList<>();
        list.add(callable);
        list.add(callable);
        list.add(callable);
        list.add(callable);
        list.add(callable);
        list.add(callable);
        list.add(callable);
        list.add(callable);
        list.add(callable);
        list.add(callable);
        list.add(callable);
        list.add(callable);

        int corePoolSize = 5;
        int maximumPoolSize = 10;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(200);
        ThreadPoolExecutor t = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue);

        // 物流时效定时 and a6b015b2596d7dcf
        List<Future<Integer>> fl = t.invokeAll(list, 5, TimeUnit.SECONDS);
        fl.forEach(f -> {
            try {
                System.out.println(f.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

}
