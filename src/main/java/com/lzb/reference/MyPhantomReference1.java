package com.lzb.reference;
/*
import jdk.internal.ref.Cleaner;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

*//**
 * 虚引用回收时机，一旦没有其他更上层的引用指向就会被回收?<br/>
 * Created on : 2021-06-27 20:00
 *
 * @author lizebin
 *//*
public class MyPhantomReference1 {

    private static volatile Person p = new Person();
    public static void main(String[] args) throws InterruptedException {

        ReferenceQueue<Person> q = new ReferenceQueue<>();
        PhantomReference<Person> reference = new PhantomReference<>(p, q);

        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    System.out.println(p);
                    //回收不会入队(enqueued)，那就只能从ReferenceHandler的线程，从pending队列里面取？
                    *//*Reference<? extends Person> ref = q.poll();
                    if (ref != null) {
                        System.out.println("被回收：" + ref.get());
                    }*//*
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

        Thread.sleep(5000);
        //手动释放，jdk的DirectByteBuffer也是一样，但是触发机制还是需要依赖GC的，只是回收的动作由开发者执行
        p.cleaner.clean();
        p = null;
        System.out.println(" person set null");

    }

    private static class Person {
        final Cleaner cleaner = Cleaner.create(this, () -> {
            System.out.println("person 被回收了");
        });
    }

}*/
