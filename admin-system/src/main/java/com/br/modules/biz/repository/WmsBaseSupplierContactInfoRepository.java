package com.br.modules.biz.repository;

import com.br.modules.biz.domain.WmsBaseSupplierContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author author
* @date 2022-06-06
**/
public interface WmsBaseSupplierContactInfoRepository extends JpaRepository<WmsBaseSupplierContactInfo, Long>, JpaSpecificationExecutor<WmsBaseSupplierContactInfo> {
}