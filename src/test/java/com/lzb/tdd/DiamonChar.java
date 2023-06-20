package com.lzb.tdd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            lines.add(line(startChar, i, 'a'));
        }
        return List.of("A");
    }

    static String line(char startChar, int length, char targetChar) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (Math.abs(startChar - i) == targetChar) {
                line.append(targetChar);
                continue;
            }
            line.append(DOT);
        }
        return line.toString();
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
