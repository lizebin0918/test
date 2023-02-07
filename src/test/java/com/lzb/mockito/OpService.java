package com.lzb.mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.collections4.CollectionUtils;

/**
 * <br/>
 * Created on : 2023-02-07 23:00
 * @author lizebin
 */
public class OpService {


    public <T> void readData(RecordReader recordReader,
            int batchSize,
            Function<Record, T> dataParser,
            Predicate<List<T>> dataStorage) {
        // 依次读取数据
        Record record;
        boolean isContinue = true;
        List<T> dataList = new ArrayList<>(batchSize);
        while (Objects.nonNull(record = recordReader.read()) && isContinue) {
            // 解析添加数据
            T data = dataParser.apply(record);
            if (Objects.nonNull(data)) {
                dataList.add(data);
            }

            // 批量存储数据
            if (dataList.size() == batchSize) {
                isContinue = dataStorage.test(dataList);
                dataList.clear();
            }
        }

        // 存储剩余数据
        if (CollectionUtils.isNotEmpty(dataList)) {
            dataStorage.test(dataList);
            dataList.clear();
        }
    }

}
