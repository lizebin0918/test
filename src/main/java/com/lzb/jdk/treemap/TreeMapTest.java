package com.lzb.jdk.treemap;

import java.util.TreeMap;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-12-04 16:50
 * @author lizebin
 */
public class TreeMapTest {

    public static void main(String[] args) {
        TreeMap<Key, String> treeMap = new TreeMap<>();
        treeMap.put(Key.of(1L, true), "1");
        treeMap.put(Key.of(1L, false), "2");
        treeMap.put(Key.of(1L, true), "3");
        treeMap.put(Key.of(2L, true), "4");
        System.out.println(treeMap.size() == 3);
        System.out.println(treeMap.get(Key.of(1L, true)).equals("3"));
        System.out.println(treeMap.get(Key.of(1L, false)).equals("2"));
        System.out.println(treeMap.get(Key.of(4L, true)) == null);
        System.out.println(treeMap.get(Key.of(2L, true)).equals("4"));
    }

    @Getter
    @EqualsAndHashCode
    @AllArgsConstructor(staticName = "of")
    private static class Key implements Comparable<Key> {
        private final long packageId;
        private final boolean isVirtual;


        /**
         * 需要比对所有字段
         * @param o the object to be compared.
         * @return
         */
        @Override
        public int compareTo(@NotNull Key o) {
            if (packageId != o.packageId) {
                return (int) (this.packageId - o.packageId);
            }
            return (isVirtual ? 1 : 0) - (o.isVirtual ? 1 : 0);
        }
    }
}
