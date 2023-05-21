package com.lzb.ddd.repository;

/**
 * <br/>
 * Created on : 2023-05-21 09:47
 * @author lizebin
 */
public class OrderRepository extends BaseRepository<Order> implements AddRepository<Order> {
    @Override
    protected Runnable doAdd(Order order) {
        return () -> System.out.println("execute doAdd");
    }

}
