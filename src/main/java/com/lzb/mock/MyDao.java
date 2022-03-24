package com.lzb.mock;

/**
 * <br/>
 * Created on : 2022-03-22 12:48
 *
 * @author lizebin
 */
public class MyDao {

    /**
     * method with common method invoke
     */
    public String should_mock_common_method() {
        return "anything".trim() + "__" + "anything".substring(1, 2) + "__" + "abc".startsWith("ab");
    }

}
