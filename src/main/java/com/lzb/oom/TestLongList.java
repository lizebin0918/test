package com.lzb.oom;

import org.redisson.api.RSortedSet;
import org.redisson.api.RedissonClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Long列表:500万个Long占用内存13M, Integer占用9M<br/>
 * Created on : 2021-11-13 10:13
 *
 * @author lizebin
 */
public class TestLongList {

    private static RedissonClient redisson;

    public static void main(String[] args) throws InterruptedException {
        /*int count = 500000;
        List<Integer> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(i);
        }

        Thread.sleep(100000000L);*/

        int pageIndex = 2, pageSize = 3;
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println(list.stream()
                .filter(i -> i != 3 && i != 6 && i != 9)
                .skip((long) (pageIndex - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList()));
    }

}
