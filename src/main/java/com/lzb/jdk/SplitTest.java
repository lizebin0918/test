package com.lzb.jdk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 字符串切割<br/>
 * Created on : 2021-08-03 13:33
 *
 * @author lizebin
 */
public class SplitTest {

    public static void main(String[] args) {
        String str = "a,b,c,,";
        // String[] ary = str.split(",");
        // ary.length结果为3
        // String[] ary = str.split(",", -1);
        // ary.length结果为5

        //LocalDate.now().lengthOfYear()

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if ("2".equals(item)) {
                iterator.remove();
            }
        }
        System.out.println(list);

        System.out.println(Set.of("1", "2").contains("2"));

        System.out.println(Integer.toBinaryString(0x61c88647));
        System.out.println(Integer.highestOneBit(0x61c88647));
        System.out.println(Integer.lowestOneBit(0x61c88647));
        System.out.println(0x61c88647 & (15));
    }

}
