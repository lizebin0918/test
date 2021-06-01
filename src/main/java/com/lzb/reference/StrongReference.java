package com.lzb.reference;

import java.util.ArrayList;

/**
 * 强引用<br/>
 * Created on : 2021-05-31 22:44
 * @author lizebin
 */
public class StrongReference {

    public static void main(String[] args) {
        ArrayList<String> s = new ArrayList<>();
        s.add("1");
        s.add("1");
        s.add("1");
        s.add("1");
        //把所有元素设为null，断开数组与对象的"关系"，方便gc回收
        s.clear();
    }

}
