package com.lzb.ddd.repository;

import javax.annotation.Resource;

import lombok.Setter;

/**
 * <br/>
 * Created on : 2023-05-21 09:53
 * @author lizebin
 */
@Setter
public abstract class BaseRepository<R> implements AddRepository<R> {

    // 受保护变量才能设置进去，不能是private
    /*@Resource
    protected TransactionHelper transactionHelper;

    @Resource
    protected DomainEventSender domainEventSender;

    @Resource
    protected OperationLogSupport operationLogSupport;*/

    protected abstract Runnable doAdd(R r);

    @Override
    public final long add(R r) {
        System.out.println("before add");
        doAdd(r).run();
        System.out.println("after add");
        return 0;
    }
}
