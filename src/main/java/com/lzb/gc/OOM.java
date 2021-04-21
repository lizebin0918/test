package com.lzb.gc;

import java.util.LinkedList;
import java.util.List;

/**
 * <br/>
 * Created on : 2021-04-21 21:30
 * @author lizebin
 */
public class OOM {

    public static void main(String[] args) {

        List<Person> persons = new LinkedList<>();
        for (;;) {
            persons.add(new Person());
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private static class Person {

        /*
        >jmap -histo 4871 | head -20
     num     #instances         #bytes  class name
    ----------------------------------------------
       1:          4015       40073992  [B
       2:           282        1643680  [I
       3:          1360         140544  [C
       4:          3909          93816  java.util.LinkedList$Node
       5:          3897          62352  com.lzb.gc.OOM$Person
        * */
        //private byte[] m = new byte[10 * 1024];
    }

}
