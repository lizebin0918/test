package com.lzb.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.Data;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * Fastjson 和 gson 并发压力测试<br/>
 * Created on : 2021-05-20 17:23
 * @author chenpi 
 */
public class JsonLoadTest {

    public static ParserConfig global = new ParserConfig();

    public static void main(String[] args) throws InterruptedException {
        final String jsonString = "{\"buyDate\":\"1617552000\",\"payMoney\":\"0\",\"storeNo\":\"81104\",\"orderNo\":\"100432293097765048\",\"name\":\"片仔癀\",\"count\":\"1.0\",\"drugId\":\"107231\",\"costPrice\":\"476.00\",\"originMoney\":\"590.00\",\"retailPrice\":\"590.00\",\"costAmount\":\"476.00\",\"grossProfitAmount\":\"97.30\"}";

        int threadSize = 1000;
        CountDownLatch latch = new CountDownLatch(threadSize);
        for (int i=0; i<threadSize; i++) {
            Thread thread = new Thread(() -> {

                //所有线程阻塞
                try {
                    latch.countDown();
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String threadName = Thread.currentThread().getName();
                long start = System.currentTimeMillis();

                //fastjson
                {
                    /*System.out.println(JSON.parseObject(jsonString, OrderDetailDTO.class,
                                                        Feature.DisableCircularReferenceDetect,
                                                        Feature.DisableFieldSmartMatch,
                                                        Feature.DisableSpecialKeyDetect).getBuyDate());*/
                    //System.out.println(threadName + fastjsonString);
                    System.out.println(JSONObject.parseObject(jsonString).getString("buyDate"));
                }

                //gson
                {
                    //gson.fromJson(jsonString, OrderDetailDTO.class);
                    //String gsonString = OrderDetailDTO.fromJson(jsonString).toJson();
                    //System.out.println(gsonString);

                    //OrderDetailDTO.fromJson(jsonString);
                }

                //gson parser
                {
                    /*JsonObject o = JsonParser.parseString(jsonString).getAsJsonObject();
                    if (o.has("buyDate")) {
                        System.out.println(o.get("buyDate").toString());
                    }*/
                }

                long end = System.currentTimeMillis();
                System.out.println(threadName + "----->耗时(毫秒):" + (end - start));
            });
            thread.start();
        }

        latch.await();

        System.out.println("完成");

        Thread.sleep(10 * 1000);
    }

    @Data
    private static class OrderDetailDTO {

        /**
         * 下单时间
         */
        private Integer buyDate;


        private static final Gson GSON_WITH_FILTER = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return !f.getName().contains("buyDate");
            }
            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();

        /**
         * 提高容错
         */
        private static final Gson GSON_WITH_TYPE_ADAPTER = new GsonBuilder().registerTypeAdapter(Integer.class, new TypeAdapter<Integer>() {
            @Override
            public void write(JsonWriter jsonWriter, Integer value) throws IOException {
                jsonWriter.value(value);
            }
            @Override
            public Integer read(JsonReader jsonReader) throws IOException {
                String value = jsonReader.nextString();
                if (Objects.toString(value, "").length() == 0) return null;
                for (int i = 0, length = value.length(); i < length; i++) {
                    if (!Character.isDigit(value.charAt(i))) {
                        return null;
                    }
                }
                return Integer.valueOf(value);
            }
        }).registerTypeAdapter(BigDecimal.class, new TypeAdapter<BigDecimal>() {
            @Override
            public void write(JsonWriter jsonWriter, BigDecimal value) throws IOException {
                jsonWriter.value(value);
            }
            @Override
            public BigDecimal read(JsonReader jsonReader) throws IOException {
                String value = jsonReader.nextString();
                if (Objects.toString(value, "").length() == 0) return null;
                for (int i = 0, length = value.length(); i < length; i++) {
                    if (!Character.isDigit(value.charAt(i))) {
                        return null;
                    }
                }
                return new BigDecimal(value);
            }
        }).create();

        final static Gson DEFAULT_GSON = new Gson();

        public static OrderDetailDTO fromJson(String jsonString) {
            return GSON_WITH_TYPE_ADAPTER.fromJson(jsonString, OrderDetailDTO.class);
        }

        public String toJson() {
            return DEFAULT_GSON.toJson(this);
        }
    }
}
