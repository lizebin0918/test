package com.lzb.jdk;

import java.util.Objects;

/**
 * 测试继承的synchronized<br/>
 * Created on : 2021-09-15 09:47
 *
 * @author lizebin
 */
public class TestExtendSynchronized {

    private static abstract class A {

        private volatile B b;

        public B getB() {
            if (Objects.isNull(b)) {
                synchronized (this) {
                    System.out.println(this);
                    if (Objects.isNull(b)) {
                        b = instance();
                    }
                }
            }
            return b;
        }

        public abstract B instance();
    }

    private static class B {

        long t = System.currentTimeMillis();

    }

    public static void main(String[] args) {
        A a1 = new A() {
            @Override
            public B instance() {
                return new B();
            }
        };
        System.out.println("============");
        System.out.println(a1);
        System.out.println(a1.getB().t);
        System.out.println("============");
    }



}
