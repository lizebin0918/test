package com.lzb.mock;

import com.alibaba.testable.core.annotation.MockDiagnose;
import com.alibaba.testable.core.annotation.MockInvoke;
import com.alibaba.testable.core.model.LogLevel;
import org.junit.Test;

import static com.alibaba.testable.core.matcher.InvocationVerifier.verifyInvoked;
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
    }

    @Test
    public void test_should_mock_common_method() {
        assertEquals("trim_string__sub_string__false", myService.should_mock_common_method());
        verifyInvoked("trim").withTimes(1);
        verifyInvoked("sub").withTimes(1);
        verifyInvoked("startsWith").withTimes(1);
    }

}
