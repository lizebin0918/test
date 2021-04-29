package com.lzb.concurrent;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * 测试<br/>
 * Created on : 2021-04-24 11:11
 * @author lizebin
 */
public class LongAdderTest {

    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        longAdder.add(1);
        LongAdderTest tester = new LongAdderTest();
        /*for (int i=0; i<10; i++) {
            int num = i;
            new Thread(() -> {
                System.out.println(">>>>>>>>>>>>>>>>>" + num + ":" + tester.getProbe());
                ThreadLocalRandom.current();
                System.out.println(">>>>>>>>>>>>>>>>>" + num + ":" + tester.getProbe());
            }).start();
        }*/

        Map<String, String> map = new ConcurrentHashMap<>(128);
        for (int i=0; i<100; i++) {
            map.put(Objects.toString(i), Objects.toString(i));
        }
    }

    public int getProbe() {
        return createUnsafe().getInt(Thread.currentThread(), threadLocalRandomProbe);
    }

    private static final long threadLocalRandomProbe;
    static {
        try {
            sun.misc.Unsafe UNSAFE = createUnsafe();
            Class<?> sk = LongAdderTest.class;
            Class<?> tk = Thread.class;
            threadLocalRandomProbe = UNSAFE.objectFieldOffset
                (tk.getDeclaredField("threadLocalRandomProbe"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    /*private static final sun.misc.Unsafe UNSAFE;
    private static final long base;
    private static final long cellsBusy;
    private static final long threadLocalRandomProbe;
    static {
        try {
            UNSAFE = createUnsafe();
            Class<?> sk = LongAdderTeset.class;
            base = UNSAFE.objectFieldOffset
                (sk.getDeclaredField("base"));
            cellsBusy = UNSAFE.objectFieldOffset
                (sk.getDeclaredField("cellsBusy"));
            Class<?> tk = Thread.class;
            threadLocalRandomProbe = UNSAFE.objectFieldOffset
                (tk.getDeclaredField("threadLocalRandomProbe"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }*/

    public static Unsafe createUnsafe() {
        try {
            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            Field field = unsafeClass.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Unsafe unsafe = (Unsafe) field.get(null);
            return unsafe;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    public void add(long x) {
        //as:Cell[]引用
        //b:base值（总数）
        //v:期望值
        //m:Cell[].length cell长度
        //a:Cell[n] 命中的单元
        Cell[] as; long b, v; int m; Cell a;
        if ((as = cells) != null || !casBase(b = base, b + x)) {
            boolean uncontended = true;
            if (as == null || (m = as.length - 1) < 0 ||
                (a = as[getProbe() & m]) == null ||
                !(uncontended = a.cas(v = a.value, v + x)))
                longAccumulate(x, null, uncontended);
        }
    }
     */

}
