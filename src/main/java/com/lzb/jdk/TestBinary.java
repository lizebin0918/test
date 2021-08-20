package com.lzb.jdk;

import java.util.Objects;

/**
 * 二进制运算测试<br/>
 * Created on : 2021-08-03 22:33
 *
 * @author lizebin
 */
public class TestBinary {

    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int COUNT_MASK = (1 << COUNT_BITS) - 1;

    public static void main(String[] args) {
        // 输出的是补码：11111111111111111111111111111111
        System.out.println(Integer.toBinaryString(-1));
        //补码转原码
        System.out.println(Integer.parseUnsignedInt("11111111111111111111111111111111", 2));

        int i = 0x61c88647;
        System.out.println(i);
        System.out.println(Integer.toBinaryString(i));
        System.out.println(Integer.toBinaryString(15));
        System.out.println(15 & i);

        //判断一个数的奇偶
        int num = Integer.MAX_VALUE + 1;
        System.out.println("num = " + num);
        System.out.println("it is 奇数:" + ((num & 1) == 1));
        System.out.println("it is 偶数:" + ((num & 1) == 0));
        //判断一个数是否是2的幂次方，最高位为1，其他全0
        int val = 6;
        System.out.println((val & (val - 1)) == 0);
        System.out.println((val & -val) == val);

        System.out.println(Integer.toBinaryString(COUNT_MASK));
        //正数的补码等于它自己，原码：01111111 11111111 11111111 11111111(31个1)
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));

        {
            //& 按位与是用原码计算？还是补码？
            System.out.println("------------------");
            System.out.println(Integer.toBinaryString(1));
            System.out.println(Integer.toBinaryString(-1));
            System.out.println(1 & -1);
        }

        {
            // & 按位与是用原码计算？还是补码？
            System.out.println("------------------");
            System.out.println(Integer.toBinaryString(-2));
            System.out.println(Integer.toBinaryString(-1));
            System.out.println(-2 & -1);
            // 输出:-8，补码形式运算，以原码形式打印
            System.out.println(-4 & -5);
        }

        int countMask = (1 << COUNT_BITS) - 1;
        int running = -1 << COUNT_BITS;
        System.out.println(Integer.toBinaryString(COUNT_MASK & running));
        int shutdown =  0 << COUNT_BITS;
        System.out.println(Integer.toBinaryString(shutdown));

        String s = null;
        boolean isEmpty = Objects.toString(s, "").isEmpty();
        boolean isEmpty1 = Objects.toString(s, "").isBlank();

        // 无符号右移
        int k = 1 | (1 >>> 4);
        System.out.println("k = " + k);

        // 表示运行中
        int status = 4;
        int runStatus = 6;
        // (运行状态 & status) == status
        System.out.println("isRunning = " + ((runStatus & status) == status));

        System.out.println(Integer.toBinaryString(-1));
        // int类型用4个字节32位存储，如果移动位数超过最大位数，会对移动位数进行取模操作
        // 实际是无符号右移4位:-1>>>(36%32)=-1>>>4，同理：“>>” “<<” “>>>” 也遵循这个原理。
        // 无符号右移是表示无视符号位，右移N位，左补零，相当于把数变成正数
        System.out.println(-1 >>> 65);
        System.out.println(-1L >>> 65);

        System.out.println(2 >> 33);
    }

}
