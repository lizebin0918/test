package com.lzb.mockito.test_lambda;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 交易订单服务
 */
@Service
public class TradeOrderService {
    /** 注入依赖对象 */
    /** 交易ODPS服务 */
    @Autowired
    private TradeOdpsService tradeOdpsService;

    /**
     * 查询交易订单
     * 
     * @param userId 用户标识
     * @param maxCount 最大数量
     * @return 交易订单列表
     */
    public List<TradeOrderVO> queryTradeOrder(Long userId, Integer maxCount) {
        String format = ResourceHelper.getResourceAsString(getClass(), "query_trade_order.sql");
        String sql = String.format(format, userId, maxCount);
        return tradeOdpsService.executeQuery(sql, TradeOrderService::convertTradeOrder);
    }

    /**
     * 转化交易订单
     *
     * @param record ODPS记录
     * @return 交易订单
     */
    private static TradeOrderVO convertTradeOrder(Record record) {
        TradeOrderVO tradeOrder = new TradeOrderVO();
        tradeOrder.setId(record.getBigint("id"));
        // ...
        return tradeOrder;
    }
}