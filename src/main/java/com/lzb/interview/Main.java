package com.lzb.interview;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/>
 * Created on : 2022-02-22 14:17
 *
 * @author lizebin
 */
public class Main {

    private List<String> list = new ArrayList<>();

    private volatile boolean running = false;

    public void stop() {
        new Thread(() -> {
            running = true;
            while (true) {
                System.out.println();
                if (list.size() == 5) {
                    running = false;
                    break;
                }
            }
        }).start();
    }

    public void start() {
        new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.add("1");
            }
        }).start();
    }

    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();
        main.stop();
        // 保证stop先启动
        Thread.sleep(1000);
        main.start();
    }

}
