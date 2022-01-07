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

import java.util.*;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
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
 * 4.stubbing:"录制"和"播放"
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
        mockedList.get(0);
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

    /**
     * 自定义返回
     */
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
        assertThat(list.get(0)).isEqualTo("first");

        when(list.get(anyInt())).thenThrow(new RuntimeException());
        try {
            String first = list.get(0);
            // 如果没有抛异常则执行失败
            fail();
        } catch (Exception e) {
            assertThat(e).isInstanceOf(RuntimeException.class);
        }

    }

    @Test
    public void test_how_to_stubbing_void_method() {
        List<String> list = mock(ArrayList.class);
        doNothing().when(list).clear();
        list.clear();
        // 验证clear执行次数
        verify(list, atLeast(1)).clear();
    }

    @Test
    public void test_do_return_then_return() {
        List<String> list = mock(ArrayList.class);
        doReturn("first").when(list).get(0);
        when(list.get(1)).thenReturn("second");

        assertThat(list.get(0)).isEqualTo("first");
        assertThat(list.get(1)).isEqualTo("second");

    }

    /**
     * 多次调用
     */
    @Test
    public void test_iterate() {
        List<String> list = mock(ArrayList.class);
        when(list.size()).thenReturn(1);
        when(list.size()).thenReturn(2);
        when(list.size()).thenReturn(3);
        // 这样是覆盖
        when(list.size()).thenReturn(4);

        assertThat(list.size()).isEqualTo(4);

        when(list.get(0)).thenReturn("1").thenReturn("2").thenReturn("3").thenReturn("4");
        when(list.get(0)).thenReturn("1", "2", "3", "4");
        assertThat(list.get(0)).isEqualTo("1");
        assertThat(list.get(0)).isEqualTo("2");
        assertThat(list.get(0)).isEqualTo("3");
        assertThat(list.get(0)).isEqualTo("4");

    }

    @Test
    public void test_customize_answer() {
        List<String> list = mock(ArrayList.class);
        when(list.get(anyInt())).thenAnswer(mock -> {
            Integer p = mock.getArgument(0);
            return p.toString() + p.toString();
        });
        assertThat(list.get(0)).isEqualTo("00");

        when(list.get(anyInt())).thenCallRealMethod();
        // 抛异常：IndexOutOfBoundsException
        // assertThat(list.get(0)).isEqualTo("0");
    }

    @Test
    public void test_spy() {
        List<String> list = spy(ArrayList.class);
        // 如果是mock(ArrayList.class)，执行对应对象的所有方法都不会返回
        // 但是spy(ArrayList.class) 会执行真正的方法，实现部分mock()
        list.add("a");

        assertThat(list.size()).isGreaterThanOrEqualTo(0);
        assertThat(list.get(0)).isEqualTo("a");

        when(list.get(0)).thenReturn("b");
        assertThat(list.get(0)).isEqualTo("b");

    }

    /**
     * 返回目标值
     */
    @Test
    public void test_return_target_value() {
        List<String> list = mock(ArrayList.class);

        // 任意下标都是返回"a"
        when(list.get(anyInt())).thenReturn("a");
        assertThat(list.get(0)).isEqualTo("a");
        assertThat(list.get(1)).isEqualTo("a");

        // 指定下标返回"b"
        reset(list);
        when(list.get(anyInt())).thenReturn("a");
        when(list.get(eq(100))).thenReturn("b");
        assertThat(list.get(0)).isEqualTo("a");
        assertThat(list.get(100)).isEqualTo("b");
        assertThat(list.get(100)).isEqualTo("b");
    }

    @Test
    public void test_return_void() {
        List<String> list = mock(ArrayList.class);
        doNothing().when(list).clear();

        list.add("a");
        list.get(0);
        list.clear();

        verify(list, atLeast(1)).clear();

    }

    @Test
    public void test_return_random() {
        List<String> list = mock(ArrayList.class);
        Random random = new Random();
        // 返回缓存值
        when(list.get(eq(100))).thenReturn(String.valueOf(random.nextInt(100)));
        assertThat(list.get(100)).isEqualTo(list.get(100));
    }

    @Test
    public void test_increment() {
        List<Integer> list = mock(ArrayList.class);
        when(list.get(anyInt())).thenAnswer(new Answer<Integer>() {
            private final AtomicInteger counter = new AtomicInteger();
            @Override
            public Integer answer(InvocationOnMock invocationOnMock) throws Throwable {
                return counter.incrementAndGet();
            }
        });
        assertThat(list.get(0)).isEqualTo(1);
        assertThat(list.get(0)).isEqualTo(2);
    }

    @Test
    public void test_id_increment() {
        Map<String, Integer> map = mock(HashMap.class);
        when(map.get(anyString())).thenAnswer(new Answer<Integer>() {
            private final Map<String, AtomicInteger> innerMap = new HashMap<>();
            @Override
            public Integer answer(InvocationOnMock invocationOnMock) throws Throwable {
                String key = invocationOnMock.getArgument(0);
                /*AtomicInteger counter = innerMap.get(key);
                if (Objects.isNull(counter)) {
                    counter = new AtomicInteger();
                    innerMap.put(key, counter);
                }*/
                // 等价上面的代码
                return innerMap.computeIfAbsent(key, (k) -> new AtomicInteger()).incrementAndGet();
            }
        });

        assertThat(map.get("1")).isEqualTo(1);
        assertThat(map.get("1")).isEqualTo(2);
        assertThat(map.get("1")).isEqualTo(3);
        assertThat(map.get("2")).isEqualTo(1);

    }
}