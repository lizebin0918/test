package com.lzb.concurrent;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 测试<br/>
 * Created on : 2021-04-27 14:36
 * @author chenpi 
 */
public class TestConcurrenHashMap {

    public static void main(String[] args) {
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i=0;i<1000;i++) {
            map.put(Objects.toString(i), Objects.toString(i));
        }

        //System.out.println(map.get("100"));

        int j = 0x7fffffff;
        int i = -1;
        System.out.println("-1:");
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));
        System.out.println(Integer.toBinaryString(j));
        System.out.println("-2:");
        System.out.println(Integer.toBinaryString(-2));
        System.out.println("-2 & j:");
        System.out.println(Integer.toBinaryString(-2 & j));
        //System.out.println(Integer.toBinaryString(1));
        //System.out.println("j:");
        //System.out.println(Integer.toBinaryString((j)));
        //System.out.println("~j:");
        //System.out.println(Integer.toBinaryString((~j)));
        //System.out.println(Integer.toBinaryString(i & j));

    }

}
