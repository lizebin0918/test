package com.lzb.jdk;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * 测试时间工具类<br/>
 * Created on : 2021-09-01 17:49
 *
 * @author lizebin
 */
public class TestTime {

    public static void main(String[] args) {
        OffsetDateTime time = OffsetDateTime.parse("2021-09-01T00:48:12-07:00");
        // 转成本地时间
        System.out.println(time.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
        System.out.println(time.atZoneSimilarLocal(ZoneId.systemDefault()).toLocalDateTime());
        System.out.println(time.toLocalDateTime());

        System.out.println(ZoneId.systemDefault());
        OffsetDateTime offsetDateTime = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.ofHours(8));
        System.out.println("-8偏移量时间为：" + offsetDateTime);

        // 转换为ZonedDateTime的表示形式
        System.out.println("ZonedDateTime的表示形式：" + offsetDateTime.toZonedDateTime());
        // atZoneSameInstant()：将此日期时间与时区结合起来创建ZonedDateTime，以确保结果具有相同的Instant，(本地)当地时间点
        // 等价于：LocalDateTime.ofInstant(Instant.ofEpochSecond(offsetDateTime.toEpochSecond()), ZoneId.systemDefault())
        System.out.println("ZonedDateTime的表示形式：" + offsetDateTime.atZoneSameInstant(ZoneId.systemDefault()));// 北京时间
        System.out.println("ZoneId.of(\"America/New_York\")-ZonedDateTime的表示形式：" + offsetDateTime.atZoneSameInstant(ZoneId.of("America/New_York")));// 纽约时间
        // atZoneSimilarLocal：将此日期时间与时区结合起来创建ZonedDateTime，以确保结果具有相同的本地时间，
        System.out.println("ZonedDateTime的表示形式：" + offsetDateTime.atZoneSimilarLocal(ZoneId.systemDefault()));// 北京时间
        System.out.println("ZoneId.of(\"America/New_York\")-ZonedDateTime的表示形式：" + offsetDateTime.atZoneSimilarLocal(ZoneId.of("America/New_York")));// 纽约时间
        // OffsetDateTime = LocalDateTime + 偏移量ZoneOffset；ZonedDateTime = LocalDateTime + 时区ZoneId
        //OffsetDateTime可以随意设置偏移值，但ZonedDateTime无法自由设置偏移值，因为此值是由时区ZoneId控制的
    }

}
