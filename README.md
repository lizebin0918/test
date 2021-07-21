# test
* 日常调试程序
* jdk14需要添加模块
```  
--add-opens=java.base/jdk.internal.misc=ALL-UNNAMED 
--add-opens=java.base/jdk.internal.ref=ALL-UNNAMED 
-Dio.netty.tryReflectionSetAccessible=true
```
## Java
* "强软弱虚"引用:[`com.lzb.reference`](https://github.com/lizebin0918/test/tree/main/src/main/java/com/lzb/reference)
* 缓存行:[`com.lzb.cache_line`](https://github.com/lizebin0918/test/tree/main/src/main/java/com/lzb/cache_line)
* JMM测试:[`com.lzb.jmm`](https://github.com/lizebin0918/test/tree/main/src/main/java/com/lzb/jmm)

## redis
* list队列增加set属性，保证队列只有一份数据，list + hash实现，lua保证原子性:[`com.lzb.redis.QueueManager`](https://github.com/lizebin0918/test/blob/main/src/main/java/com/lzb/redis/QueueManager.java)
* 多线程消费者:[`com.lzb.redis.Consumer`](https://github.com/lizebin0918/test/blob/main/src/main/java/com/lzb/redis/Consumer.java)

## netty
* rpc-demo:[`com.lzb.netty.rpc`](https://github.com/lizebin0918/test/tree/main/src/main/java/com/lzb/netty/rpc)
* netty自定义编解码:[`com.lzb.netty.sinoxk`](https://github.com/lizebin0918/test/tree/main/src/main/java/com/netty/sinoxk)

## nio
* reactor单线程模型:[`com.lzb.nio.reactor_single`](https://github.com/lizebin0918/test/tree/main/src/main/java/com/lzb/nio/reactor_single)
* reactor主从模型:[`com.lzb.nio.reactor_mul_main_sub`](https://github.com/lizebin0918/test/tree/main/src/main/java/com/lzb/nio/reactor_mul_main_sub)
* non-blocking IO(无selector):[`com.lzb.nio.NioServerSocket`](https://github.com/lizebin0918/test/blob/main/src/main/java/com/lzb/nio/NioServerSocket.java)

## interview
* 数据库缓存一致性（单机版）:[`com.lzb.cache.TestMySQLAndRedisConsistency`](https://github.com/lizebin0918/test/blob/main/src/main/java/com/lzb/cache/TestMySQLAndRedisConsistency.java)

## zookeeper
* 分布式配置demo:[`com.lzb.zk.config`](https://github.com/lizebin0918/test/tree/main/src/main/java/com/lzb/zk/config)
* 分布式锁demo:[`com.lzb.zk.lock`](https://github.com/lizebin0918/test/tree/main/src/main/java/com/lzb/zk/lock)

## 同步转异步
* 单条插入转批量:[`com.lzb.concurrent.completablefuture.SingleToMultipleApi`](https://github.com/lizebin0918/test/tree/main/src/main/java/com/lzb/concurrent/completablefuture/SingleToMultipleApi.java)
* 基于CountDownLatch实现同步请求，异步回应:[`com.lzb.concurrent.async.Client`](https://github.com/lizebin0918/test/tree/main/src/main/java/com/lzb/concurrent/async/Client.java)
