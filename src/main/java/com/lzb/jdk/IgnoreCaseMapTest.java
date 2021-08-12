package com.lzb.jdk;

import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.TreeMap;

/**
 * 忽略大小写的map<br/>
 * Created on : 2021-08-12 10:59
 *
 * @author lizebin
 */
public class IgnoreCaseMapTest {

    public static void main(String[] args) {

        // TreeMap
        var ignoreCaseMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        ignoreCaseMap.put("a", 2);
        ignoreCaseMap.put("A", 3);

        System.out.println(ignoreCaseMap.size());
        System.out.println(ignoreCaseMap);

        // CaseInsensitiveMap
        var ignoreCaseMap1 = new CaseInsensitiveMap<>();
        ignoreCaseMap1.put("abc",2);
        ignoreCaseMap1.put("abC",3);
        ignoreCaseMap1.put("aBC",4);
        System.out.println(ignoreCaseMap1);

        // LinkedCaseInsensitiveMap
        var i = new LinkedCaseInsensitiveMap<>();


    }

}
