package com.lzb.guava;

import com.google.common.base.Verify;

/**
 * <br/>
 * Created on : 2023-06-09 19:13
 * @author lizebin
 */
public class TestVerify {

    public static void main(String[] args) {
        //Verify.verify(false, "this is error", 1);
        Verify.verify(false, "this is message %s", 2);
    }

}
