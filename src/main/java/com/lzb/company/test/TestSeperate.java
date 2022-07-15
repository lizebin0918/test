package com.lzb.company.test;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定指定数量的数字，分到对应的List中，保证每一个List都有数字<br/>
 * Created on : 2021-11-05 23:03
 *
 * @author lizebin
 */
public class TestSeperate {

    public static void main(String[] args) {

        // 12个数字分到5个List里面
        int count = 5;
        int size = 12;
        List<List<Integer>> totalList = new ArrayList<>();

        // 原列表
        List<Integer> sourceList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            sourceList.add(i);
        }

        // 方法一：遍历add
        /*for (int i = 0; i < count; i++) {
            totalList.add(new ArrayList<>());
        }
        for (int i = 0; i < sourceList.size(); i++) {
            int index = i % count;
            totalList.get(index).add(sourceList.get(i));
        }*/

        // 方法二：双指针遍历，在原集合中遍历，前后指针同时往前移动
        int step = size / count, surplus = size % count, index = 0;
        for (int i = 0; i < count; i++) {
            totalList.add(sourceList.subList(index, index += step + (surplus-- > 0 ? 1 : 0)));
        }

        System.out.println(JSON.toJSONString(totalList));
    }

}
