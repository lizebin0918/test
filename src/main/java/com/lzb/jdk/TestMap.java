package com.lzb.jdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

        //compute:对key的value计算，返回新的value。
        /*map.compute("b", (k, v) -> {
            //返回新的value
            return 3;
        });*/
        /*map.compute("a", (k, v) -> {
            return v + 1;
        });*/

        // 如果key不存在，对value操作会抛异常
        /*map.compute("b", (k, v) -> {
            return v + 1;
        });*/

        //computeIfPresent:key存在才会计算
        /*map.computeIfPresent("c", (k, v) -> {
            return 2;
        });*/

        //computeIfPresent:key不存在才会计算
        // computeIfAbsent 和 putIfAbsent 区别
        /*
        Java Map 接口中的 computeIfAbsent() 和 putIfAbsent() 方法都是用于向 Map 中添加元素的方法，但它们之间有以下区别：
        1. 参数不同：computeIfAbsent() 方法需要传入一个 Function 接口类型的参数，该参数用于计算新值；而 putIfAbsent() 方法需要传入一个键和一个值。
        2. 返回值不同：computeIfAbsent() 方法返回计算出的新值，如果 Map 中已经存在该键，则返回已有的值；而 putIfAbsent() 方法返回插入的值，如果 Map 中已经存在该键，则返回已有的值。
        3. 用途不同：computeIfAbsent() 方法用于在 Map 中不存在指定键时计算新值并插入到 Map 中；而 putIfAbsent() 方法用于在 Map 中不存在指定键时插入一个指定的键值对。
        总之，computeIfAbsent() 方法更适合用于需要计算新值的场景，而 putIfAbsent() 方法更适合用于插入指定的键值对的场景。
         */
        Integer d = map.computeIfAbsent("z", (k) -> {
            System.out.println("123");
            return 5;
        });
        System.out.println("d = " + d);

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

        // 通过merge实现group by
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(1);
        list.add(3);
        list.add(4);
        list.add(4);
        list.add(4);
        Map<Integer, Integer> num2Count = new HashMap<>();
        list.forEach(i -> {
            num2Count.merge(i, 1, Integer::sum);
        });
        System.out.println("num2Count:" + num2Count);

        Map<Integer, Integer> num2Count1 = new HashMap<>();
        list.forEach(i -> {
            num2Count1.compute(i, (key, value) -> {
                System.out.println("--------");
                System.out.println("key:" + key);
                System.out.println("value:" + value);
                return Optional.ofNullable(value).orElse(0) + key;
            });
        });
        System.out.println("num2Count1:" + num2Count1);

        System.out.println(map);

    }

}
