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
        Date date = new Date();
        ReferenceQueue<Date> queue = new ReferenceQueue<>();
        WeakReference<Date> r = new WeakReference<>(date, queue);

        new Thread(() -> {
            Reference<? extends Date> gc = null;
            while (Objects.isNull(gc)) {
                gc = queue.poll();
            }
            //只会返回引用Reference，对应的对象已经被回收了
            System.out.println("被gc回收:" + gc.get());
        }).start();

        System.out.println("1:" + r.get());
        //需要把这个对象的强引用解除
        date = null;
        System.gc();
        System.out.println(r.get());

    }

}
