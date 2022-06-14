package com.br.modules.biz.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;
import com.br.annotation.Query;

/**
* @author lin
* @date 2022-06-08
**/
@Data
public class WmsBaseFloorInfoQueryCriteria{

    @Query(type = Query.Type.INNER_LIKE)
    @ApiModelProperty(value = "楼层名称")
    private String name;

    @Query(type = Query.Type.INNER_LIKE)
    @ApiModelProperty(value = "楼层编码")
    private String code;
}