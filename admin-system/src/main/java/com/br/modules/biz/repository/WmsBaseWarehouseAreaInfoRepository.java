package com.br.modules.biz.repository;

import com.br.modules.biz.domain.WmsBaseWarehouseAreaInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
* @author lin
* @date 2022-06-08
**/
public interface WmsBaseWarehouseAreaInfoRepository extends JpaRepository<WmsBaseWarehouseAreaInfo, Long>, JpaSpecificationExecutor<WmsBaseWarehouseAreaInfo> {
    List<WmsBaseWarehouseAreaInfo> findByFloorInfo_PkFloor(Long pkFloor);
}