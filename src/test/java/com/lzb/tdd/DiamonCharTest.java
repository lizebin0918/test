package com.lzb.tdd;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

/**
 * 题目：Print Diamond
 *
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
class DiamonCharTest {

    @Test
    void should_return_A_when_input_A() {
        List<String> exceptList = List.of("A");
        DiamonChar diamonChar = new DiamonChar('A');
        assertEquals(exceptList, diamonChar.diamonPrint());
    }

    @Test
    void should_return_when_input_B() {
        DiamonChar diamonCharB = new DiamonChar('B');
        Assertions.assertEquals(3, diamonCharB.length());

        DiamonChar diamonCharC = new DiamonChar('C');
        Assertions.assertEquals(5, diamonCharC.length());
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

    @Test
    void should_return_diamon_B_when_input_C() {

        DiamonChar diamonChar = new DiamonChar('C');
        List<String> expectList = new ArrayList<>();
        expectList.add("..A..");
        expectList.add(".B.B.");
        expectList.add("C...C");
        expectList.add(".B.B.");
        expectList.add("..A..");

        List<String> actualList = diamonChar.diamonPrint();
        Assertions.assertEquals(expectList, actualList);
    }

    @Test
    void should_return_line_when_input_B() {
        DiamonChar diamonChar = new DiamonChar('B');
        String b = diamonChar.line('B');
        assertEquals("B.B", b);

        String c = diamonChar.line('A');
        assertEquals(".A.", c);
    }

    @Test
    void should_reverseAppend() {
        DiamonChar diamonChar = new DiamonChar('A');
        List<String> list = List.of("1", "2", "3");
        Assertions.assertEquals(List.of("1", "2", "3", "2", "1"), diamonChar.reverseAppend(list, 0, 2));
    }

}
