package com.lzb.vavr;

import io.vavr.collection.Stream;

/**
 * <br/>
 * Created on : 2023-11-21 09:47
 * @author lizebin
 */
public class StreamTest {

    public static void main(String[] args) {
        for (double random : Stream.continually(Math::random).take(1000)) {
            System.out.println(random);
        }
    }

}
