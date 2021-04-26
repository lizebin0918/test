package com.lzb.interview;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

/**
 * <br/>
 * Created on : 2021-04-26 15:41
 * @author chenpi 
 */
public class Test3 {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("/Users/lizebin/Desktop/logs/info-2021-04-26.5.log");
        List<String> list = Files.readAllLines(path);
        TreeMap<String, Integer> map = new TreeMap<>();
        //时间-请求数
        /*list.forEach(s -> {
            if (!s.contains("uri为")) {
                return;
            }
            String key = s.substring(0, 17);
            int counter = map.getOrDefault(key, 0);
            map.put(key, counter + 1);
        });*/
        list.forEach(s -> {
            if (!s.contains("uri为")) {
                return;
            }
            if (!s.startsWith("2021-04-26 14:2")) {
                return;
            }
            int si = s.indexOf("uri为"), ei = s.lastIndexOf(",");
            String key = s.substring(si, ei);
            int counter = map.getOrDefault(key, 0);
            map.put(key, counter + 1);
        });
        map.forEach((k, v) -> {
            if (v > 100) {
                System.out.println(k + ":" + v);
            }
        });
    }

}
