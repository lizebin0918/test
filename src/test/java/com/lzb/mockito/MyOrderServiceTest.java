package com.lzb.mockito;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * <br/>
 * Created on : 2023-02-02 10:05
 * @author lizebin
 */
@ExtendWith(MockitoExtension.class)
class MyOrderServiceTest {

    @Mock
    private MyOrderDao myOrderDao;

    @InjectMocks
    private MyOrderService myOrderService;

    @Test
    void should_dao_arg() {
        String assertString = "ababc";
        when(myOrderDao.doDelete(any(), any())).thenReturn(assertString);
        doNothing().when(myOrderDao).doDelete(anyList());
        assertEquals(assertString, myOrderService.deleteById("a"));

        // 验证myOrderDao 入参
        ArgumentCaptor<String> orderDaoArg0 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> orderDaoArg1 = ArgumentCaptor.forClass(String.class);
        verify(myOrderDao).doDelete(orderDaoArg0.capture(), orderDaoArg1.capture());
        assertEquals("ab", orderDaoArg0.getValue());
        assertEquals("abc", orderDaoArg1.getValue());

        /**
         * 执行多次
         * Mockito.verify(personRepository, Mockito.times(2)).delete(captor.capture());
         *
         * List<Person> allValues = captor.getAllValues();
         *
         * for (Person captured : allValues) {
         *     Assertions.assertThat(captured.getName()).isEqualTo("deleted");
         * }
         */
        ArgumentCaptor<List<String>> orderDaoArg2 = ArgumentCaptor.forClass(List.class);
        verify(myOrderDao).doDelete(orderDaoArg2.capture());
        assertArrayEquals(new String[]{"ab"}, orderDaoArg2.getValue().toArray(String[]::new));

    }

}
