package com.lzb.goto_test;

/**
 * break out;跳出out对应层的逻辑，继续执行后续逻辑<br/>
 * Created on : 2021-08-02 21:34
 *
 * @author lizebin
 */
public class TestGoto {

    public static void main(String[] args) throws InterruptedException {
        //多层循环，跳到指定的那一层循环
        /*for (int i = 0; i < 10; i++) {
            System.out.println(i);
            out:
            for (; ; ) {
                for (; ; ) {
                    System.out.println(1);
                    break out;
                }
            }
        }*/

        //if-else
        o:
        while (true) {
            System.out.println("1");
            Thread.sleep(1000);
            if (args.length == 0) {
                /* 注释 */
                // 继续标签的位置执行
                //continue o;
                // 退出标签的位置
                //break o;
            }
            System.out.println("2");
        }
        /*if (args.length == 0) {
            System.out.println("3");
        }*/
    }

}
