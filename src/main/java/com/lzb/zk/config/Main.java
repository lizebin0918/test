package com.lzb.zk.config;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * <br/>
 * Created on : 2021-06-20 13:43
 *
 * @author lizebin
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        ZooKeeper zk = Config.get();

        WatchCallback wc = new WatchCallback(zk);

        wc.aWait();

        while (true) {
            Thread.sleep(1000);
            System.out.println("--------------");
            System.out.println(wc.getData());
            System.out.println("--------------");
        }
    }
}
