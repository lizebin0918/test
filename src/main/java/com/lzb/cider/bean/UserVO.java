package com.lzb.cider.bean;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserVO implements Serializable {

    /**
     * 用户唯一id
     */
    private Long uid;

    /**
     * 姓、名
     */
    private String familyName, givenName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 生日 2020-08-03
     */
    private String birthday;

    /**
     * 性别 1女生 2男性 3未知
     */
    private Integer sex;

    /**
     * 用户图像，绝对地址
     */
    private String headPic;

    /**
     * 用户密码、盐
     */
    private String password, salt;

    /**
     * 用户状态 -1注销 1有效
     */
    private Integer userStatus;

    /**
     * 用户来源
     */
    private String userSource;

    /**
     * 添加、更新时间
     */
    private LocalDateTime addTime, updateTime;

}
