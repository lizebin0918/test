package com.lzb.tdd;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-06-19 22:39
 * @author mac
 */
@Getter
public class DiamonChar {

    private final char startChar;

    public static final char END_CHAR = 'A';

    public static final String DOT = ".";

    public DiamonChar(char startChar) {
        this.startChar = startChar;
    }

    List<String> diamonPrint() {
        int length = length();
        List<String> lines = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {

            lines.add(line(startChar, END_CHAR, currentChar))

        }
        return List.of("A");
    }

    /**
     * 边长
     * @return
     */
    private int length() {
        int length = (END_CHAR - startChar) * 2 - 1;
        return length;
    }
}
