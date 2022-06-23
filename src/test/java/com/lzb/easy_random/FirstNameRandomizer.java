package com.lzb.easy_random;

import org.jeasy.random.api.Randomizer;

import java.util.Random;

public class FirstNameRandomizer implements Randomizer<String> {

    private Random random = new Random();

    /** List of desired values */
    private String[] firstNames = {"Alice", "Bob", "Charlie"};

    @Override
    public String getRandomValue() {
        return firstNames[random.nextInt(firstNames.length)];
    }
}