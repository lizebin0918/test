package com.lzb.mockito.test1;

import java.util.List;

public class Game {

    private final Player player;

    private final List<String> weapenList;

    public Game(Player player, List<String> weapenList) {
        this.player = player;
        this.weapenList = weapenList;
    }

    public String attack() {
        return "Player attack with:" + player.getWeapon();
    }

    public int sizeOfWeapeans() {
        return weapenList.size();
    }

}