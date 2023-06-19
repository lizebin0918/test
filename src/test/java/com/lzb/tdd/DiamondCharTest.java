package com.lzb.tdd;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This is the print diamond for 'E'.
 *
 * ....A....
 * ...B.B...
 * ..C...C..
 * .D.....D.
 * E.......E
 * .D.....D.
 * ..C...C..
 * ...B.B...
 * ....A....<br/>
 *
 * This is the print diamond for 'C'.
 *
 * ..A..
 * .B.B.
 * C...C
 * .B.B.
 * ..A..
 *
 * This is the print diamond for 'A'.
 *
 * A
 *
 *
 * Created on : 2023-06-19 22:36
 * @author mac
 */
class DiamondCharTest {

    @Test
    void should_return_A_when_input_A() {
        List<String> exceptList = List.of("A");
        DiamonChar diamonChar = new DiamonChar('A');
        Assertions.assertEquals(exceptList, diamonChar.diamonPrint());
    }

    @Test
    void should_return_diamon_B_when_input_B() {

        DiamonChar diamonChar = new DiamonChar('B');
        List<String> expectList = new ArrayList<>();
        expectList.add(".A.");
        expectList.add("B.B");
        expectList.add(".A.");

        List<String> actualList = diamonChar.diamonPrint();
        Assertions.assertEquals(expectList, actualList);
    }

}
