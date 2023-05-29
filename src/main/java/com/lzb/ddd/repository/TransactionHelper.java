package com.lzb.ddd.repository;

/**
 * <br/>
 * Created on : 2023-05-29 19:04
 * @author mac
 */
public class TransactionHelper {

    public void execute(Runnable runnable) {
        System.out.println("事务执行-------------------");
        runnable.run();
        System.out.println("事务执行-------------------");
    }

}
