package com.lzb.concurrent.dead_lock;

import lombok.Data;

/**
 * Account<br/>
 * Created on : 2021-05-24 22:39
 * @author lizebin
 */
@Data
public class Account {

    private int amount;
    private String name;

    public Account() {

    }

    public Account(String name, Integer amount) {
        this.name = name;
        this.amount = amount;
    }

    public int add(int value) {
        return amount += value;
    }

    public int sub(int value) {
        return amount -= value;
    }

}
