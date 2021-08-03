package com.lzb.jdk;

/**
 * 二进制运算测试<br/>
 * Created on : 2021-08-03 22:33
 *
 * @author lizebin
 */
public class TestBinary {

    public static void main(String[] args) {
        // 输出的是补码：11111111111111111111111111111111
        System.out.println(Integer.toBinaryString(-1));
        //补码转原码
        System.out.println( Integer.parseUnsignedInt("11111111111111111111111111111111", 2));

        int i = 0x61c88647;
        System.out.println(i);
        System.out.println(Integer.toBinaryString(i));
        System.out.println(Integer.toBinaryString(15));
        System.out.println(15 & i);
    }

}
