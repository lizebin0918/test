package com.lzb.jdk.threadlocal;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 黄金分割比<br/>
 * Created on : 2021-10-03 10:11
 * 一、黄金分割数与斐波那契数列
 * 首先复习一下斐波那契数列，下面的推导过程来自某搜索引擎的wiki：
 *
 * 斐波那契数列：1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, ...
 * 通项公式：假设F(n)为该数列的第n项（n ∈ N*），那么这句话可以写成如下形式：F(n) = F(n-1) + F(n-2)。
 * 有趣的是，这样一个完全是自然数的数列，通项公式却是用无理数来表达的。而且当n趋向于无穷大时，
 * 前一项与后一项的比值越来越逼近0.618（或者说后一项与前一项的比值小数部分越来越逼近0.618），而这个值0.618就被称为黄金分割数。
 *
 * 二、2654435769为32位无符号整数的黄金分割值，而-1640531527就是32位带符号整数的黄金分割值。而ThreadLocal中的哈希魔数正是1640531527(十六进制为0x61c88647)。
 * 为什么要使用0x61c88647作为哈希魔数？这里提前说一下ThreadLocal在ThreadLocalMap(ThreadLocal在ThreadLocalMap以Key的形式存在)中的哈希求Key下标的规则：
 *
 * 哈希算法：keyIndex = ((i + 1) * HASH_INCREMENT) & (length - 1)
 *
 * 其中，i为ThreadLocal实例的个数，这里的HASH_INCREMENT就是哈希魔数0x61c88647，length为ThreadLocalMap中可容纳的Entry(K-V结构)的个数(或者称为容量)。
 * 在ThreadLocal中的内部类ThreadLocalMap的初始化容量为16，扩容后总是2的幂次方，因此我们可以写个Demo模拟整个哈希的过程：test()
 *
 *
 * @author lizebin
 */
public class GoldenRatio {

    private Product[] PRODUCT_ARRAY = new Product[128];

    private static final int CAPACITY = 100;

    public static void main(String[] args) throws Exception {
        //黄金分割数 * 2的32次方 = 2654435769 - 这个是无符号32位整数的黄金分割数对应的那个值
        long c = (long) ((1L << 32) * (Math.sqrt(5) - 1) / 2);
        System.out.println(c);// 无符号：2654435769
        //强制转换为带符号为的32位整型，值为-1640531527
        int i = (int) c;
        System.out.println(i);// 有符号：-1640531527

        // 斐波那契散列保证最小2的N次方下散列空间元素不冲突，实现了完美散列......
        // 应用场景：
        // 例如我分了16个从库，如何把请求均匀打到不同的数据库
        // 对应抽奖概率，均匀散列到数组里面
        test();

        Product aP = new Product(1, "A", 30);
        Product bP = new Product(2, "B", 45);
        Product cP = new Product(3, "C", 25);



    }

    private static final int HASH_INCREMENT = 0x61c88647;

    public static void test() throws Exception {
        hashCode(4);
        hashCode(16);
        hashCode(32);
    }

    private static void hashCode(int capacity) throws Exception {
        int keyIndex;
        for (int i = 0; i < capacity; i++) {
            keyIndex = ((i + 1) * HASH_INCREMENT) & (capacity - 1);
            System.out.print(keyIndex);
            System.out.print(" ");
        }
        System.out.println();
    }

    @Data
    @AllArgsConstructor
    private static class Product {
        private Integer id;
        private String name;
        /**
         * 概率：30%
         */
        private int rate;
    }

}
