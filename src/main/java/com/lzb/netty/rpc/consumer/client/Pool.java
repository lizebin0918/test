package com.lzb.netty.rpc.consumer.client;

import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Arrays;

/**
 * 客户端连接池<br/>
 * Created on : 2021-06-15 22:55
 *
 * @author lizebin
 */
public class Pool {

    NioSocketChannel[] clients;
    Object[] locks;

    public Pool(int size) {
        clients = new NioSocketChannel[size];
        locks = new Object[size];
        for (int i = 0; i < locks.length; i++) {
            locks[i] = new Object();
        }
    }
}
