package com.lzb.guava;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

/**
 * <br/>
 * Created on : 2023-06-09 16:17
 * @author lizebin
 */
public class TestRangeSet {

    public static void main(String[] args) {
        RangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1, 10));
        rangeSet.add(Range.closed(12, 15));
        rangeSet.add(Range.closed(10, 12));

        Range<Integer> range = Range.closed(1, 10);

        System.out.println(JSON.toJSONString(rangeSet.asRanges()));
        System.out.println(JSON.toJSONString(rangeSet.asDescendingSetOfRanges()));
    }

}
