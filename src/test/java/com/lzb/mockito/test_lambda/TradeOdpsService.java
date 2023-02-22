package com.lzb.mockito.test_lambda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

/**
 * 交易ODPS服务类
 */
@Slf4j
@Service
public class TradeOdpsService {
    /** 注入依赖对象 */
    /** 交易ODPS */
    @Resource(name = "tradeOdps")
    private Odps tradeOdps;

    /**
     * 执行查询
     *
     * @param <T> 模板类型
     * @param sql SQL语句
     * @param dataParser 数据解析器
     * @return 查询结果列表
     */
    public <T> List<T> executeQuery(String sql, Function<Record, T> dataParser) {
        try {
            // 打印提示信息
            log.info("开始执行ODPS数据查询...");

            // 执行ODPS查询
            Instance instance = SQLTask.run(tradeOdps, sql);
            instance.waitForSuccess();

            // 获取查询结果
            List<Record> recordList = SQLTask.getResult(instance);
            if (CollectionUtils.isEmpty(recordList)) {
                log.info("完成执行ODPS数据查询: totalSize=0");
                return Collections.emptyList();
            }

            // 依次读取数据
            List<T> dataList = new ArrayList<>();
            for (Record record : recordList) {
                T data = dataParser.apply(record);
                if (Objects.nonNull(data)) {
                    dataList.add(data);
                }
            }

            // 打印提示信息
            log.info("完成执行ODPS数据查询: totalSize={}", dataList.size());

            // 返回查询结果
            return dataList;
        } catch (Exception e) {
            log.warn("执行ODPS数据查询异常: sql={}", sql, e);
            throw new RuntimeException("执行ODPS数据查询异常");
        }
    }
}