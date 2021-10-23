package com.lzb.mapstruct;

import com.alibaba.fastjson.JSON;
import com.lzb.mapstruct.mapper.PackageRuleMapper;

/**
 * <br/>
 * Created on : 2021-10-23 15:55
 *
 * @author lizebin
 */
public class Main {

    public static void main(String[] args) {
        RuleSaveOrUpdateDto ruleDto = new RuleSaveOrUpdateDto();
        ruleDto.setId(1L);
        ruleDto.setTitle("this is title");
        ruleDto.setCourierCompanyTypeIds(new Long[]{1L, 2L});

        PackageDistributeRule ruleEntity = PackageRuleMapper.INSTANCE.toEntity(ruleDto);

        System.out.println(JSON.toJSONString(ruleEntity));

    }

}
