package com.lzb.leetcode;

import java.util.Random;

/**
 * 1603:https://leetcode-cn.com/problems/design-parking-system/
 * 0 <= big, medium, small <= 1000
 * carType 取值为 1， 2 或 3
 * 最多会调用 addCar 函数 1000 次
 */
class ParkingSystem {

    int bigUnit = 21;
    int mediumUnit = 11;
    int smallUnit = 1;

    int total = 0;

    public int getTotal() {
        return total;
    }

    public ParkingSystem(int big, int medium, int small) {
        for (int i = 0; i < big; i++) {
            total += 1 << bigUnit;
        }
        for (int i = 0; i < medium; i++) {
            total += 1 << mediumUnit;
        }
        for (int i = 0; i < small; i++) {
            total += smallUnit;
        }
    }

    /**
     * @param carType 1-打车，2-中车，3-小车
     * @return
     */
    public boolean addCar(int carType) {
        boolean success = false;
        switch (carType) {
            case 1:
                total -= 1 << bigUnit;
                if (total >= 0) {
                    success = true;
                }
                break;
            case 2:
                if ((total & ((1 << (bigUnit)) - 1)) - (1 << mediumUnit) >= 0) {
                    total = total - (1 << mediumUnit);
                    success = true;
                }
                break;
            case 3:
                if ((total & ((1 << (mediumUnit)) - 1)) - 1 >= 0) {
                    total -= 1;
                    success = true;
                }
                break;
            default:
        }
        return success;
    }

    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(1000));
        //int count = new Random().nextInt(1000);
        int count = 1000;
        ParkingSystem p = new ParkingSystem(0, count, 0);
        for (int i = 0; i < count + 1; i++) {
            if (!p.addCar(2)) {
                System.out.println(i);
                System.out.println("wrong");
            }
        }
    }
}