package com.lzb.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 引用计数器<br/>
 * Created on : 2021-06-27 19:35
 *
 * @author lizebin
 */
public class AtomicIntegerFieldUpdaterTest {

    public static void main(String[] args) throws InterruptedException {

        Person p = new Person();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                Person.AGE_UPDATER.addAndGet(p, 1);
            });
            t.start();
            t.join();
        }

        System.out.println("person.age = " + p.age);
    }

    private static class Person {

        private static final AtomicIntegerFieldUpdater<Person> AGE_UPDATER =
                AtomicIntegerFieldUpdater.newUpdater(Person.class, "age");

        private volatile int age;

        /**
         * netty采用上面的方式实现引用计数，这种方式会有性能损耗
         */
        //private AtomicInteger ageCounter;
    }

}
