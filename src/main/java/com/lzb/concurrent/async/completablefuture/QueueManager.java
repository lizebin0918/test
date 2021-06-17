package com.lzb.concurrent.async.completablefuture;

import com.lzb.concurrent.async.model.OrderDto;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 队列管理器<br/>
 * Created on : 2021-06-16 11:59
 *
 * @author chenpi
 */
public class QueueManager {

    private static final ConcurrentHashMap<Integer, OrderDto> queue = new ConcurrentHashMap<>();

    public boolean offer(OrderDto orderDto) {
        return false;
    }
}
