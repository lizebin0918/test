package com.lzb.mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 只要里面的变量要作为参数传递，大部分场景都可以拿到参数验证<br/>
 * Created on : 2023-02-07 23:05
 * @author lizebin
 */
@ExtendWith(MockitoExtension.class)
class OpServiceTest {

    @Mock
    private RecordReader recordReader;

    @InjectMocks
    private OpService opService;

    @Test
    void testReadData() throws Exception {
        // 模拟依赖方法
        // 模拟依赖方法: recordReader.read
        Record record0 = Mockito.mock(Record.class);
        Record record1 = Mockito.mock(Record.class);
        Record record2 = Mockito.mock(Record.class);
        Mockito.doReturn(record0, record1, record2, null).when(recordReader).read();
        // 模拟依赖方法: dataParser.apply
        Object object0 = new Object();
        Object object1 = new Object();
        Object object2 = new Object();
        Function<Record, Object> dataParser = Mockito.mock(Function.class);
        Mockito.doReturn(object0).when(dataParser).apply(record0);
        Mockito.doReturn(object1).when(dataParser).apply(record1);
        Mockito.doReturn(object2).when(dataParser).apply(record2);
        // 模拟依赖方法: dataStorage.test
        Predicate<List<Object>> dataStorage = Mockito.mock(Predicate.class);
        Mockito.doReturn(true).when(dataStorage).test(Mockito.anyList());

        // 调用测试方法
        opService.readData(recordReader, 2, dataParser, dataStorage);

        // 验证依赖方法
        // 模拟依赖方法: recordReader.read
        Mockito.verify(recordReader, Mockito.times(4)).read();
        // 模拟依赖方法: dataParser.apply
        Mockito.verify(dataParser, Mockito.times(3)).apply(Mockito.any(Record.class));
        // 验证依赖方法: dataStorage.test
        ArgumentCaptor<List<Object>> recordListCaptor = ArgumentCaptor.forClass(List.class);
        Mockito.verify(dataStorage, Mockito.times(2)).test(recordListCaptor.capture());
        Assertions.assertEquals(List.of(List.of(object0, object1), List.of(object2)), recordListCaptor.getAllValues());
    }


    @Test
    void testReadData1() throws Exception {
        // 模拟依赖方法
        // 模拟依赖方法: recordReader.read
        Record record0 = Mockito.mock(Record.class);
        Record record1 = Mockito.mock(Record.class);
        Record record2 = Mockito.mock(Record.class);
        Mockito.doReturn(record0, record1, record2, null).when(recordReader).read();
        // 模拟依赖方法: dataParser.apply
        Object object0 = new Object();
        Object object1 = new Object();
        Object object2 = new Object();
        Function<Record, Object> dataParser = Mockito.mock(Function.class);
        Mockito.doReturn(object0).when(dataParser).apply(record0);
        Mockito.doReturn(object1).when(dataParser).apply(record1);
        Mockito.doReturn(object2).when(dataParser).apply(record2);

        // 模拟依赖方法: dataStorage.test 获取传入的参数，收集起来
        List<Object> dataList = new ArrayList<>();
        Predicate<List<Object>> dataStorage = Mockito.mock(Predicate.class);
        Mockito.doAnswer(invocation -> {
            return dataList.addAll((List<Object>) invocation.getArgument(0));
        }).when(dataStorage).test(Mockito.anyList());

        // 调用测试方法
        opService.readData(recordReader, 2, dataParser, dataStorage);
        // 验证依赖方法
        Mockito.verify(dataStorage, Mockito.times(2)).test(Mockito.anyList());
        Assertions.assertEquals(List.of(object0, object1, object2), dataList);
    }
}
