package com.lzb.cache;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

/**
 * 数据库和redis缓存一致性<br/>
 *
 * 所有方案都是基于场景
 *
 * 方案一(强一致性)：MySQL事务和Redis缓存更新操作在同一个事务
 * 方案二(弱一致性，存在误差)：更新数据，删除缓存，后续查询从数据库加载
 *  问题：
 *  先前缓存刚好失效
 *  请求A查数据库，得到旧值
 *  请求B更新数据库
 *  请求B删除缓存
 *  请求A将旧值写入缓存
 *
 *  0.上面问题通过超时时间兜底
 *  1.更新数据库，事务提交，删除缓存
 *  2.AtomicBoolean 控制只有一个线程（A）能操作
 *  3.A线程查询数据写缓存，并设置有效期（兜底，防止旧数据），其他线程自旋查询缓存是否有更新
 *  4.多台集群，多个节点同时操作3，只有一个能成功，其他线程马上可见，并不会把查询都打到数据库
 *  5.是否能解决缓存击穿和缓存雪崩？
 *
 * Created on : 2021-05-30 20:16
 * @author lizebin
 */
public class TestMySQLAndRedisConsistency {


    @Data
    private static class UserAccount {
        private Integer id;
        /**
         * 账户余额
         */
        private BigDecimal balance;
        /**
         * 账户名称
         */
        private String name;

        private int version = (int) (System.currentTimeMillis() / 1000L);
    }

    private final ConcurrentHashMap<Integer, UserAccount> cache =
        new ConcurrentHashMap<>();

    private final ReentrantLock reentrantLock = new ReentrantLock(true);

    /**
     * reentrant lock 版本
     *
     * @param id
     * @return
     */
    public Optional<UserAccount> readByReentrantLock(int id) {
        UserAccount ua = get(id);
        if (Objects.nonNull(ua)) return Optional.of(ua);
        reentrantLock.lock();
        try {
            while (Objects.isNull(ua = get(id))) {
                ua = new UserAccount();
                ua.setId(id);
                ua.setBalance(new BigDecimal(id));
                ua.setName(Objects.toString(id));
                ua.setVersion(id);
                cache.put(id, ua);
                System.out.println(Thread.currentThread().getName() + ":更新---");
            }
        } finally {
            reentrantLock.unlock();
        }
        return Optional.of(ua);
    }

    private final StampedLock stampedLock = new StampedLock();

    /**
     * stampedLock 版本
     *
     * @param id
     * @return
     */
    public Optional<UserAccount> readByStampedLock(int id) {
        UserAccount ua = get(id);
        if (Objects.nonNull(ua)) return Optional.of(ua);
        long stamp = stampedLock.readLock();
        try {
            while (Objects.isNull(ua = get(id))) {
                long ws = stampedLock.tryConvertToWriteLock(stamp);
                if (ws != 0L) {
                    stamp = ws;
                    ua = new UserAccount();
                    ua.setId(id);
                    ua.setBalance(new BigDecimal(id));
                    ua.setName(Objects.toString(id));
                    ua.setVersion(id);
                    cache.put(id, ua);
                    System.out.println(Thread.currentThread().getName() + ":更新---");
                    break;
                } else {
                    stampedLock.unlockRead(stamp);
                    stamp = stampedLock.writeLock();
                }
            }
        } finally {
            stampedLock.unlock(stamp);
        }
        return Optional.of(ua);
    }

    private final AtomicBoolean isUpdating = new AtomicBoolean(false);

    /**
     * 导致多线程在CAS
     *
     * @param id
     * @return
     */
    public Optional<UserAccount> readByAtomicBoolean(int id) {
        UserAccount ua = get(id);
        if (Objects.nonNull(ua)) return Optional.of(ua);
        while (Objects.isNull(ua = get(id))) {
            if (isUpdating.compareAndSet(false, true)) {
                ua = new UserAccount();
                ua.setId(id);
                ua.setBalance(new BigDecimal(id));
                ua.setName(Objects.toString(id));
                ua.setVersion(id);
                cache.put(id, ua);
                System.out.println(Thread.currentThread().getName() + ":更新---");
                isUpdating.compareAndSet(true, false);
                break;
            }
        }
        return Optional.of(ua);
    }

    Semaphore semaphore = new Semaphore(1);
    /**
     * 利用信号量
     *
     * @param id
     * @return
     */
    public Optional<UserAccount> readBySemphore(int id) {
        UserAccount ua = get(id);
        if (Objects.nonNull(ua)) return Optional.of(ua);
        try {
            while (Objects.isNull(ua = get(id)) && semaphore.tryAcquire(1,
                                                                        TimeUnit.SECONDS)) {
                try {
                    ua = new UserAccount();
                    ua.setId(id);
                    ua.setBalance(new BigDecimal(id));
                    ua.setName(Objects.toString(id));
                    ua.setVersion(id);
                    cache.put(id, ua);
                    System.out.println(Thread.currentThread().getName() + ":更新---");
                } finally {
                    semaphore.release();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.of(ua);
    }

    /**
     * @param id
     * @return
     */
    public UserAccount get(int id) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cache.get(id);
    }


    public static void main(String[] args) throws InterruptedException {
        int threadSize = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        CyclicBarrier barrier = new CyclicBarrier(threadSize);
        ExecutorService threadPool = Executors.newCachedThreadPool();
        TestMySQLAndRedisConsistency t = new TestMySQLAndRedisConsistency();

        long start = System.currentTimeMillis();
        for (int i = 0; i < threadSize; i++) {
            threadPool.execute(() -> {
                try {
                    barrier.await();
                    /*System.out.println(Thread.currentThread().getName() + ":"
                                           + JSON.toJSONString(t
                                           .readByReentrantLock(1)));*/
                    /*System.out.println(Thread.currentThread().getName() + ":"
                                           + JSON.toJSONString(t
                                           .readByStampedLock(1)));*/
                    /*System.out.println(Thread.currentThread().getName() +
                                           ":" + JSON.toJSONString(t.readByAtomicBoolean(1)));*/
                    System.out.println(Thread.currentThread().getName() + ":" + JSON.toJSONString(t.readBySemphore(1)));
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        long end = System.currentTimeMillis();

        System.out.println("耗时(毫秒):" + (end - start));

        threadPool.shutdown();
    }

}
