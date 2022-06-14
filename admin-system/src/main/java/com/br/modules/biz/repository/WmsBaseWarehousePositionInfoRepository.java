package com.br.modules.biz.repository;

import com.br.modules.biz.domain.WmsBaseWarehousePositionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author lin
* @date 2022-06-08
**/
public interface WmsBaseWarehousePositionInfoRepository extends JpaRepository<WmsBaseWarehousePositionInfo, Long>, JpaSpecificationExecutor<WmsBaseWarehousePositionInfo> {
}