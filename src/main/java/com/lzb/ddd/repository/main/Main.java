package com.lzb.ddd.repository.main;

import com.lzb.ddd.repository.Order;
import com.lzb.ddd.repository.OrderRepository;

/**
 * <br/>
 * Created on : 2023-05-21 09:48
 * @author lizebin
 */
public class Main {

    public static void main(String[] args) {
        OrderRepository orderRepository = new OrderRepository();
        orderRepository.add(new Order());
        orderRepository.update(new Order());


    }

}
