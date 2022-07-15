package com.lzb.company.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SaleOrderDetailDO {

    /**
     * <table>sale_order_detail</table>
     * <p>
     * order_detail_id    bigint auto_increment comment '售卖订单详情id'
     *     primary key,
     * order_id           bigint               null comment 'sale_order唯一ID',
     * oid                bigint               null comment '总订单号，order唯一ID',
     * cart_id            bigint               null comment '购物车ID',
     * skuId              bigint               null comment 'skuId',
     * buy_num            smallint(5) unsigned null comment '购买件数',
     * order_status       tinyint default 0    null comment '订单支付状态 1退货退款 2取消',
     * should_pay         decimal(12, 4)       null comment '应付金额，保留两位，注意币种和汇率',
     * actual_pay         decimal(12, 4)       null comment '实际支付金额，保留两位，注意币种和汇率',
     * coupon_discount    decimal(8, 4)        null comment '优惠券优惠金额，保留两位，注意币种和汇率',
     * promotion_discount decimal(8, 4)        null comment '活动优惠，保留两位，注意币种和汇率',
     * add_time           datetime             null comment '添加时间',
     * update_time        datetime             null comment '更新时间'
     */

    private Long orderDetailId, orderId, oid;

    private Long cartId;

    private Long skuId;

    private Integer buyNum;

    private Integer orderStatus;

    private BigDecimal shouldPay, actualPay;

    private BigDecimal couponDiscount, promotionDiscount;

    private LocalDateTime addTime, updateTime;

    //sku_main表的erpCode
    private String skuCode;

    /**
     * 预计发货时间
     * 只有订单是(支付或者未支付状态，取消状态不展示)未发货状态才展示
     */
    private LocalDateTime preorderDate;

    /**
     * 对应ERP 的订单detail id
     */
    private Long erpDetailId;

    /**
     * 退款ID  order_refund 的主键ID
     */
    private Long refundId;
    /**
     * 当前单件退款的金额 refund_amount
     */
    private BigDecimal refundAmount;
    /**
     * 当前商品退款的原因refund_reason
     */
    private String refundReason;
    /**
     * 退款单applicationId
     */
    private Long refundApplicationId;
}