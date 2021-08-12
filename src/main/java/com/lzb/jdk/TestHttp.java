package com.lzb.jdk;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.Authenticator;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.util.concurrent.Flow;

/**
 * Http测试<br/>
 * Created on : 2021-08-12 15:41
 *
 * @author lizebin
 */
public class TestHttp {

    public static void main(String[] args) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.baidu.com"))
                .GET()
                .build();
        var client = HttpClient.newHttpClient();

        // 同步
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("---------body--------------");
        System.out.println(response.body());
        System.out.println("---------header--------------");
        System.out.println(JSON.toJSONString(response.headers().map()));

        System.out.println("----------------------------");

        // 异步
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println);

        Thread.sleep(10000);
    }

}
