package com.lzb.lambda;

import java.time.OffsetDateTime;

/**
 * <br/>
 * Created on : 2021-07-30 16:28
 *
 * @author lizebin
 */
public class Test {

    public static void main(String[] args) {
        var user = new User(666);

        Object o = (Runnable)user::getId;
        Runnable o1 = () -> {
            System.out.println("runnable");
        };

        System.out.println(o);
        System.out.println(o1);
        System.out.println(o.getClass());
        System.out.println(o1.getClass());

        Thread.onSpinWait();



    }
    private static class User {
        int id;
        public User(int id) {
            this.id = id;
        }
        public void getId() {
            System.out.println(id);
        }
    }

}
