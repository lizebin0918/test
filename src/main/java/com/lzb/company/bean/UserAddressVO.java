package com.lzb.company.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserAddressVO implements Serializable {
    private Long id;

    private Long uid;

    private String firstName;

    private String lastName;

    private String addressLine1;

    private String addressLine2;

    private String country;

    private String state;

    private String city;

    private String zipCode;

    /**
     * 国家简码
     */
    private String simpleCode;

    private String phoneNumber;

    private Boolean isDel;

    private Boolean isDefault;

    private Date upTime;

    /**
     * 我们系统里面countryid
     */
    private Long countryId;
    /**
     * 省id
     */
    private Long stateId;
    /**
     * 增加区号
     */
    private String phoneCode;

    /**
     * 税号
     */
    private String taxNumber;
    /**
     * 用户邮箱
     */
    private String email;
}
