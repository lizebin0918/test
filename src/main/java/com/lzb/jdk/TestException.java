package com.lzb.jdk;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * 测试异常<br/>
 * Created on : 2021-08-05 08:04
 *
 * @author lizebin
 */
public class TestException {

    /**
     * 参数异常:非null的参数值不正确
     */
    private IllegalArgumentException illegalArgumentException = new IllegalArgumentException();

    /**
     * 不合适方法调用的对象状态
     */
    private IllegalStateException illegalStateException = new IllegalStateException();

    private NullPointerException nullPointerException = new NullPointerException();

    private IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException();

    private ConcurrentModificationException concurrentModificationException = new ConcurrentModificationException();

    /**
     * 对象不支持用户请求的方法
     */
    private UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException();

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        // 输出多少？
        try {
            int i = 1 / 0;
        } finally {
            System.out.println("1");
        }
        System.out.println("2");

        // 输出多少？
        /*try {
            try {
                int i = 1 / 0;
            } finally {
                System.out.println("1");
            }
        } finally {
            System.out.println("2");
        }*/
    }
}
