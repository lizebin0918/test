package com.lzb.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * 变量声明成static，这个内存分配在老年代？<br/>
 * Created on : 2023-01-13 10:28
 * @author lizebin
 */
public class TestYoungGc {

    /**
     * 主要放在老年代
     */
    private static final List<byte[]> LIST = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        List<byte[]> list = getList();
        for (int i = 0; i < 50; i++) {
            list.add(new byte[1000 * 1000]);
            Thread.sleep(5000);
            System.out.println("done-" + list.size());
        }

    }

    /**
     * 局部变量
     * @return
     */
    private static List<byte[]> getList() {
        return new ArrayList<>();
        // return LIST;
    }

}
