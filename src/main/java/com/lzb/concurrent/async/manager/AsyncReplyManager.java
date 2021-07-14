package com.lzb.concurrent.async.manager;

import com.lzb.concurrent.async.model.AsyncReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 同步转异步回应管理器<br/>
 * Created on : 2016-10-19 08:50
 * @author lizebin
 */
public class AsyncReplyManager {

    private static final Logger log = LoggerFactory.getLogger(AsyncReplyManager.class);

    private final static ConcurrentHashMap<String, AsyncReply> manager = new ConcurrentHashMap<>(512, 0.75f, 32);

    private AsyncReplyManager() {}

    private static class InnerClass {
        private static final AsyncReplyManager instance = new AsyncReplyManager();
    }

    public static AsyncReplyManager getInstance() {
        return InnerClass.instance;
    }

    /**
     * msgId 保证全局唯一<br/>
     * Created on : 2016-10-19 09:05
     * @author lizebin
     * @version V1.0.0
     * @param msgId
     * @param asyncReply
     * @return
     */
    public AsyncReply put(String msgId, AsyncReply asyncReply) {
        return manager.put(msgId, asyncReply);
    }

    /**
     * 获取异步回应<br/>
     * Created on : 2016-10-19 09:07
     * @author lizebin
     * @version V1.0.0
     * @param msgId
     * @return 如果不存在，则为null
     */
    public AsyncReply get(String msgId) {
        return manager.get(msgId);
    }

    public boolean remove(String msgId, AsyncReply asyncReply) {
        return manager.remove(msgId, asyncReply);
    }

    public AsyncReply remove(String msgId) {
        return manager.remove(msgId);
    }

    public int size() {
        return manager.size();
    }
}