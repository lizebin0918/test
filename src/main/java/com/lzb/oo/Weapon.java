package com.lzb.oo;

import lombok.Data;

@Data
public abstract class Weapon {
    String name;
    int damage;
    // 0 - physical, 1 - fire, 2 - ice etc.
    int damageType;

    public Weapon(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }
}