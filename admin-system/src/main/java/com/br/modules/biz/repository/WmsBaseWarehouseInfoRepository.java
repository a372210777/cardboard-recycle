package com.br.modules.biz.repository;

import com.br.modules.biz.domain.WmsBaseWarehouseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author lin
* @date 2022-06-06
**/
public interface WmsBaseWarehouseInfoRepository extends JpaRepository<WmsBaseWarehouseInfo, Long>, JpaSpecificationExecutor<WmsBaseWarehouseInfo> {
}