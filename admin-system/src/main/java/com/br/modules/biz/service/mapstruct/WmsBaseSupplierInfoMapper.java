package com.br.modules.biz.service.mapstruct;


import com.br.base.BaseMapper;
import com.br.modules.biz.domain.WmsBaseSupplierInfo;
import com.br.modules.biz.service.dto.WmsBaseSupplierInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author author
* @date 2022-06-06
**/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WmsBaseSupplierInfoMapper extends BaseMapper<WmsBaseSupplierInfoDto, WmsBaseSupplierInfo> {

}