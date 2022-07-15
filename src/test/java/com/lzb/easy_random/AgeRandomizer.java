package com.lzb.easy_random;

import org.jeasy.random.api.Randomizer;

import java.util.Random;

public class AgeRandomizer implements Randomizer<Integer> {

   @Override
   public Integer getRandomValue() {
      return new Random().nextInt(1) + 9;
   }

}