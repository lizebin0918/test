package com.lzb.concurrent.redisson;

import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

import java.util.concurrent.CompletableFuture;

/**
 * 分布式公平锁测试<br/>
 * Created on : 2021-06-03 21:10
 * @author lizebin
 */
public class Test {

    private static final RedissonClient redisson;
    static {
        Config config = new Config();
        config.setCodec(StringCodec.INSTANCE)
            .useSingleServer().setAddress("redis://192.168.56.100:6379")
            .setSslEnableEndpointIdentification(false);
        redisson = Redisson.create(config);
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("-------------------");

        RBucket<String> key = redisson.getBucket("1", StringCodec.INSTANCE);
        System.out.println(key.get());

        //Note:这个key不能跟其他键冲突
        RLock lock = redisson.getLock("lock");
        for (int i = 0; i < 10; i++) {
            CompletableFuture.runAsync(() -> {
                String tn = Thread.currentThread().getName();
                lock.lock();
                System.out.println(tn + " get lock ");
                try {
                    System.out.println(tn + " start sleep");
                    Thread.sleep((long) (Math.random() * 500L));
                    System.out.println(tn + " start end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            });
        }

        Thread.sleep(100000000);
    }

}
