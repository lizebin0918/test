# test
日常调试程序

## Java
* "强软弱虚"引用:`com.lzb.reference`
* 缓存行:`com.lzb.cache_line`
* JMM测试:`com.lzb.jmm`

## redis
* list队列增加set属性，保证队列只有一份数据，list + hash实现，lua保证原子性:[`com.lzb.redis.QueueManager`](https://github.com/lizebin0918/test/blob/main/src/main/java/com/lzb/redis/QueueManager.java)
* 多线程消费者:[`com.lzb.redis.Consumer`](https://github.com/lizebin0918/test/blob/main/src/main/java/com/lzb/redis/Consumer.java)

## netty
* rpc-demo:`com.lzb.netty.rpc`
* 自定义编解码:`com.lzb.netty.sinoxk`

## nio
* reactor单线程模型:`com.lzb.nio.reactor_single`
* reactor主从模型:`com.lzb.nio.reactor_mul_main_sub`
* non-blocking IO(无selector):`com.lzb.nio.NioServerSocket`

## interview
* 数据库缓存一致性（单机版）:`com.lzb.cache.TestMySQLAndRedisConsistency`

## zookeeper
* 分布式配置demo:`com.lzb.zk.config`
* 分布式锁demo:`com.lzb.zk.lock`

