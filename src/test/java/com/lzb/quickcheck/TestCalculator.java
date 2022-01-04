package com.lzb.quickcheck;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <br/>
 * Created on : 2022-01-04 17:22
 *
 * @author lizebin
 */
@RunWith(JUnitQuickcheck.class)
public class TestCalculator {

    @Test
    public void testAdditionExampleBased() {

        Calculator calculator = new Calculator();
        calculator.add(2);
        assertEquals(calculator.getResult(), 2);
    }

    @Property(trials = 5)
    public void testAddition(int number) {

        System.out.println("Generated number for testAddition: " + number);

        Calculator calculator = new Calculator();
        calculator.add(number);
        assertEquals(calculator.getResult(), number);
    }

}
