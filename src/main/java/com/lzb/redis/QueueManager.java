package com.lzb.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 订单明细队列管理器，只有一个key的数据存在于队列中
 * list + hash实现，lua保证原子性
 * <br/>
 * Created on : 2021-05-17 17:21
 * @author chenpi 
 */
@Slf4j
@Component
public class QueueManager {

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 队列名称
     */
    public static final String REDIS_KEY_ORDER_DETAIL_QUEUE = "test:etl:order:detail:queue";
    /**
     * 队列消息映射
     */
    public static final String REDIS_KEY_ORDER_DETAIL_HASH = "test:etl:order:detail:hash";

    private static final String PUSH_LUA =
        "local exist = redis.call('hset', KEYS[2], ARGV[1], ARGV[2])\n"//设置最新值
            + "if( exist == 1 )\n"//设置成功，表示原值不存在
            + "then\n"
            + "    return redis.call('lpush', KEYS[1], ARGV[1])\n"//入队，返回当前队列长度
            + "end\n"
            + "return redis.call('llen', KEYS[1])\n";

    private static final DefaultRedisScript<Long> PUSH_LUA_SCRIPT = new DefaultRedisScript<>(PUSH_LUA, Long.class);

    private static final String POP_LUA =
        "local key = redis.call('rpop', KEYS[1])\n"//出队（不支持阻塞读）
            + "if key then\n"
            + "    local s = redis.call('hget', KEYS[2], key)\n"//根据key获取映射值
            + "    redis.call('hdel', KEYS[2], key)\n"
            + "    return s\n"
            + "end\n";

    private static final DefaultRedisScript<String> POP_LUA_SCRIPT = new DefaultRedisScript<>(POP_LUA, String.class);

    /**
     * 读锁
     */
    private final ReentrantLock popLock = new ReentrantLock();

    /**
     * 非空标识
     */
    private final Condition notEmpty = popLock.newCondition();

    /**
     * 队列为空，阻塞等待
     * @throws InterruptedException
     */
    private void awaitEmpty() throws InterruptedException {
        try {
            popLock.lockInterruptibly();
            notEmpty.await();
        } finally {
            popLock.unlock();
        }
    }

    /**
     * 唤醒等待的消费者
     * @throws InterruptedException
     */
    private void signalNotEmpty() throws InterruptedException {
        try {
            popLock.lockInterruptibly();
            notEmpty.signalAll();
        } finally {
            popLock.unlock();
        }
    }

    /**
     *
     * @param key 唯一键
     * @param value 实际的value
     * @return
     */
    public void push(String key, String value) {
        Long result = redisTemplate.execute(PUSH_LUA_SCRIPT,
                                            Arrays.asList(REDIS_KEY_ORDER_DETAIL_QUEUE, REDIS_KEY_ORDER_DETAIL_HASH),
                                            key, value);
        //队列长度
        if (result > 0) {
            try {
                signalNotEmpty();
            } catch (InterruptedException e) {
                log.error("订单明细入队，获取锁等待被中断");
                //中断线程，返回，外部调用者判断中断标识位
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 队列rpop元素
     *
     * @return value
     */
    public Optional<String> blockingPop() {
        String result = null;
        while (!Thread.currentThread().isInterrupted()
            && Objects.isNull(result = redisTemplate.execute(POP_LUA_SCRIPT, Arrays.asList(REDIS_KEY_ORDER_DETAIL_QUEUE, REDIS_KEY_ORDER_DETAIL_HASH)))) {
            try {
                awaitEmpty();
            } catch (InterruptedException e) {
                //防止redis连不上，打爆日志
                log.error("订单明细出队等待，被中断");
                //中断线程，返回，外部调用者判断中断标识位
                Thread.currentThread().interrupt();
            }
        }
        return Optional.ofNullable(result);
    }

    /**
     * 队列长度
     * @return
     */
    public long size() {
        return redisTemplate.opsForList().size(REDIS_KEY_ORDER_DETAIL_QUEUE);
    }
}