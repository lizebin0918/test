package com.lzb.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.time.LocalDateTime;

/**
 * 软引用:内存敏感的缓存，OutOfMemory抛出之前会被回收<br/>
 * Created on : 2021-05-31 22:46
 * @author lizebin
 */
public class MySoftReference {

    public static void main(String[] args) {
        //识别垃圾收集器的动作
        ReferenceQueue<LocalDateTime> queue = new ReferenceQueue<>();
        SoftReference<LocalDateTime> sr =
            new SoftReference<>(LocalDateTime.now(), queue);

        LocalDateTime d = sr.get();

        System.out.println("====================");

        if (d == null) {
            System.out.println("对象被垃圾回收了");
        }




    }

}
