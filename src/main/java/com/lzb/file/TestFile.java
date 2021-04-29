package com.lzb.file;

import java.io.*;

/**
 * 测试文件按行读取，如果文件很多，是否能按行读取<br/>
 * Created on : 2021-04-29 19:05
 * @author chenpi 
 */
public class TestFile {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("xxx.txt")));

        String data = null;
        while((data = br.readLine())!=null)
        {
            System.out.println(data);
        }
        br.close();



    }

}
