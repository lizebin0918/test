package com.lzb.ddd.repository;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.LongSupplier;

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

    protected TransactionHelper transactionHelper = new TransactionHelper();

    protected abstract LongSupplier doAdd(R r);

    @Override
    public final long add(R r) {
        System.out.println("before add");
        AtomicReference<Long> id = new AtomicReference<>();
        LongSupplier longSupplier = doAdd(r);
        transactionHelper.execute(() -> {
            id.set(longSupplier.getAsLong());
        });
        System.out.println("after add");
        return id.get();
    }
}
