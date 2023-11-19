package com.lzb.vavr;

import io.vavr.Tuple2;
import io.vavr.collection.Queue;
import io.vavr.control.Option;

/**
 * <br/>
 * Created on : 2023-11-19 17:58
 * @author mac
 */
public class QueueTest {

    public static void main(String[] args) {
        Queue<Integer> queue = Queue.of(1, 2, 3)
                .enqueue(4)
                .enqueue(5);
        System.out.println(queue);

        Integer getOrElse888 = queue.getOrElse(100, 888);
        System.out.println(getOrElse888);

        // 获取头元素
        Integer get = queue.get();
        System.out.println("get = " + get);

        // 如果为空，返回默认值
        Integer getOrElse = queue.getOrElse(888);
        System.out.println("getOrElse = " + getOrElse);

        Option<Tuple2<Integer, Queue<Integer>>> tuple2s = queue.dequeueOption();
        System.out.println(tuple2s);

        Option<Integer> t2To1 = tuple2s.map(Tuple2::_1);
        System.out.println(t2To1.isDefined());
        System.out.println(t2To1.get());
    }

}
