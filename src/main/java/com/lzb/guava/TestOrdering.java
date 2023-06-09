package com.lzb.guava;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <br/>
 * Created on : 2023-06-09 19:21
 * @author lizebin
 */
public class TestOrdering {

    public static void main(String[] args) {
        Ordering<Foo> ordering = Ordering.natural().nullsFirst().onResultOf(Foo::getSortedBy);
        Iterable<Foo> list = List.of(new Foo("1", 1), new Foo("2", 2));
        Foo min = ordering.min(list);
        System.out.println(JSON.toJSONString(min));
    }

    @Data
    @AllArgsConstructor
    public static class Foo {
        private String sortedBy;
        private int notSortedBy;
    }
}
