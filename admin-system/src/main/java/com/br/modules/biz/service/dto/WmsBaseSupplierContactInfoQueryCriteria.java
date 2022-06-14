package com.br.modules.biz.service.dto;

import lombok.Data;
import java.util.List;
import com.br.annotation.Query;

/**
* @author author
* @date 2022-06-06
**/
@Data
public class WmsBaseSupplierContactInfoQueryCriteria{

    @Query(propName = "pkSupplier",joinName = "supplier")
    private Long fkSupplier;

}