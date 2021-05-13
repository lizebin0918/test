package com.lzb.util;

import redis.clients.jedis.*;

import java.util.Set;

public class RedisHelper {

    private final JedisPool pool;

	public static RedisHelper newInstance(String host, int port, String password, int maxIdle, int maxTotal, long maxWaitMillis) {
		return new RedisHelper(host, port, password, maxIdle, maxTotal, maxWaitMillis);
	}
	
	private RedisHelper(String host, int port, String password, int maxIdle, int maxTotal, long maxWaitMillis) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(maxIdle);//最大空闲连接数
	    config.setMaxTotal(maxTotal);//最大连接数
	    config.setTestOnBorrow(true);
	    config.setTestOnReturn(false);
	    config.setMaxWaitMillis(maxWaitMillis);
		pool = new JedisPool(config, host, port, 10000, password);
	}

    /**
     * 没有特别需求，请不要在外部调用此方法
     * @return
     */
	public Jedis getConnection() {
		return pool.getResource();
	}
	
	public void returnConnection(Jedis conn) {
	    //自Jedis3.0版本后jedisPool.returnResource()遭弃用,官方重写了Jedis的close方法用以代替
		if(null != conn) {
		    conn.close();
		}
	}
}