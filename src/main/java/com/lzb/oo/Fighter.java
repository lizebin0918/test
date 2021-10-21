package com.lzb.oo;

import lombok.Data;

@Data
public class Fighter extends Player {

    private Sword weapon;

    private String name;
    public Fighter(String name) {
        this.name = name;
    }

}