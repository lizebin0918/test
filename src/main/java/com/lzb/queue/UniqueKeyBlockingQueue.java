package com.lzb.queue;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * FIFO队列，按照Key合并，例如"key1"在对头，再来一个"key1"，key1对应的value被覆盖，pop() 返回 k1 最新version的值<br/>
 * Created on : 2021-04-30 09:43
 * @author chenpi 
 */
public class UniqueKeyBlockingQueue<T> {

    private ConcurrentHashMap<String, Node<T>> map = new ConcurrentHashMap<>();

    private ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * @param key
     * @param value
     * @param version 如果冲突，只保留最新的版本号
     * @return
     */
    public void push(String key, T value, int version) {
        Node node = new Node(key, value, version);
        Node oldNode = map.putIfAbsent(key, node);
        if (Objects.isNull(oldNode)) {
            queue.offer(key);
            return;
        }
        //队列存在，判断版本号，只保留最新版本的node
        if (oldNode.version < version) {
            oldNode.version = version;
            oldNode.value = value;
        }
    }

    /**
     * 返回对应value
     * @return 对应value
     */
    public Optional<T> pop() {
        String key = queue.poll();
        if (Objects.nonNull(key)) {
            Node<T> node = map.remove(key);
            if (Objects.isNull(node)) return Optional.empty();
            return Optional.ofNullable(node.getValue());
        }
        return Optional.empty();
    }

    @Data
    private static class Node<T> {
        private T value;
        private String key;
        private int version;

        Node() {}

        Node(String key, T value, int version) {
            this.key = key;
            this.value = value;
            this.version = version;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UniqueKeyBlockingQueue<String> queue = new UniqueKeyBlockingQueue();
        int customerId = 1;

        int taskSize = 10000;
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(taskSize);

        long start = System.currentTimeMillis();

        for (int i=0; i<taskSize; i++) {
            int version = i;
            threadPool.execute(() -> {
                try {
                    int day = 20210513;
                    queue.push(String.valueOf(customerId) + "_" + String.valueOf(day + 1), Objects.toString(version + 1), version);
                    queue.push(String.valueOf(customerId) + "_" + String.valueOf(day + 2), Objects.toString(version + 2), version);
                    queue.push(String.valueOf(customerId) + "_" + String.valueOf(day + 3), Objects.toString(version + 3), version);
                    queue.push(String.valueOf(customerId) + "_" + String.valueOf(day + 4), Objects.toString(version + 4), version);
                    queue.push(String.valueOf(customerId) + "_" + String.valueOf(day + 5), Objects.toString(version + 5), version);
                    queue.push(String.valueOf(customerId) + "_" + String.valueOf(day + 6), Objects.toString(version + 6), version);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        //多线程读
        for (int i=0,size=queue.size(); i<size; i++) {
            threadPool.execute(() -> {
                while (!queue.isEmpty()) {
                    System.out.println(JSON.toJSONString(queue.pop().get()));
                }
            });
        }

        threadPool.shutdown();

        long end = System.currentTimeMillis();

        System.out.println("执行耗时（毫秒）:" + (end - start));//2600-3200
    }

}
