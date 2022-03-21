package com.lzb.mockito;

import com.alibaba.testable.core.annotation.MockWith;
import com.lzb.mock.MyService;
import org.junit.Test;

import static com.alibaba.testable.core.matcher.InvocationVerifier.verifyInvoked;
import static org.junit.Assert.assertEquals;

/**
 * <br/>
 * Created on : 2022-03-21 23:05
 *
 * @author lizebin
 */
@MockWith(MyServiceMock.class)
public class MyServiceTest {

    MyService myService = new MyService();

    @Test
    public void should_mock_common_method() {
        assertEquals("trim_string__sub_string__false", myService.commonFunc());
        verifyInvoked("trim").withTimes(1);
        verifyInvoked("sub").withTimes(1);
        verifyInvoked("startsWith").withTimes(1);
    }

}
