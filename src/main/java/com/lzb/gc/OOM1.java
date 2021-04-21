package com.lzb.gc;

import java.util.LinkedList;
import java.util.List;

/**
 * <br/>
 * Created on : 2021-04-21 21:30
 * @author lizebin
 */
public class OOM1 {

    public static void main(String[] args) {

        List<String> persons = new LinkedList<>();
        for (;;) {
            StringBuilder sb = new StringBuilder();
            for (int i=0; i<100000; i++) {
                sb.append(i);
            }
            persons.add(sb.toString());
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
