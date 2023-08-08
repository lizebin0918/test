package com.lzb.jdk.extend.multiple_inheritance;

/**
 * <br/>
 * Created on : 2023-08-04 17:54
 * @author lizebin
 */
public class CookerSinger {

    private Cooker cooker = new Cooker() {
        @Override
        void cook() {
            System.out.println("cook");
        }
    };

    private Singer singer = new Singer() {
        @Override
        void sing() {
            System.out.println("sing");
        }
    };

    public void cook() {
        cooker.cook();
    }

    public Cooker asCooker() {
        return cooker;
    }

    public Singer asSinger() {
        return singer;
    }


}
