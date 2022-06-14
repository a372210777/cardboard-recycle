package com.br.modules.biz.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;
import com.br.annotation.Query;

/**
* @author author
* @date 2022-06-06
**/
@Data
public class WmsBaseSupplierInfoQueryCriteria{


    @Query(blurry = "shortName,fullName")
    @ApiModelProperty(value = "供应商名称")
    private String name;

    @Query(type = Query.Type.INNER_LIKE)
    @ApiModelProperty(value = "供应商编码")
    private String code;
}