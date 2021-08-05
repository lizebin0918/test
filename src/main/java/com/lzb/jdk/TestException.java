package com.lzb.jdk;

/**
 * 测试异常<br/>
 * Created on : 2021-08-05 08:04
 *
 * @author lizebin
 */
public class TestException {

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
