package com.lzb.reference;

import java.lang.ref.WeakReference;
import java.util.Date;

/**
 * 弱引用<br/>
 * Created on : 2021-05-31 22:46
 * @author lizebin
 */
public class MyWeakReference {

    public static void main(String[] args) {
        Date date = new Date();
        WeakReference<Date> r = new WeakReference<>(date);

        System.out.println(r.get());
        //需要把这个对象的强引用解除
        date = null;
        System.gc();
        System.out.println(r.get());

    }

}
