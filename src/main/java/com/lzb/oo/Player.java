package com.lzb.oo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public abstract class Player {

    @Setter(AccessLevel.PROTECTED)
    Weapon weapon;
}