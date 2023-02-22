package com.lzb.mockito;

import java.util.Collections;

import lombok.RequiredArgsConstructor;

/**
 * <br/>
 * Created on : 2023-02-02 02:08
 * @author lizebin
 */
@RequiredArgsConstructor
public class MyOrderService {

    private MyOrderDao myOrderDao;

    public String deleteById(String a) {

        String b = a + "b";
        String c = b + "c";

        Order order = myOrderDao.get(1L);
        order.cancel();

        myOrderDao.doDelete(Collections.singletonList(b));

        return myOrderDao.doDelete(b, c);
    }
}
