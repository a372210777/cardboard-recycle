package com.br.modules.biz.service.mapstruct;

import com.br.base.BaseMapper;
import com.br.modules.biz.domain.WmsBaseWarehousePositionInfo;
import com.br.modules.biz.service.dto.WmsBaseWarehousePositionInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author lin
* @date 2022-06-08
**/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WmsBaseWarehousePositionInfoMapper extends BaseMapper<WmsBaseWarehousePositionInfoDto, WmsBaseWarehousePositionInfo> {

}