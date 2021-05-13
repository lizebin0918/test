package com.lzb.queue;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * FIFO队列，按照Key合并，例如"key1"在对头，再来一个"key1"，key1对应的value被覆盖，pop() 返回 k1 的值<br/>
 * Created on : 2021-04-30 09:43
 * @author chenpi 
 */
public class UniqueKeyBlockingQueue {

    private ConcurrentHashMap<String, Node> map = new ConcurrentHashMap<>();

    private LinkedBlockingQueue<Node> queue = new LinkedBlockingQueue<>();

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * 是否被覆盖
     * @param customerId
     * @param day
     * @param version
     * @param fileUrl
     * @return
     */
    public  boolean push(int customerId, int day, int version, String fileUrl) {
        Node node = new Node(customerId, day, version, fileUrl);
        String key = node.getKey();
        synchronized(key.intern()) {
            Node oldNode = map.put(key, node);
            if (Objects.isNull(oldNode)) {
                queue.offer(node);
                return true;
            } else {
                //队列存在，判断版本号，只保留最新的node
                if (oldNode.version > node.version) {
                    map.put(key, oldNode);
                }
            }
            return false;
        }
    }

    /**
     * 返回路径
     * @return
     */
    public Optional<Node> pop() {
        Node node = queue.poll();
        if (Objects.nonNull(node)) {
            String key = node.getKey();
            Node mNode = map.remove(key);
            if (Objects.isNull(mNode)) return Optional.empty();
            node.fileUrl = mNode.fileUrl;
            node.version = mNode.version;
            return Optional.ofNullable(node);
        }
        return Optional.empty();
    }

    public void forEach(Consumer<Node> consumer) {
        queue.forEach(consumer::accept);
    }

    @Data
    private static class Node {
        int customerId, version, day;
        String fileUrl;

        Node() {}

        Node(int customerId, int day, int version, String fileUrl) {
            this.customerId = customerId;
            this.day = day;
            this.version = version;
            this.fileUrl = fileUrl;
        }

        public String getKey() {
            return Objects.toString(customerId) + "_" + Objects.toString(day);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        UniqueKeyBlockingQueue queue = new UniqueKeyBlockingQueue();
        int customerId = 1;

        int taskSize = 5000;
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(taskSize);

        for (int i=0; i<taskSize; i++) {
            int version = i;
            threadPool.execute(() -> {
                try {
                    Random r = new Random();
                    Thread.sleep(r.nextInt(10) * 41);
                    int day = 20210513;
                    queue.push(customerId, day, version, Objects.toString(version) + "-" + day);
                    Thread.sleep(r.nextInt(10) * 31);
                    queue.push(customerId, day + 1, version, Objects.toString(version) + "-" + (day + 1));
                    Thread.sleep(r.nextInt(10) * 89);
                    queue.push(customerId, day + 2, version, Objects.toString(version) + "-" + (day + 2));
                    Thread.sleep(r.nextInt(10) * 23);
                    queue.push(customerId, day + 3, version, Objects.toString(version) + "-" + (day + 3));
                    Thread.sleep(r.nextInt(10) * 17);
                    queue.push(customerId, day + 4, version, Objects.toString(version) + "-" + (day + 4));
                    Thread.sleep(r.nextInt(10) * 71);
                    queue.push(customerId, day + 5, version, Objects.toString(version) + "-" + (day + 5));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        System.out.println(JSON.toJSONString(queue.pop().get()));
        System.out.println(JSON.toJSONString(queue.pop().get()));
        System.out.println(JSON.toJSONString(queue.pop().get()));
        System.out.println(JSON.toJSONString(queue.pop().get()));
        System.out.println(JSON.toJSONString(queue.pop().get()));
        System.out.println(JSON.toJSONString(queue.pop().get()));
        System.out.println("queue.size() = " + queue.size());
        System.out.println("map.size() = " + queue.map.size());
        threadPool.shutdown();
    }

}
