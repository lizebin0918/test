package com.lzb.seq;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import com.alibaba.fastjson.JSON;

/**
 * <br/>
 * Created on : 2023-04-30 21:23
 * @author mac
 */
public class SeqTest {

    public static void main(String[] args) {

        List<Integer> list = List.of(1, 2, 3);
        Seq<Integer> seq = list::forEach;

        // 转字符串
        List<String> strings = seq.map(Objects::toString).toList();
        System.out.println(strings);

        List<Integer> integers = seq.toList();
        System.out.println(JSON.toJSONString(integers));

        // 流读取
        Seq<String> seq1 = c -> {
            try (BufferedReader reader = Files.newBufferedReader(Paths.get("file"))) {
                String s;
                while ((s = reader.readLine()) != null) {
                    c.accept(s);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

}
