package com.lzb.jdk.feature.jdk17;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/>
 * Created on : 2022-04-23 11:26
 *
 * @author lizebin
 */
public class StreamTest {

    public static void main(String[] args) {

        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");

        // 返回一个不可变的List
        List<String> newStrings = strings.stream().filter(item -> item.equals("2")).toList();
        System.out.println(newStrings);
        newStrings.add("a");


    }

}
