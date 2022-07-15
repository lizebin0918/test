package com.lzb.company.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDO {

    /**
     * <table>ci_order</table>
     * <p>
     * oid                      bigint         not null comment '订单号',
     * uid                      int            null comment '用户唯一id',
     * order_status             tinyint        null comment '订单状态 1未支付 2已支付 3取消',
     * currency                 varchar(15)    null comment '币种 人民币RMB  美元USD  欧元EUR  英镑DEM',
     * exchange_rate            decimal(7, 4)  null comment '汇率，保留四位',
     * total_should_pay         decimal(12, 4) null comment '实际应付金额，计算需参考币种和汇率，保留两位小数',
     * total_actual_pay         decimal(12, 4) null comment '实际应付金额，保留两位小数，注意币种和汇率',
     * coupon_id                int            null comment '本单使用优惠券ID',
     * total_coupon_discount    decimal(8, 4)  null comment '优惠券总优惠，保留两位',
     * total_promotion_discount decimal(8, 4)  null comment '总活动优惠',
     * shipping_fee             decimal(8, 4)  null comment '运费',
     * shipping_guarantee       decimal(8, 4)  null comment '运送保证金',
     * sale_tax                 decimal(8, 4)  null comment '税',
     * add_time                 datetime       null comment '添加时间',
     * update_time              datetime       null comment '更新时间',
     * version                  varchar        null comment '用户下单的版本号'
     */

    private Long oid;

    private Long uid;

    private String device;

    private String deviceId;

    private Integer orderStatus;

    private String currency;

    private BigDecimal totalShouldPay, totalActualPay, exchangeRate;

    private Long couponId;

    private BigDecimal totalCouponDiscount, totalPromotionDiscount;

    private BigDecimal shippingFee, shippingGuarantee, saleTax;

    private BigDecimal shippingDiscount;

    private LocalDateTime addTime, updateTime;

    private Long shippingAddressId;

    private Long courierExpressDetailId;

    /**
     * 礼品卡的id
     */
    private Long giftCardId;

    /**
     * 礼品卡折扣的钱
     */
    private BigDecimal giftCardDiscount;

    /**
     * 标签
     */
    private String tag;
    /**
     * 用户下单的版本号
     */
    private String version;
}
