package com.lzb.zk.config;

import com.alibaba.fastjson.JSON;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * 配置类<br/>
 * Created on : 2021-06-20 13:33
 *
 * @author lizebin
 */
public class Config {

    private static final String host = "192.168.56.100:%s";

    private static volatile ZooKeeper zk;

    private final static CountDownLatch latch = new CountDownLatch(1);
    private final static DefaultWatch watch = new DefaultWatch(latch);


    public static ZooKeeper get() {
        if (Objects.isNull(zk)) {
            synchronized (Config.class) {
                if (Objects.isNull(zk)) {
                    try {
                        zk = new ZooKeeper(
                                String.format(host, "2181") + ","
                                        + String.format(host, "2182") + ","
                                        + String.format(host, "2183") + "/test-config",
                                3000, watch);
                        latch.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return zk;
    }
}