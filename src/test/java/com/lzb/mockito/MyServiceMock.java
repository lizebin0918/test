package com.lzb.mockito;

import com.alibaba.testable.core.annotation.MockInvoke;
import com.alibaba.testable.core.annotation.MockWith;
import com.lzb.mock.MyService;

/**
 * <br/>
 * Created on : 2022-03-21 23:45
 *
 * @author lizebin
 */
public class MyServiceMock {

    @MockInvoke(targetClass = String.class)
    private String substring(int i, int j) {
        return "sub_string";
    }

    @MockInvoke(targetClass = String.class)
    private String trim() {
        return "trim_string";
    }

    @MockInvoke(targetClass = String.class)
    private boolean startsWith(String s) {
        return false;
    }

}
