package com.lzb.ddd.repository;

import java.util.function.LongSupplier;

/**
 * <br/>
 * Created on : 2023-05-21 09:47
 * @author lizebin
 */
public class OrderRepository extends BaseRepository<Order> implements AddRepository<Order> {
    @Override
    protected LongSupplier doAdd(Order order) {
        System.out.println("before doAdd");
        return () -> {
            System.out.println("execute doAdd");
            return 1L;
        };
    }

}
