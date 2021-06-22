package com.lzb.manager;

import com.lzb.ApplicationTests;
import com.lzb.redis.QueueManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.LongAdder;

/**
 * 队列管理器测试<br/>
 * Created on : 2021-05-18 10:55
 * @author chenpi 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTests.class)
public class QueueManagerTest {

    @Resource
    private QueueManager queueManager;

    @Test
    public void test() throws Exception {
        int threadSize = 5;

        //每个线程生成1-max个数，随机放到redis队列，所有线程执行完，redis队列只有max个元素
        int max = 1000;
        CountDownLatch readCdl = new CountDownLatch(threadSize);
        for (int i = 0; i < threadSize; i++) {
            Thread push = new Thread(() -> {
                try {
                    List<Integer> numList = new ArrayList<>(max);
                    for (int num = 1; num <= max; num++) {
                        numList.add(num);
                    }
                    //乱序，增加冲突
                    Collections.shuffle(numList);
                    numList.forEach(num -> {
                        queueManager.push(Objects.toString(num), Objects.toString(num));
                    });
                } finally {
                    readCdl.countDown();
                }
            });
            push.start();
        }
        readCdl.await();
        System.out.println("push完毕，队列个数:" + queueManager.size());

        LongAdder sum = new LongAdder();
        for (int i = 0; i < threadSize; i++) {
            Thread pop = new Thread(() -> {
                while (true) {
                    queueManager.blockingPop().map(Long::valueOf).ifPresent(sum::add);
                }
            });
            pop.start();
        }

        //每隔一秒钟打印sum
        Thread printSum = new Thread(() -> {
           while (true) {
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               System.out.println(sum.longValue());
           }
        });
        printSum.start();
        printSum.join();

    }

}
