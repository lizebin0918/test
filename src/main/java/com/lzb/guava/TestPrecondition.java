package com.lzb.guava;

import com.google.common.base.Preconditions;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <br/>
 * Created on : 2023-06-09 19:16
 * @author lizebin
 */
public class TestPrecondition {

    public static void main(String[] args) {
        Preconditions.checkNotNull(Integer.valueOf(1));
        //Preconditions.checkNotNull(null);

        checkNotNull(null, "orderId = %s 快照不能为空", 1L);
    }

}
