package com.lzb.mapstruct.mapper;

import com.lzb.mapstruct.PackageDistributeRule;
import com.lzb.mapstruct.RuleSaveOrUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 映射类<br/>
 * Created on : 2021-10-23 15:51
 *
 * @author lizebin
 */
@Mapper
public interface PackageRuleMapper {

    PackageRuleMapper INSTANCE = Mappers.getMapper(PackageRuleMapper.class);

    /**
     * 转换成实体
     * @param ruleEntity
     * @return
     */
    PackageDistributeRule toEntity(RuleSaveOrUpdateDto ruleSaveOrUpdateDto);

}
