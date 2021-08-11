package com.lzb.javers;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;

import java.math.BigDecimal;

/**
 * <br/>
 * Created on : 2021-08-06 11:50
 *
 * @author lizebin
 */
public class Main {

    public static void main(String[] args) {
        Staff tommy = Staff.builder()
                .name("Tommy")
                .age(18)
                .height(180d)
                .salary(new BigDecimal("10000"))
                .hobbies(Lists.newArrayList("film", "game"))
                .phones(ImmutableMap.of("home", "1234", "office", "4321"))
                .manager(Staff.builder().name("ok").build())
                .build();
        Staff ggg = Staff.builder()
                .name("ggg")
                .age(17)
                .height(180.000000001d)
                .hobbies(Lists.newArrayList("game", "music", "travel"))
                .phones(ImmutableMap.of("mobile", "4321", "home", "1235"))
                .manager(Staff.builder().name("ok").build())
                .build();
        Javers javers = JaversBuilder.javers().build();
        Diff diff = javers.compare(tommy, ggg);

        System.out.println(0xFF);
        System.out.println(0x00);
        System.out.println(diff);

    }

}
