package com.lzb.jdk;

import java.util.Objects;

/**
 * 二进制运算测试，下面以类型：int 举例，，long同理<br/>
 * 1.计算机的位移运算都是基于补码
 * 2.位移位数超出最大位数需要取模再移动，如果位移位数超过32位，需要对32取模，比如：1<<33 等价于 1<<(33%32) --> 1<<1
 *
 * 正数（无符号整数）
 *  转码规则：原码 <--> 反码 <--> 补码
 *  左移（<<）：基于补码，符号位不动，剩余位左移，超出部分舍弃。
 *  右移（>>）：基于补码，带符号位右移（因为符号位为零，往前补"0"），低位舍弃
 *  无符号右移（>>>）：无视符号位，前面补零，低位舍弃
 * 负数（带符号整数）
 *  转码规则：
 *  原码 --> 反码：符号位不变，其余位取反 --> 补码：反码 + 1
 *  补码 --> 反码：符号位不变，其余位取反 --> 原码：反码 + 1
 *  左移（<<）：基于补码，符号位不动，剩余位左移，超出部分舍弃。
 *  右移（>>）：基于补码，**带符号**右移（因为符号位为1，往前补"1"），低位舍弃
 *  无符号右移（>>>）：无视符号位，前面补零（相当于负数变正数），低位舍弃
 *
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

        // 有符号右移，以补码形式右移
        // -1 的补码 32个1，有符号右移，符号位不动，高位填1，右移33位，相当于全部是1，这是补码。补码的符号位不变，取反全部为零，加1，所以是-1
        System.out.println("-1 >> 33 = " + (-1 >> 33));
        System.out.println(Integer.toBinaryString(-1 >>> 33));

        int j = Integer.MAX_VALUE;
        System.out.println("真值：" + j);
        System.out.println("原码：" + Integer.toBinaryString(j));
        System.out.println(~j + 1);
    }

}
