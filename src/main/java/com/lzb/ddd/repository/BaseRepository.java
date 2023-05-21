package com.lzb.ddd.repository;

/**
 * <br/>
 * Created on : 2023-05-21 09:53
 * @author lizebin
 */
public abstract class BaseRepository<R> implements AddRepository<R> {

    protected abstract Runnable doAdd(R r);

    @Override
    public final long add(R r) {
        System.out.println("before add");
        doAdd(r).run();
        System.out.println("after add");
        return 0;
    }
}
