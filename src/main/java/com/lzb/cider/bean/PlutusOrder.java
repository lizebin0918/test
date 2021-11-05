package com.lzb.cider.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <br/>
 * Created on : 2021-08-11 11:36
 *
 * @author lizebin
 */
public class PlutusOrder {

    public static void main(String[] args) {
        OrderDO order = new OrderDO();
        UserAddressVO userAddressVO = new UserAddressVO();
        UserVO userVO = new UserVO();
        OrderPaymentDO orderPayment = new OrderPaymentDO();
        //plutusDto数据参数
        JSONObject orderDTOJson = new JSONObject();
        orderDTOJson.put("channel", "mobile");
        orderDTOJson.put("channelOrderId", order.getOid());
        orderDTOJson.put("channelOrderNumber", order.getOid());
        orderDTOJson.put("email", userVO.getEmail());
        orderDTOJson.put("phone", userAddressVO.getPhoneNumber());
        orderDTOJson.put("currency", order.getCurrency());
        orderDTOJson.put("exchangeRate", order.getExchangeRate());
        orderDTOJson.put("totalShouldPay", order.getTotalShouldPay());
        orderDTOJson.put("totalCouponDiscount", order.getTotalCouponDiscount());
        orderDTOJson.put("totalActualPay", order.getTotalActualPay());
        orderDTOJson.put("totalPromotionDiscount", order.getTotalPromotionDiscount());
        orderDTOJson.put("shippingFee", order.getShippingFee());
        orderDTOJson.put("shippingDiscount", order.getShippingDiscount());
        String courierExpressType = "";
        orderDTOJson.put("shippingType", courierExpressType);
        orderDTOJson.put("saleTax", order.getSaleTax());
        orderDTOJson.put("transactionNumber", orderPayment.getPayTradeNo());//交易流水号
        orderDTOJson.put("transactionChannel", orderPayment.getPayType());
        orderDTOJson.put("orderPayTime", orderPayment.getAddTime());
        orderDTOJson.put("orderStatus", "PAID");//订单状态
        orderDTOJson.put("device", order.getDeviceId());
        orderDTOJson.put("orderAddTime", order.getAddTime());
        orderDTOJson.put("orderUpdateTime", order.getUpdateTime());

        orderDTOJson.put("couponCode", "");
        //设置tag
        String tags = "";
        orderDTOJson.put("tags", tags);

        // 设置备注
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("用户备注:").append("this is remark").append("\n");
        stringBuffer.append("系统备注:").append(tags);
        orderDTOJson.put("remark", stringBuffer.toString());

        //添加税号
        orderDTOJson.put("taxNumber", userAddressVO.getTaxNumber());


        //运输地址
        JSONObject shippingAddressJson = new JSONObject();
        shippingAddressJson.put("country", userAddressVO.getCountry());
        shippingAddressJson.put("state", userAddressVO.getState());
        shippingAddressJson.put("city", userAddressVO.getCity());
        shippingAddressJson.put("zipCode", userAddressVO.getZipCode());
        shippingAddressJson.put("addressLine1", userAddressVO.getAddressLine1());
        shippingAddressJson.put("addressLine2", userAddressVO.getAddressLine2());
        shippingAddressJson.put("email", userVO.getEmail());
        shippingAddressJson.put("phoneNumber", userAddressVO.getPhoneNumber());//哪个电话
        shippingAddressJson.put("firstName", userAddressVO.getFirstName());
        shippingAddressJson.put("lastName", userAddressVO.getLastName());
        shippingAddressJson.put("countryCode", userAddressVO.getSimpleCode());
        orderDTOJson.put("shippingAddress", shippingAddressJson);


        //进行sku聚合
        List<SaleOrderDetailDO> mergerCartItemList = Lists.newArrayList();
        //订单明细
        List<JSONObject> orderItemsJson = mergerCartItemList.stream().map(e -> {
            JSONObject itemJson = new JSONObject();
            //  itemJson.put("skuCode", erpCodeMap.containsKey(e.getSkuCode()) ? erpCodeMap.get(e.getSkuCode()) : e.getSkuCode());
            itemJson.put("skuCode", e.getSkuCode());
            itemJson.put("buyNum", e.getBuyNum());
            itemJson.put("price", e.getShouldPay());//price不知道是不是应付金额
            return itemJson;
        }).collect(Collectors.toList());
        orderDTOJson.put("orderItems", orderItemsJson);

        System.out.println(JSON.toJSONString(orderDTOJson));
    }

}
