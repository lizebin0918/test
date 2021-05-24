package com.lzb.concurrent.dead_lock;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;

/**
 * 转账<br/>
 * Created on : 2021-05-24 22:42
 * @author lizebin
 */
public class Transfer implements Runnable {

    private Account a, b;
    private int amount;

    /**
     * A账户向B账户转账，amount为转账金额
     * @param a
     * @param b
     * @param amount
     */
    public Transfer(Account a, Account b, int amount) {
        this.a = a;
        this.b = b;
        this.amount = amount;
    }

    @Override
    public void run() {
        synchronized (a) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (b) {
                a.sub(amount);
                b.add(amount);
            }
        }
        System.out.println("转账完成:转账金额:" + amount);
        System.out.println("转出账户余额:" + JSON.toJSONString(a));
        System.out.println("转入账户余额:" + JSON.toJSONString(b));
    }
}
