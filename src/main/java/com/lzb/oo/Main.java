package com.lzb.oo;


/**
 * <br/>
 * Created on : 2021-10-18 15:05
 *
 * @author lizebin
 */
public class Main {

    public static void main(String[] args) {
        // Given
        Fighter fighter = new Fighter("Hero");

        Sword sword = new Sword("Sword", 10);
        fighter.setWeapon(sword);
        System.out.println(fighter.getWeapon());

        Staff staff = new Staff("Staff", 10);
        fighter.setWeapon(staff);
        System.out.println(fighter.getWeapon());
    }

}
