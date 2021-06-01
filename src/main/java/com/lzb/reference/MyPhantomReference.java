package com.lzb.reference;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 虚引用<br/>
 *
 * -Xmx20M
 *
 * Created on : 2021-05-31 22:46
 * @author lizebin
 */
public class MyPhantomReference {

    public static void main(String[] args) throws InterruptedException {

        byte[] m = new byte[1024];
        ReferenceQueue<Integer> queue = new ReferenceQueue<>();
        PhantomReference<Integer> pr = new PhantomReference<>(new Integer(1),
                                                              queue);
        List<byte[]> mb = new ArrayList<>();

        new Thread(() -> {
            while (true) {
                mb.add(m);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(pr.get());
            }
        }).start();


        new Thread(() -> {
            while (true) {
                Reference<? extends Integer> i = queue.poll();
                if (i != null) {
                    System.out.println("i被jvm回收:" + i);
                }
            }
        }).start();


        Thread.sleep(5000);
    }

}
