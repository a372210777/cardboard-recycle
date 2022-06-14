package com.br.modules.biz.service.mapstruct;

import com.br.base.BaseMapper;
import com.br.modules.biz.domain.WmsBaseWarehouseInfo;
import com.br.modules.biz.service.dto.WmsBaseWarehouseInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author lin
* @date 2022-06-06
**/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WmsBaseWarehouseInfoMapper extends BaseMapper<WmsBaseWarehouseInfoDto, WmsBaseWarehouseInfo> {

}