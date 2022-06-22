package com.lzb.mock;

import com.alibaba.testable.core.annotation.MockDiagnose;
import com.alibaba.testable.core.annotation.MockInvoke;
import com.alibaba.testable.core.model.LogLevel;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * <br/>
 * Created on : 2022-03-21 23:05
 *
 * @author lizebin
 */
@MockDiagnose(LogLevel.VERBOSE)
public class MyServiceTest {

    MyService myService = new MyService();

    public static class Mock {

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

        @MockInvoke(targetClass = MyDao.class)
        private String should_mock_common_method() {
            return "a";
        }
    }

    @Test
    public void test_should_mock_common_method() {
        System.out.println(myService.string());
        assertEquals("a", myService.string());
    }


}
