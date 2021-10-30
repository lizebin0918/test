package com.lzb.mapstruct;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 新增、更新实体<br/>
 * Created on : 2021-10-01 17:43
 *
 * @author lizebin
 */
@Data
public class RuleSaveOrUpdateDto {

    /**
     * 主键
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 规则明细
     */
    private JSON content;

    /**
     * 渠道集合
     */
    private Long[] courierCompanyTypeIds;

    /**
     * 添加/修改人员
     */
    private Long uid;

}
