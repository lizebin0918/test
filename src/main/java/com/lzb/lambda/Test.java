package com.lzb.lambda;

import java.time.OffsetDateTime;
import java.util.Arrays;

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

        // 没啥特别...
        Object o2 = (Runnable)(() -> user.getId());

        int[] array = new int[]{1, 2, 3};
        bubble(Test::swap, array);
        System.out.println("swap array:" + Arrays.toString(array));
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

    interface SwapFunc {
        void swap(int i, int j, int[] array);
    }

    private static void swap(int i, int j, int[] array) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 这样也行？？我去...
     * @param func
     * @param array
     */
    public static void bubble(SwapFunc func, int... array) {
        func.swap(0, 1, array);
    }


}
