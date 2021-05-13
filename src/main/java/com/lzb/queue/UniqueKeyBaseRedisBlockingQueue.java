package com.lzb.queue;

import com.alibaba.fastjson.JSON;
import com.lzb.util.RedisHelper;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.concurrent.*;

/**
 * FIFO队列，按照Key合并，例如"key1"在对头，再来一个"key1"，key1对应的value被覆盖，pop() 返回 k1 的值<br/>
 * Created on : 2021-04-30 09:43
 * @author chenpi 
 */
public class UniqueKeyBaseRedisBlockingQueue {

    private static final String REDIS_KEY_QUEUE = "order:detail:queue";
    private static final String REDIS_KEY_HASH = "order:detail:key:hash";

    private static final String PUSH_LUA =
        "local exist = redis.call('hset', KEYS[2], ARGV[1], ARGV[2])\n"//设置最新值
        + "if( exist == 1 )\n"//设置成功，表示原值不存在
        + "then\n"
        + "    redis.call('lpush', KEYS[1], ARGV[1])\n"//入队
        + "end\n";

    private static final String POP_LUA = "local key = redis.call('rpop', KEYS[1])\n"
        + "if(key ~= nil)\n"
        + "then\n"
        + "    local s = redis.call('hget', KEYS[2], key)\n"
        + "    redis.call('hdel', KEYS[2], key)\n"
        + "    return s\n"
        + "end\n";

    private static final List<String> KEYS = Arrays.asList(REDIS_KEY_QUEUE, REDIS_KEY_HASH);

    RedisHelper redisHelper = RedisHelper.newInstance("",
                                                      6380,
                                                      "sinoxk@123",
                                                      5,
                                                      100,
                                                      -1);

    public long size() {
        Jedis connection = null;
        try {
            connection = redisHelper.getConnection();
            return Optional.ofNullable(connection.llen(REDIS_KEY_QUEUE)).orElse(0L);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisHelper.returnConnection(connection);
        }
        return 0L;
    }

    /**
     * 是否被覆盖
     * @param customerId
     * @param day
     * @param fileUrl
     * @return
     */
    public void push(int customerId, int day, String fileUrl) {
        String key = Objects.toString(customerId) + "_" + Objects.toString(day);
        List<String> params = new ArrayList<>();
        params.add(key);
        Node value = new Node();
        value.setCustomerId(customerId);
        value.setFileUrl(fileUrl);
        value.setDay(day);
        params.add(JSON.toJSONString(value));
        Jedis connection = null;
        try {
            connection = redisHelper.getConnection();
            if (Objects.isNull(connection)) {
                return;
            }
            connection.eval(PUSH_LUA, KEYS, params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisHelper.returnConnection(connection);
        }
    }

    /**
     * 返回路径
     * @return
     */
    public Optional<Node> pop() {
        Jedis connection = null;
        try {
            connection = redisHelper.getConnection();
            String value = (String)connection.eval(POP_LUA, KEYS, Collections.emptyList());
            if (StringUtils.isBlank(value)) {
                return Optional.empty();
            }
            return Optional.ofNullable(JSON.parseObject(value, Node.class));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisHelper.returnConnection(connection);
        }
        return Optional.empty();
    }

    @Data
    private static class Node {
        int customerId, day;
        String fileUrl;
    }

    public static void main(String[] args) throws InterruptedException {
        UniqueKeyBaseRedisBlockingQueue queue = new UniqueKeyBaseRedisBlockingQueue();
        int customerId = 1;

        int taskSize = 50;
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(taskSize);

        long start = System.currentTimeMillis();

        for (int i=0; i<taskSize; i++) {
            int version = i;
            threadPool.execute(() -> {
                try {
                    int day = 20210513;
                    queue.push(customerId, day, RandomStringUtils.randomAlphabetic(32));
                    queue.push(customerId, day + 1, RandomStringUtils.randomAlphabetic(32));
                    queue.push(customerId, day + 2, RandomStringUtils.randomAlphabetic(32));
                    queue.push(customerId, day + 3, RandomStringUtils.randomAlphabetic(32));
                    queue.push(customerId, day + 4, RandomStringUtils.randomAlphabetic(32));
                    queue.push(customerId, day + 5, RandomStringUtils.randomAlphabetic(32));
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

        threadPool.shutdown();
        long end = System.currentTimeMillis();

        System.out.println("执行耗时（毫秒）:" + (end - start));//1216 1312
    }

}
