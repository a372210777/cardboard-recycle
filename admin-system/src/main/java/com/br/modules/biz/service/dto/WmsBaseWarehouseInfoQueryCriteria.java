package com.br.modules.biz.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;
import com.br.annotation.Query;

/**
* @author lin
* @date 2022-06-06
**/
@Data
public class WmsBaseWarehouseInfoQueryCriteria{

    @Query(type = Query.Type.INNER_LIKE)
    @ApiModelProperty(value = "仓库名称")
    private String name;

    @Query(type = Query.Type.INNER_LIKE)
    @ApiModelProperty(value = "仓库编码")
    private String code;

    @Query(type = Query.Type.INNER_LIKE)
    @ApiModelProperty(value = "仓库地区")
    private String area;

}