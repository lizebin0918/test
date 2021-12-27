package com.lzb.mockito;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.io.TempDir;
import org.junit.runner.RunWith;
import org.mockito.invocation.Invocation;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.Stubbing;

import java.util.ArrayList;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * 使用mockito
 * 1.@RunWith(MockitoJUnitRunner.class)
 * 2.MockitoAnnotations.initMocks(this); and @Mock
 * 3.@Rule
 * private MockitoRule mockitoRule = MockitoJUnit.rule();
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoAnnotationTest {

    // @Rule
    // private MockitoRule mockitoRule = MockitoJUnit.rule();

    private List<String> list;

    @Before
    public void setup() {
        // MockitoAnnotations.initMocks(this);
        this.list = mock(ArrayList.class);
    }

    @After
    public void after() {
        reset(list);
    }

    @TempDir
    private File tempDir;

    @Test
    public void test_add_list() {
        //mock creation
        List<String> mockedList = mock(List.class);
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();

        // 构建行为
        // when(repository.save(any())).then(returnsFirstArg());

        // 真正执行的逻辑
        // TodoItem item = service.addTodoItem(TodoParameter.of("foo"));

        // 判断执行返回值
        // assertThat(item.getContent()).isEqualTo("foo");

        // Verify方法用于检查是否发生了某些行为。我们可以在测试方法代码的末尾使用Mockito验证方法，以确保调用了指定的方法。
        // verify(repository).save(any());
    }

    /**
     * 默认情况下，所有方法都会返回值，一个 mock 将返回要么 null，一个原始/基本类型的包装值或适当的空集。例如，对于一个 int/Integer 就是 0，而对于 boolean/Boolean 就是 false。
     * Stubbing 可以被覆盖。
     * 一旦 stub，该方法将始终返回一个 stub 的值，无论它有多少次被调用。
     * 最后的 stubbing 是很重要的 - 当你使用相同的参数 stub 多次同样的方法。换句话说：stubbing 的顺序是重要的，但它唯一有意义的却很少，例如当 stubbing 完全相同的方法调用，或者有时当参数匹配器的使用，等等。
     */
    @Test
    public void test_return_list() {
        //You can mock concrete classes, not just interfaces
        LinkedList mockedList = mock(LinkedList.class);

        //stubbing
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        //following prints "first"
        //System.out.println(mockedList.get(0));

        //following throws runtime exception
        //System.out.println(mockedList.get(1));

        //following prints "null" because get(999) was not stubbed
        //System.out.println(mockedList.get(999));

        //Although it is possible to verify a stubbed invocation, usually it's just redundant
        //If your code cares what get(0) returns, then something else breaks (often even before verify() gets executed).
        //If your code doesn't care what get(0) returns, then it should not be stubbed. Not convinced? See here.
        // 确保验证get(0)被执行
        verify(mockedList).get(0);
    }

    @Test
    public void when_thenReturn(){
        //mock一个Iterator类
        Iterator iterator = mock(Iterator.class);
        //预设当iterator调用next()时第一次返回hello，第n次都返回world
        when(iterator.next()).thenReturn("hello").thenReturn("world");
        //使用mock的对象
        String result = iterator.next() + " " + iterator.next() + " " + iterator.next();
        //验证结果
        System.out.println("result = " + result);
        assertEquals("hello world world",result);
    }

    @Test
    public void test_sleep() {
        List<String> list = mock(ArrayList.class);
        when(list.add(anyString())).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                System.out.println("开始睡眠");
                Thread.sleep(1000);
                System.out.println("结束睡眠");
                return true;
            }
        });

        assertTrue(list.add("1"));
    }

    @Test
    public void test_how_to_use_stubbing() {
        when(list.get(0)).thenReturn("first");
        assertThat(list.get(0), equalTo("first"));

        when(list.get(anyInt())).thenThrow(new RuntimeException());
        try {
            String first = list.get(0);
            // 如果没有抛异常则执行失败
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(RuntimeException.class));
        }

    }

}