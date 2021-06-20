package com.lzb.zk.lock;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.apache.zookeeper.Watcher.Event.EventType.NodeCreated;
import static org.apache.zookeeper.Watcher.Event.EventType.NodeDataChanged;

/**
 * 反复回调<br/>
 * Created on : 2021-06-20 14:22
 *
 * @author lizebin
 */
public class WatchCallback implements Watcher,
        AsyncCallback.StringCallback,
        AsyncCallback.Children2Callback, AsyncCallback.StatCallback {

    private ZooKeeper zk;
    private static final String ctx = "abc";
    private CountDownLatch latch = new CountDownLatch(1);
    private final String threadName;
    private String pathName;

    public WatchCallback(ZooKeeper zk, String threadName) {
        this.zk = zk;
        this.threadName = threadName;
    }

    public void lock() {
        try {
            zk.create("/lock",
                    threadName.getBytes(),
                    //临时顺序节点
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL,
                    this,
                    ctx);
            latch.await();
            System.out.println(Thread.currentThread().getName() + " locked ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unlock() {
        System.out.println(Thread.currentThread().getName() + " unlock : " + pathName);
        try {
            zk.delete(pathName, -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        switch (watchedEvent.getType()) {
            case None:
                break;
            case NodeCreated:
                break;
            case NodeDeleted:
                zk.getChildren("/", false, this, ctx);
                break;
            case NodeDataChanged:
                break;
            case NodeChildrenChanged:
                break;
            case DataWatchRemoved:
                break;
            case ChildWatchRemoved:
                break;
            case PersistentWatchRemoved:
                break;
        }
    }

    @Override
    public void processResult(int i, String s, Object o, List<String> children, Stat stat) {
        System.out.println("children:" + children);
        if (children == null) {
            return;
        }
        Collections.sort(children);
        int index = children.indexOf(pathName.substring(1));
        if (index < 0) {
            return;
        }
        if (index == 0) {
            System.out.println(threadName + " get lock ");
            latch.countDown();
        } else {
            //找到前一个节点，后续前一个节点删除
            zk.exists("/" + children.get(index - 1), this, this, ctx);
        }
    }

    @Override
    public void processResult(int i, String path, Object o, String nodeName) {
        if (StringUtils.isNotBlank(nodeName)) {
            System.out.println(threadName + " create node: " + path);
            System.out.println(threadName + " create node: " + nodeName);
            zk.getChildren("/", false, this, ctx);
            pathName = nodeName;
        }
    }

    @Override
    public void processResult(int i, String s, Object o, Stat stat) {

    }
}
