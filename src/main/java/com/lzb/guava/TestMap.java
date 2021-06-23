package com.lzb.guava;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * Map测试<br/>
 * Created on : 2021-06-22 23:56
 *
 * @author lizebin
 */
public class TestMap {

    public static void main(String[] args) {
        Multimap<Integer,String> multimap = ArrayListMultimap.create();
        multimap.put(1, "狄仁杰");
        multimap.put(1, "阿离");
        multimap.put(1, "小卤蛋");
        multimap.put(2, "韩信");
        multimap.put(2, "李白");
        multimap.put(2, "猴哥");

        System.out.println(multimap);

    }

}
