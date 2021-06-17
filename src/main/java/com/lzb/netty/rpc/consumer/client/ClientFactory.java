package com.lzb.netty.rpc.consumer.client;

import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * client工厂<br/>
 * Created on : 2021-06-15 23:01
 *
 * @author lizebin
 */
public class ClientFactory {

    public static final ClientFactory INSTANCE = new ClientFactory();

    public static ClientFactory getInstance() {
        return INSTANCE;
    }

    int poolSize = 1;

    /**
     * 一个Providor对应一个连接池
     */
    ConcurrentHashMap<String, Pool> providorPool = new ConcurrentHashMap<>();

    public synchronized NioSocketChannel getClient(String providor) {
        Pool pool = providorPool.get(providor);
        if (Objects.isNull(pool)) {
            pool = new Pool(poolSize);
            providorPool.putIfAbsent(providor, pool);
        }

        int i = ThreadLocalRandom.current().nextInt(poolSize);
        NioSocketChannel client = pool.clients[i];
        if (Objects.isNull(client)) {

        }
        return null;
    }

}
