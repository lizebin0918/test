package com.lzb.zk.config;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

/**
 * 默认监听器<br/>
 * Created on : 2021-06-20 13:39
 *
 * @author lizebin
 */
public class DefaultWatch implements Watcher {

    private CountDownLatch latch;

    public DefaultWatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public DefaultWatch() {}

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            latch.countDown();
        }
    }
}
