package com.lzb.zk.lock;

import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.TimeUnit;

/**
 * 测试类<br/>
 * Created on : 2021-06-20 15:13
 *
 * @author lizebin
 */
public class Main {

    public static void main(String[] args) {
        ZooKeeper zk = Config.get();

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                WatchCallback wc = new WatchCallback(zk, Thread.currentThread().getName());
                wc.lock();

                System.out.println(Thread.currentThread().getName() + " working ");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                wc.unlock();
            });
            t.start();
        }

        while(true) {

        }
    }

}
