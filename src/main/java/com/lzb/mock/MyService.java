package com.lzb.mock;

/**
 * <br/>
 * Created on : 2022-03-21 23:02
 *
 * @author lizebin
 */
public class MyService {

    /**
     * method with common method invoke
     */
    public String should_mock_common_method() {
        return "anything".trim() + "__" + "anything".substring(1, 2) + "__" + "abc".startsWith("ab");
    }

}
