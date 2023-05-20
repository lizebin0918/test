package com.lzb.jdk;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

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

        ReceiverAddress receiverAddress = new ReceiverAddress(country, city, area, receiver, null, 0);

        // 获取收件人country，receiverAddress 有可能为空
        System.out.println(Optional.ofNullable(receiverAddress)
                .flatMap(ReceiverAddress::getCountry)
                .map(Country::getName)
                .orElse("无国家"));

        Optional<Person> p = getPerson();

        // convert(receiverAddress, p);

        // 好丑的写法
        // p.ifPresentOrElse(i -> convert(receiverAddress, i), () -> convert(receiverAddress));

        // if/else 也丑
        /*if (p.isEmpty()) {
            convert(receiverAddress);
        } else {
            // 如果多个Optional呢？
            convert(receiverAddress, p.get());
        }*/

        convert(receiverAddress);
        // p.ifPresent(receiverAddress::fillPerson);
        p.ifPresent(person -> fillPerson(receiverAddress, person));

    }

    private static void convert(ReceiverAddress receiverAddress, @NonNull Optional<Person> person) {
        if (person.isPresent()) {
            Person person1 = person.get();
            receiverAddress.setPersonAge(person1.getAge());
            receiverAddress.setPersonName(person1.getName());
        }
    }

    private static void convert(ReceiverAddress receiverAddress, @NonNull Person person) {
        convert(receiverAddress);
        receiverAddress.setPersonAge(person.getAge());
        receiverAddress.setPersonName(person.getName());
    }

    private static void convert(ReceiverAddress receiverAddress) {
        System.out.println("收货人地址转换");
    }

    private static void fillPerson(ReceiverAddress receiverAddress, @NonNull Person person) {
        receiverAddress.setPersonAge(person.getAge());
        receiverAddress.setPersonName(person.getName());
    }


    private static Optional<Person> getPerson() {
        return Optional.empty();
    }

    @Data
    private static class Person {
        private int age;
        private String name;
    }

    /**
     * 收件人地址
     */
    @Setter
    @Getter
    @AllArgsConstructor
    private static class ReceiverAddress {

        private Country country;
        private City city;
        private Area area;
        private Receiver receiver;
        private String personName;
        private int personAge;

        public Optional<OptionalTest.Country> getCountry() {
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
