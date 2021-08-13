package com.lzb.reference;

import java.lang.ref.*;
import java.util.Date;
import java.util.Objects;

/**
 * 弱引用<br/>
 * Created on : 2021-05-31 22:46
 * @author lizebin
 */
public class MyWeakReference {

    public static void main(String[] args) throws InterruptedException {
        A a = new A();
        WeakReference<A> r = new WeakReference<>(a);

        System.out.println(r.get());
        // 需要把这个对象的强引用解除
        // a = null;
        System.gc();
        System.out.println(r.get());

    }

    private static class A {
        @Override
        protected void finalize() throws Throwable {
            System.out.println("gc reclaim");
        }
    }

}
