package com.lzb.mock;

/**
 * <br/>
 * Created on : 2022-03-21 23:02
 *
 * @author lizebin
 */
public class MyService {

    private MyDao dao = new MyDao();

    /**
     * method with common method invoke
     */
    public String string() {
        return dao.should_mock_common_method();
    }

}
