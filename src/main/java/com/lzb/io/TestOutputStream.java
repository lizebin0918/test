package com.lzb.io;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <br/>
 * Created on : 2021-05-21 17:20
 * @author chenpi 
 */
public class TestOutputStream {

    public static void main(String[] args) {
        String jsonString = "{\"buyDate\":\"1617552000\",\"payMoney\":\"0\",\"storeNo\":\"81104\",\"orderNo\":\"100432293097765048\",\"name\":\"片仔癀\",\"count\":\"1.0\",\"drugId\":\"107231\",\"costPrice\":\"476.00\",\"originMoney\":\"590.00\",\"retailPrice\":\"590.00\",\"costAmount\":\"476.00\",\"grossProfitAmount\":\"97.30\"}";
        int size = 1000000;
        List<OrderDetail> list = new ArrayList<>(size);

        for (int i=0; i<size; i++) {
            list.add(JSON.parseObject(jsonString, OrderDetail.class));
        }

        long start = System.currentTimeMillis();
        //new String(stringWrite(list));
        new String(streamWrite(list));
        long end = System.currentTimeMillis();

        System.out.println("耗时（毫秒）:" + (end - start));

    }

    public static byte[] stringWrite(List<OrderDetail> list) {
        byte[] bytes = null;
        bytes = list.stream().map(OrderDetail::toString).collect(Collectors.joining("\n")).getBytes(StandardCharsets.UTF_8);
        return bytes;
    }

    public static byte[] streamWrite(List<OrderDetail> list) {
        byte[] bytes = null;
        int bufferSize = 2048;
        try (
            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(bufferSize)) {
            for (OrderDetail detail : list) {
                JSON.writeJSONString(byteOutputStream, detail, SerializerFeature.DisableCircularReferenceDetect);
                byteOutputStream.write(System.lineSeparator().getBytes());
            };
            list.clear();
            bytes = byteOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return bytes;
    }

    @Data
    private static class OrderDetail {

        private Integer customerId;

        private Integer buyDate;

        private String cardNo;

        /**
         * 订单数量
         */
        private Double orderNos;

        private BigDecimal payMoney;

        private String storeNo;

        private String storeUserNo;

        private String orderNo;

        private String name;

        private String approval;

        private BigDecimal count;

        private String drugId;

        private Integer year;

        private Integer month;

        private Integer day;

        private String originMoney;

        private String costPrice;

        private String retailPrice;

        private String promotionalContent;

        /**
         * 成本额
         */
        private String costAmount;

        /**
         * 毛利额
         */
        private String grossProfitAmount;

        /**
         * 退款订单号
         */
        private String returnOrderNo;

        private Date createDate;
        /**
         * 订单明细id
         */
        private String detailId;

        private Integer sign;

        private Integer version;

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

}
