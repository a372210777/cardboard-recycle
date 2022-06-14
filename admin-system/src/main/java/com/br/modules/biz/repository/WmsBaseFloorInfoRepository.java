package com.br.modules.biz.repository;

import com.br.modules.biz.domain.WmsBaseFloorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author lin
* @date 2022-06-08
**/
public interface WmsBaseFloorInfoRepository extends JpaRepository<WmsBaseFloorInfo, Long>, JpaSpecificationExecutor<WmsBaseFloorInfo> {
}