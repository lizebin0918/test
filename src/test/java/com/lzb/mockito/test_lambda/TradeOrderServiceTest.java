package com.lzb.mockito.test_lambda;

import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.data.util.CastUtils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 交易订单服务测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class TradeOrderServiceTest {
    /** 定义静态常量 */
    /** 资源路径 */
    private static final String RESOURCE_PATH = "testTradeOrderService/";

    /** 模拟依赖对象 */
    /** 交易ODPS服务 */
    @Mock
    private TradeOdpsService tradeOdpsService;

    /** 定义测试对象 */
    /** 交易订单服务 */
    @InjectMocks
    private TradeOrderService tradeOrderService;

    @Test
    public void testQueryTradeOrderWithNormal1() {
        // 参数里面的对象也可以mock()啊
        Instance instance = mock(Instance.class);
        Record record1 = mock(Record.class);
        Record record2 = mock(Record.class);
        List<Record> recordList = Arrays.asList(record1, record2);
        when(SQLTask.getResult(instance)).thenReturn(recordList);
        // 模拟依赖方法: record.getString
        Mockito.doReturn(1L).when(record1).getBigint("id");
        Mockito.doReturn(2L).when(record2).getBigint("id");

        // 调用测试方法
        Long userId = 12345L;
        Integer maxCount = 100;
        List<TradeOrderVO> tradeOrderList = tradeOrderService.queryTradeOrder(userId, maxCount);
        String path = RESOURCE_PATH + "testQueryTradeOrderWithNormal/";
        String text = ResourceHelper.getResourceAsString(getClass(), path + "tradeOrderList.json");
        Assert.assertEquals("交易订单列表不一致", text, JSON.toJSONString(tradeOrderList));

        // 验证依赖方法
        //PowerMockito.verifyStatic(SQLTask.class);
        // 验证依赖方法: SQLTask.run
        text = ResourceHelper.getResourceAsString(getClass(), path + "queryTradeOrder.sql");
        //SQLTask.run(tradeOdps, text);
        // 验证依赖方法: SQLTask.getResult
        SQLTask.getResult(instance);
        // 验证依赖方法: instance.waitForSuccess
        Mockito.verify(instance).waitForSuccess();
        // 验证依赖方法: record.getString
        Mockito.verify(record1).getBigint("id");
        Mockito.verify(record2).getBigint("id");
    }

    /**
     * 测试: 查询交易订单-正常
     */
    @Test
    public void testQueryTradeOrderWithNormal() {
        // 模拟依赖方法
        // 模拟依赖方法: tradeOdpsService.executeQuery
        List<TradeOrderVO> tradeOrderList = mock(List.class);
        Mockito.doReturn(tradeOrderList).when(tradeOdpsService).executeQuery(Mockito.anyString(), Mockito.any());

        // 调用测试方法
        Long userId = 12345L;
        Integer maxCount = 100;
        Assert.assertSame("交易订单列表不一致", tradeOrderList, tradeOrderService.queryTradeOrder(userId, maxCount));

        // 验证依赖方法
        // 验证依赖方法: tradeOdpsService.executeQuery
        String path = RESOURCE_PATH + "testQueryTradeOrderWithNormal/";
        String text = ResourceHelper.getResourceAsString(getClass(), path + "queryTradeOrder.sql");
        Mockito.verify(tradeOdpsService).executeQuery(Mockito.eq(text), Mockito.any());
    }

    /**
     * 测试: 转化交易订单
     * 
     * @throws Exception 异常信息
     */
    @Test
    public void testConvertTradeOrder() throws Exception {
        // 模拟依赖方法
        Long id = 12345L;
        Record record = mock(Record.class);
        Mockito.doReturn(id).when(record).getBigint("id");

        // 调用测试方法
        //TradeOrderVO tradeOrder = Whitebox.invokeMethod(TradeOrderService2.class, "convertTradeOrder", record);
        //Assert.assertEquals("订单标识不一致", id, tradeOrder.getId());

        // 验证依赖方法
        Mockito.verify(record).getBigint("id");
    }
}