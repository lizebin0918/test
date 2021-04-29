package com.lzb.file;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * 1亿个数如何排序？流式归并<br/>
 * Created on : 2021-04-29 19:05
 * @author chenpi 
 */
public class MergeSortByFile {

    public static void main(String[] args) throws IOException {

    }

    /**
     * 写文件
     * @param file
     * @param size
     * @throws IOException
     */
    public void writeFile(Path file, int size) throws IOException {
        Random random = new Random();
        int batchSize = 10 * 10000;
        List<String> batch = new ArrayList<>(batchSize);
        if (!Files.exists(file)) Files.createFile(file);
        for (int i=0; i<size; i++) {
            batch.add(Objects.toString(random.nextInt()));
            if (i % batchSize == 0) {
                Files.write(file, batch, StandardOpenOption.APPEND);
            }
        }
        System.out.println("创建文件成功，个数：" + size);
    }

    /**
     * 读取文件
     * @param file
     * @param size
     * @return
     * @throws IOException
     */
    public List<Integer> readFile(Path file, int size) throws IOException {
        BufferedReader br = Files.newBufferedReader(file);
        String data = null;
        List<Integer> list = new ArrayList<>(size);
        while ((data = br.readLine()) != null) {
            list.add(Integer.valueOf(data));
            if (--size == 0) {
                break;
            }
        }
        br.close();
        return list;
    }

}
