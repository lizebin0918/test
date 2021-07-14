package com.lzb.concurrent.async.model;

import com.lzb.concurrent.async.manager.AsyncReplyManager;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

public abstract class AsyncReply<REQ, RSP> {

    private final String msgId;

    private final CountDownLatch latch;

    private final RSP nil = null;

    private final AtomicReference<RSP> atomicReference = new AtomicReference<>(nil);

    public AsyncReply(String msgId) {
        this.msgId = msgId;
        this.latch = new CountDownLatch(1);
    }

    public RSP getReply(REQ request, int timeout, TimeUnit unit) throws Exception {
        try {
            AsyncReplyManager.getInstance().put(msgId, this);
            send(request);
            if (!this.latch.await(timeout, unit)) {
                throw new TimeoutException("请求超时");
            }
            return atomicReference.get();
        } catch (Exception e) {
            throw e;
        } finally {
            AsyncReplyManager.getInstance().remove(msgId);
        }
    }

    public void setReply(RSP reply) {
        if(atomicReference.compareAndSet(this.nil, reply)) {
            this.latch.countDown();
        }
    }

    public abstract void send(REQ request) throws Exception;
}