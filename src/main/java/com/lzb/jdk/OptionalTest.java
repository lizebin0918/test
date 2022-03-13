package com.lzb.jdk;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Optional;

/**
 * <br/>
 * Created on : 2021-12-05 23:05
 *
 * @author lizebin
 */
public class OptionalTest {

    public static void main(String[] args) {

        Country country = new Country();
        country.setName("中国");

        City city = new City();
        city.setName("广东省");

        Area area = new Area();
        area.setName("广州市");

        Receiver receiver = new Receiver();
        receiver.setName("陈皮");
        receiver.setMobile("13800000000");

        ReceiverAddress receiverAddress = new ReceiverAddress(country, city, area, receiver);

        // 获取收件人country，receiverAddress 有可能为空
        System.out.println(Optional.ofNullable(receiverAddress).flatMap(ReceiverAddress::getCountry).map(Country::getName).orElse("无国家"));

    }

    /**
     * 收件人地址
     */
    @Getter
    @AllArgsConstructor
    private static class ReceiverAddress {

        private Country country;
        private City city;
        private Area area;
        private Receiver receiver;

        public Optional<Country> getCountry() {
            return Optional.ofNullable(country);
        }

        public Optional<City> getCity() {
            return Optional.ofNullable(city);
        }

        public Optional<Area> getArea() {
            return Optional.ofNullable(area);
        }

        public Optional<Receiver> getReceiver() {
            return Optional.ofNullable(receiver);
        }
    }

    @Data
    private static class Receiver {

        private String name;
        private String mobile;

    }

    /**
     * 国家
     */
    @Data
    private static class Country {

        private String name;

    }

    /**
     * 城市
     */
    @Data
    private static class City {

        private String name;

    }

    /**
     * 地区
     */
    @Data
    private static class Area {

        private String name;

    }

}
