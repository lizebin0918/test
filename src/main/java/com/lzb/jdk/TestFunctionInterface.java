package com.lzb.jdk;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * <br/>
 * Created on : 2021-12-09 22:52
 *
 * @author lizebin
 */
public class TestFunctionInterface {

    public static void main(String[] args) {

        Order order = new Order("orderNo", 1L, () -> listOrderDetail());

        System.out.println(JSON.toJSONString(order));
        System.out.println("orderDetailList:" + JSON.toJSONString(order.getOrderDetailList()));
        System.out.println(order.getOrderDetailList() == order.getOrderDetailList());

        testConsumerLink();
    }

    /**
     * Consumer链式调用
     */
    public static void testConsumerLink() {
        //当前值
        Consumer<Integer> consumer1 = x -> System.out.println("当前值 : " + x);
        //相加
        Consumer<Integer> consumer2 = x -> { System.out.println("相加 : " + (x + x)); };
        //相乘
        Consumer<Integer> consumer3 = x -> System.out.println("相乘 : " + x * x);
        //andThen拼接
        consumer1.andThen(consumer2).andThen(consumer3).accept(1);
    }

    public static List<OrderDetail> listOrderDetail() {

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail od1 = new OrderDetail(1L, 1L, "1");
        OrderDetail od2 = new OrderDetail(2L, 2L, "2");
        OrderDetail od3 = new OrderDetail(3L, 3L, "3");
        orderDetailList.add(od1);
        orderDetailList.add(od2);
        orderDetailList.add(od3);
        return orderDetailList;

    }

    /**
     * 订单
     */
    private static class Order {

        public Order(String orderNo, Long oid, Supplier<List<OrderDetail>> orderDetailListSupplier) {
            this.orderNo = orderNo;
            this.oid = oid;
            this.orderDetailListSupplier = orderDetailListSupplier;
        }

        private final String orderNo;
        private final Long oid;
        private final Supplier<List<OrderDetail>> orderDetailListSupplier;
        private List<OrderDetail> orderDetailList;

        public String getOrderNo() {
            return orderNo;
        }

        public Long getOid() {
            return oid;
        }

        public List<OrderDetail> getOrderDetailList() {
            if (Objects.isNull(orderDetailList)) {
                orderDetailList = orderDetailListSupplier.get();
            }
            return orderDetailList;
        }
    }

    /**
     * 订单明细
     */
    @Data
    @AllArgsConstructor
    private static class OrderDetail {
        private Long id;
        private Long skuId;
        private String skuCode;
    }

}
