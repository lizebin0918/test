package com.lzb.jdk.list;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * <br/>
 * Created on : 2022-03-26 11:34
 *
 * @author lizebin
 */
public class TestList {

    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(2);
        nums.add(3);

        List<Integer> targets = new ArrayList<>();
        targets.add(1);
        targets.add(2);
        targets.add(4);

        ListIterator<Integer> numsIterator = nums.listIterator(nums.size() >> 1);

        numsIterator.forEachRemaining(i -> {
            // 会改变迭代元素
            nums.set(nums.size() - 1, 0);
            System.out.println(i);
        });

        // 取交集
        nums.retainAll(targets);
        System.out.println(nums);

    }

}
