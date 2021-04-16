package com.lzb.gc;
/**
 * -Xmn9M -Xmx12M -XX:+PrintGCDetails <br/>
 * Created on : 2021-04-16 20:34
 * @author lizebin
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        byte[] m = new byte[1024 * 8];
        //大对象直接进入老年代
        byte[] m1 = new byte[1024 * 4];
        /**
         Heap
         PSYoungGen      total 8192K, used 2223K [0x00000007bf700000, 0x00000007c0000000, 0x00000007c0000000)
         eden space 7168K, 31% used [0x00000007bf700000,0x00000007bf92be60,0x00000007bfe00000)
         from space 1024K, 0% used [0x00000007bff00000,0x00000007bff00000,0x00000007c0000000)
         to   space 1024K, 0% used [0x00000007bfe00000,0x00000007bfe00000,0x00000007bff00000)
         ParOldGen       total 3072K, used 0K [0x00000007bf400000, 0x00000007bf700000, 0x00000007bf700000)
         object space 3072K, 0% used [0x00000007bf400000,0x00000007bf400000,0x00000007bf700000)
         Metaspace       used 2954K, capacity 4556K, committed 4864K, reserved 1056768K
         class space    used 315K, capacity 392K, committed 512K, reserved 1048576K
         */
    }

}
