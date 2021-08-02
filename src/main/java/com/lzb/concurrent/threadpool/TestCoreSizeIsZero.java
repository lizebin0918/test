package com.lzb.concurrent.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试核心线程数为0的情况<br/>
 * Created on : 2021-08-02 22:46
 *
 * @author lizebin
 */
public class TestCoreSizeIsZero {

    public static void main(String[] args) {
        /**
         * 实际把任务直接放到队列，产生新的线程处理，再根据keepalive销毁
         */
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(0, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        threadPool.execute(() -> {
            System.out.println("test");
        });

    }

}
