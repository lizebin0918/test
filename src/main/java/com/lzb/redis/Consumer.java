package com.lzb.redis;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * 消息消费者:启动固定线程数，死循环消费，防止死循环打爆日志<br/>
 * Created on : 2021-06-22 09:51
 *
 * @author chenpi
 */
@Slf4j
@Component
public class Consumer implements ApplicationRunner, DisposableBean {

    @Value("${size:0}")
    private int size;

    @Resource
    private QueueManager queueManager;

    protected final ExecutorService threadPool = Executors.newCachedThreadPool(
        new ThreadFactoryBuilder().setNameFormat("order-detail-%d").build());

    @Override
    public void destroy() throws Exception {
        threadPool.shutdown();
        if (!threadPool.awaitTermination(5, TimeUnit.SECONDS)) {
            //中断线程
            threadPool.shutdownNow();
        }

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 0; i < size; i++) {
            threadPool.execute(() -> {
                while (!Thread.currentThread().isInterrupted() && !threadPool.isShutdown()) {
                    queueManager.blockingPop().ifPresent(value -> {
                        //执行具体的业务逻辑
                    });
                }
                log.info("轮询线程退出");
            });
        }
    }
}
