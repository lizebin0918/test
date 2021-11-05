package com.lzb.cider.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Data
public class OrderPaymentDO {

    /**
     * id                bigint auto_increment primary key,
     * oid               bigint                             null comment '订单号',
     * pay_trade_no      varchar(50)                        null comment '支付订单号',
     * stripe_payment_id varchar(50)                        null comment 'stripe paymentIntent id ，查询、退款使用',
     * amount            decimal(12, 4)                     null comment '支付金额，保留两位小数',
     * currency          varchar(10)                        null comment '支付币种',
     * exchange_rate     decimal(8, 4)                      null comment '支付发生，使用的汇率',
     * pay_type          varchar(10)                        null comment '支付方式 STRIPE_CARD',
     * pay_status        tinyint                            null comment '支付状态 1未支付 2已支付',
     * bill_address      varchar(300)                       null comment '账单地址，json格式保存支付发生时地址对象信息',
     * add_time          datetime default CURRENT_TIMESTAMP null,
     * update_time       datetime                           null
     */

    private Long id;

    private Long uid;

    private Long oid;

    private String payTradeNo;

    private String stripePaymentId;

    private BigDecimal amount;

    private String currency;

    private BigDecimal exchangeRate;

    private String payType;

    /**
     * '支付状态 1未支付 2已支付',
     */
    private Integer payStatus;

    private String billAddress;

    private LocalDateTime addTime;
    private LocalDateTime updateTime;

    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<Integer> local = ThreadLocal.withInitial(() -> 1);
        //ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadPoolExecutor.AbortPolicy());
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        threadPoolExecutor.execute(() -> {
            System.out.println("start");
            local.set(20);
            local.set(21);
            local.set(22);
            System.out.println(local.get());
        });

        threadPoolExecutor.execute(() -> {
            local.set(3);
            System.out.println(local.get());
        });

        TimeUnit.SECONDS.sleep(1);
        threadPoolExecutor.shutdownNow();

        System.out.println("done");
    }
}
