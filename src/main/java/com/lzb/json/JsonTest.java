package com.lzb.json;

import com.alibaba.fastjson.JSON;

/**
 * <br/>
 * Created on : 2021-10-11 20:29
 *
 * @author lizebin
 */
public class JsonTest {

    public static void main(String[] args) {
        System.out.println(JSON.parse("{}").getClass());
        System.out.println(JSON.parse("[]").getClass());
    }

}
