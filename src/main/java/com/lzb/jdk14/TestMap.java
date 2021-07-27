package com.lzb.jdk14;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试映射<br/>
 * Created on : 2021-07-27 15:24
 *
 * @author chenpi
 */
public class TestMap {

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("d", 4);
        map.put("c", 4);

        //compute:对key的value计算，返回新的value。如果key不存在，对value操作会抛异常
        /*map.compute("b", (k, v) -> {
            //返回新的value
            return 3;
        });*/

        //computeIfPresent:key存在才会计算
        /*map.computeIfPresent("c", (k, v) -> {
            return 2;
        });*/

        //computeIfPresent:key不存在才会计算
        /*map.computeIfAbsent("d", (k) -> {
            return 4;
        });*/

        //replace:替换新的值
        //map.replace("c", "c");

        //replcae:根据旧值替换新值
        //map.replace("c", "oldValue", "newValue");

        //replaceAll:遍历所有key，替换成新的value
        /*map.replaceAll((k, v) -> {
            System.out.println("k = " + k + ", v = " + v);
            return v;
        });*/

        //putIfAbsent:不存在才会put成功，key存在返回原value；不存在返回null
        /*Integer oldValue = map.putIfAbsent("e", 3);
        System.out.println(oldValue);*/

        //merge:不存在直接put；存在通过oldValue、newValue(待写入的value)计算新值
        /*map.merge("c", 1000, (oldValue, newValue) -> {
            System.out.println("v1 = " + oldValue + ", v2 = " + newValue);
            return oldValue + newValue;
        });*/

        System.out.println(map);


    }

}
