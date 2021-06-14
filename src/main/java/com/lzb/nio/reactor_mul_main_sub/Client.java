package com.lzb.nio.reactor_mul_main_sub;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("localhost", 8080));

            System.out.println("client start");
            new Thread(() -> {
                while (true) {
                    System.out.println("client read...");
                    try {
                        InputStream inputStream = socket.getInputStream();
                        byte[] bytes = new byte[1024];
                        inputStream.read(bytes);
                        System.out.println(new String(bytes, StandardCharsets.UTF_8));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            while (true) {
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNextLine()) {
                    String s = scanner.nextLine();
                    System.out.println("client input :" + s);
                    socket.getOutputStream().write(s.getBytes());
                    System.out.println("client output :" + s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
