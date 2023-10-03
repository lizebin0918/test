package com.lzb.guava;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

/**
 * <br/>
 * Created on : 2023-10-03 16:34
 * @author lizebin
 */
public class TestSuppliers {

    public static void main(String[] args) {
        Supplier<String> stringSupplier = Suppliers.synchronizedSupplier(Suppliers.memoize(() -> {
            System.out.println("execute hello");
            return "hello";
        }));
        System.out.println(Thread.currentThread() + ":" + stringSupplier.get());
        System.out.println(Thread.currentThread() + ":" + stringSupplier.get());
        System.out.println(Thread.currentThread() + ":" + stringSupplier.get());
        System.out.println(Thread.currentThread() + ":" + stringSupplier.get());
        System.out.println(Thread.currentThread() + ":" + stringSupplier.get());
    }

}
