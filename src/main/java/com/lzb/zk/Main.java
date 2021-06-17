package com.lzb.zk;

import com.alibaba.fastjson.JSON;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 主类<br/>
 * Created on : 2021-06-17 22:02
 *
 * @author lizebin
 */
public class Main {

    public static final String host = "192.168.56.100:%s";

    public static void main(String[] args) throws Exception {

        CountDownLatch latch = new CountDownLatch(1);

        //zk是有seesion概念的，没有连接池的概念
        //watch:观察、回调
        //1.new zk时候，传入的watch是session级别的
        ZooKeeper zooKeeper = new ZooKeeper(
                String.format(host, "2181") + ","
                + String.format(host, "2182") + ","
                + String.format(host, "2183"), 3000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("---------------------");
                System.out.println("watch callback event:" + JSON.toJSONString(watchedEvent));
                System.out.println("---------------------");

                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    latch.countDown();
                }

            }
        });

        latch.await();

        //异步连接
        ZooKeeper.States state = zooKeeper.getState();
        System.out.println(state.toString());

        //4种类型的节点：持久、临时；顺序、无序
        String pathName = zooKeeper.create("/user", "lizebin".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println(pathName);

        //stat放元数据
        Stat stat = new Stat();
        byte[] node = zooKeeper.getData("/user", new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("------updateNode------");
                System.out.println(JSON.toJSONString(watchedEvent));
            }
        }, stat);

        System.out.println("--------------node------------");
        System.out.println(new String(node));
        System.out.println("--------------stat------------");
        System.out.println(JSON.toJSONString(stat));

        System.out.println("update---------------->");

        zooKeeper.setData("/user", "lizebin-1".getBytes(), 0);
        Stat stat1 = new Stat();
        byte[] node1 = zooKeeper.getData("/user", false, stat);

        System.out.println("--------------node1------------");
        System.out.println(new String(node1));
        System.out.println("--------------stat1------------");
        System.out.println(JSON.toJSONString(stat1));

    }

}
