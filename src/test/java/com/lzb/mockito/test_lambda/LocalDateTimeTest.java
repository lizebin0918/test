package com.lzb.mockito.test_lambda;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.checkerframework.checker.units.qual.Speed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LocalDateTimeTest {
    MockedStatic<LocalDateTime> localDateTimeMocked;


    @AfterEach
    void tearDown() {
        localDateTimeMocked.closeOnDemand();
    }

    @Test
    void executesTrainOnSaturday() {
        // 默认调用实际方法
        localDateTimeMocked = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS);
        LocalDateTime now = LocalDateTime.of(2022, 10, 22, 10, 0);
        localDateTimeMocked.when(LocalDateTime::now).thenReturn(now);
        Assertions.assertEquals(now, LocalDateTime.now());
        System.out.println(LocalDateTime.now(ZoneId.systemDefault()));
    }
}