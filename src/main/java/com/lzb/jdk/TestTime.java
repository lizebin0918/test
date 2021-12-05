package com.lzb.jdk;

import java.time.*;
import java.time.format.DateTimeFormatter;

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

        // Z表示零时区，前端提交的参数
        OffsetDateTime oft = OffsetDateTime.parse("2021-12-08T15:59:59.000Z");
        System.out.println("前端返回零时区时间：" + oft);

        offsetDateTime = OffsetDateTime.parse("2021-12-08T15:59:59.000Z");
        System.out.println(LocalDateTime.ofInstant(Instant.ofEpochSecond(offsetDateTime.toEpochSecond()), ZoneId.systemDefault()));

        // 带偏移量的格式：2021-12-03T08:35:30.092297+08:00
        System.out.println(OffsetDateTime.now());
        // 只是把当前时间改成UTC时间
        System.out.println(OffsetDateTime.now().withOffsetSameLocal(ZoneOffset.UTC));
        // 根据当前时间的时间戳，转成UTC时间
        System.out.println(OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC));

        String format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        System.out.println(OffsetDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        // 2021-12-03T08:47:58.930004+08:00
        System.out.println(OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        // 2021-12-03T08:47:58.930098+08:00
        System.out.println(OffsetDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        // 2021-12-03T08:47:58.930184+08:00
        System.out.println(OffsetDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME));

        // 2021-12-03T00:47:58.930Z UTC时间
        System.out.println(OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern(format)));
    }

}
