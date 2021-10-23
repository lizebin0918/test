package com.lzb.mapstruct;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 规则聚合根<br/>
 * Created on : 2021-10-09 11:21
 *
 * @author lizebin
 */
@Data
@Slf4j
public class PackageDistributeRule {

    /**
     * 主键
     */
    private Long id;

    /**
     * 规则类型：1、包裹分派物流逻辑
     */
    private Integer type;

    /**
     * 渠道集合
     */
    private Long[] courierCompanyTypeIds;

    /**
     * 添加/修改人员
     */
    private Long operator;

    /**
     * 禁用标识
     */
    private Boolean disableFlag;

    /**
     * 优先级:1 是最高级
     */
    private Integer priority;

    /**
     * 规则明细，以JSON存储
     */
    private JSON content;

    /**
     * 规则名称
     */
    private String title;

    /**
     * 规则内容
     */
    private RuleContent ruleContent;

}
