package com.lzb.concurrent.async.model;

import lombok.Data;

import java.util.concurrent.*;

/**
 * 订单实体<br/>
 * Created on : 2021-06-16 11:57
 *
 * @author chenpi
 */
@Data
public class OrderDto {

    private Integer id;
    private String orderNo;
    private String name;
    private CompletableFuture<Void> future = new CompletableFuture<>();

}
