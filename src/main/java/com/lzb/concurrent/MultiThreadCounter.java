package com.lzb.concurrent;

import com.alibaba.fastjson.JSON;

import java.util.*;
import java.util.concurrent.*;

/**
 * 线程池统计数据<br/>
 * Created on : 2021-05-08 19:32
 *
 * @author lizebin
 */
public class MultiThreadCounter {

    public static void main(String[] args) throws InterruptedException {
        int sum = 0, end = 10000;

        //构造数据
        Random random = new Random();
        Queue<Number> queue = new LinkedBlockingQueue<>();
        for (int i = 1; i <= end; i++) {
            int number = random.nextInt(100);
            queue.add(new Number(number));
            sum += number;
        }

        int threadSize = 10;
        ExecutorService threadPool = Executors.newFixedThreadPool(threadSize);
        ConcurrentHashMap<Thread, List<Number>> threadMap = new ConcurrentHashMap<>();
        CountDownLatch latch = new CountDownLatch(threadSize);

        for (int i = 0; i < threadSize; i++) {
            threadPool.execute(() -> {
                try {
                    while (!queue.isEmpty()) {
                        //模拟耗时操作
                        Thread currentThread = Thread.currentThread();
                        Thread.sleep(random.nextInt(5) * 3);
                        Number item = queue.poll();
                        if (Objects.isNull(item)) {
                            break;
                        }
                        threadMap.computeIfAbsent(currentThread, (thread) -> new LinkedList<>()).add(item);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        //归并
        int mergeSum = threadMap.values().stream().filter(Objects::nonNull).flatMap(List::stream).mapToInt(Number::getNumber).sum();

        assert sum == mergeSum;

        System.out.println("sum:" + sum);
        System.out.println("mergeSum:" + mergeSum);

        threadPool.shutdownNow();
    }

    private static class Number {
        private int number;

        Number(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }
    }

}
