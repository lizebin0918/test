package com.lzb.jdk.hashmap;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br/>
 * Created on : 2021-10-06 23:56
 *
 * @author lizebin
 */
public class Test {

    public static void main(String[] args) {
        // 扰动函数真的可以让散列更加均匀？
        Map<Integer, Integer> map = new HashMap<>(16);
        int size = 10000;
        List<String> words = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            words.add(RandomStringUtils.random(10));
        }

        for (String word : words) {
            int idx = Disturb.disturbHashIdx(word, 128);
            // int idx = Disturb.hashIdx(word, 128);
            if (map.containsKey(idx)) {
                Integer i = map.get(idx);
                map.put(idx, ++i);
            } else {
                map.put(idx, 1);
            }
        }

        System.out.println(map.values());
    }

    private static class Disturb {

        public static int disturbHashIdx(String key, int size) {
            return (size - 1) & (key.hashCode() ^ (key.hashCode() >>> 16));
        }

        public static int hashIdx(String key, int size) {
            return (size - 1) & key.hashCode();
        }

    }
}
