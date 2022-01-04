package com.lzb.quickcheck;

public class Calculator {

    int valueDisplayed;

    public Calculator() {
        this.valueDisplayed = 0;
    }

    public void add(int x) {
        this.valueDisplayed += x;
    }

    public int getResult() {
        return this.valueDisplayed;
    }
}
