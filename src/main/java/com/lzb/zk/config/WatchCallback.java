package com.lzb.zk.config;

import jdk.nashorn.internal.runtime.regexp.joni.WarnCallback;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

import static org.apache.zookeeper.Watcher.Event.EventType.NodeCreated;
import static org.apache.zookeeper.Watcher.Event.EventType.NodeDataChanged;

/**
 * 反复回调<br/>
 * Created on : 2021-06-20 14:22
 *
 * @author lizebin
 */
public class WatchCallback implements Watcher, AsyncCallback.StatCallback, AsyncCallback.DataCallback {

    private ZooKeeper zk;
    private CountDownLatch latch = new CountDownLatch(1);
    private static final String ctx = "abc";
    private static final String type = "/order";
    private String data;

    public WatchCallback(ZooKeeper zk) {
        this.zk = zk;
    }

    public void aWait() {
        zk.exists(type, this, this, ctx);
        try {
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processResult(int i, String path, Object ctx, byte[] bytes, Stat stat) {
        System.out.println("processResult: node is created");
        if (bytes != null) {
            data = new String(bytes);
            latch.countDown();
        }
    }

    @Override
    public void processResult(int i, String s, Object o, Stat stat) {
        System.out.println("processResult: node is exist");
        if (stat != null) {
            zk.getData(type, this, this, ctx);
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("process");
        Event.EventType type = watchedEvent.getType();
        if (NodeCreated == type || type == NodeDataChanged) {
            zk.getData(this.type, this, this, ctx);
        }
    }

    public String getData() {
        return data;
    }
}
