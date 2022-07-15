package com.lzb;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 测试工具类<br/>
 * Created on : 2022-05-14 14:33
 *
 * @author lizebin
 */
public class TestUtils {

    @Test
    public void should_is_true_for_null_or_blank() {
        assertEquals(0, StringUtils.split("", ",").length);
        assertEquals(0, StringUtils.split(null, ",").length);
        assertEquals(2, StringUtils.split(",", ",").length);
        assertEquals(1, StringUtils.split("1", ",").length);
        assertEquals(2, StringUtils.split("1,2", ",").length);
    }

}
