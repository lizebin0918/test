package com.lzb.jdk.hashmap;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 生成 hashcode 的乘数为什么是31<br/>
 * Created on : 2021-10-10 20:44
 *
 * @author lizebin
 */
public class HashCodeMultipier31Test {

    public static void main(String[] args) {
        int count = 100_000;
        var randomStringList = Stream.generate(() -> RandomStringUtils.randomAlphabetic(10)).limit(count).collect(Collectors.toList());
        /**
         * 乘数 =    2, 最小Hash =       67727, 最大Hash =     123019, 碰撞数量 = 62232, 碰撞概率 = 62.2320%
         * 乘数 =    3, 最小Hash =     1928877, 最大Hash =    3576167, 碰撞数量 =  3767, 碰撞概率 = 3.7670%
         * 乘数 =    5, 最小Hash =   158887841, 最大Hash =  297110675, 碰撞数量 =    35, 碰撞概率 = 0.0350%
         * 乘数 =    7, 最小Hash = -1233205939, 最大Hash = 1442659457, 碰撞数量 =     1, 碰撞概率 = 0.0010%
         * 乘数 =   17, 最小Hash = -2147477357, 最大Hash = 2147479918, 碰撞数量 =     2, 碰撞概率 = 0.0020%
         * 乘数 =   31, 最小Hash = -2147464464, 最大Hash = 2147471118, 碰撞数量 =     2, 碰撞概率 = 0.0020%
         * 乘数 =   32, 最小Hash = -2043572560, 最大Hash = 2078101145, 碰撞数量 =     4, 碰撞概率 = 0.0040%
         * 乘数 =   33, 最小Hash = -2147467724, 最大Hash = 2147405092, 碰撞数量 =     2, 碰撞概率 = 0.0020%
         * 乘数 =   39, 最小Hash = -2147417389, 最大Hash = 2147388210, 碰撞数量 =     0, 碰撞概率 = 0.0000%
         * 乘数 =   41, 最小Hash = -2147465321, 最大Hash = 2147480857, 碰撞数量 =     2, 碰撞概率 = 0.0020%
         * 乘数 =  199, 最小Hash = -2147435412, 最大Hash = 2147401634, 碰撞数量 =     0, 碰撞概率 = 0.0000%
         */
        test_collisionRate(randomStringList);

        test_hashArea(randomStringList);
    }

    public static void test_hashArea(List<String> words) {
        System.out.println("2:" + hashArea(words.stream().map(word -> hashCode(word, 2)).collect(Collectors.toList())).values());
        System.out.println("7:" + hashArea(words.stream().map(word -> hashCode(word, 7)).collect(Collectors.toList())).values());
        System.out.println("32:" + hashArea(words.stream().map(word -> hashCode(word, 32)).collect(Collectors.toList())).values());
        System.out.println("31:" + hashArea(words.stream().map(word -> hashCode(word, 31)).collect(Collectors.toList())).values());
        System.out.println("199:" + hashArea(words.stream().map(word -> hashCode(word, 199)).collect(Collectors.toList())).values());
    }

    public static Map<Integer, Integer> hashArea(List<Integer> hashCodeList) {
        Map<Integer, Integer> statistics = new LinkedHashMap<>();
        int start = 0;
        int gap = 64 * 1024 * 1024;
        for (long i = Integer.MIN_VALUE; i <= Integer.MAX_VALUE; i += gap) {
            long min = i;
            long max = min + gap;
            int num = (int) hashCodeList.parallelStream().filter(x -> x >= min && x < max).count();
            statistics.put(start++, num);
        }
        return statistics;
    }

    public static void test_collisionRate(List<String> words) {
        List<RateInfo> rateInfoList = collisionRateList(words, 2, 3, 5, 7, 17, 31, 32, 33, 39, 41, 199);
        for (RateInfo rate : rateInfoList) {
            System.out.println(String.format("乘数 = %4d, 最小Hash = %11d, 最大Hash = %10d, 碰撞数量 =%6d, 碰撞概率 = %.4f%%", rate.getMultiplier(), rate.getMinHash(), rate.getMaxHash(), rate.getCollisionCount(), rate.getCollisionRate() * 100));
        }
    }

    /**
     * 计算冲突比例
     * @param words
     * @param multipliers
     * @return
     */
    public static List<RateInfo> collisionRateList(List<String> words, int... multipliers) {
        List<RateInfo> rateInfoList = new ArrayList<>(multipliers.length);
        for (int multiplier : multipliers) {
            rateInfoList.add(hashCollisionRate(multiplier, words.stream().map(word -> {
                return hashCode(word, multiplier);
            }).collect(Collectors.toList())));
        }
        return rateInfoList;
    }

    /**
     * 计算hash值
     * @param str
     * @param multiplier 乘数
     * @return
     */
    public static Integer hashCode(String str, Integer multiplier) {
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = multiplier * hash + str.charAt(i);
        }
        return hash;
    }

    /**
     * 根据不同的乘数，计算hashCode分布
     * @param multiplier
     * @param hashCodeList
     * @return
     */
    private static RateInfo hashCollisionRate(Integer multiplier, List<Integer> hashCodeList) {
        int maxHash = hashCodeList.stream().max(Integer::compareTo).orElse(0);
        int minHash = hashCodeList.stream().min(Integer::compareTo).orElse(0);
        int collisionCount = (int) (hashCodeList.size() - hashCodeList.stream().distinct().count());
        double collisionRate = (collisionCount * 1.0) / hashCodeList.size();
        return new RateInfo(maxHash, minHash, multiplier, collisionCount, collisionRate);
    }

    @Data
    private static class RateInfo {

        private final Integer maxHash, minHash, multiplier, collisionCount;
        private final Double collisionRate;

        public RateInfo(Integer maxHash, Integer minHash, Integer multiplier, Integer collisionCount, Double collisionRate) {
            this.maxHash = maxHash;
            this.minHash = minHash;
            this.multiplier = multiplier;
            this.collisionCount = collisionCount;
            this.collisionRate = collisionRate;
        }
    }

}
