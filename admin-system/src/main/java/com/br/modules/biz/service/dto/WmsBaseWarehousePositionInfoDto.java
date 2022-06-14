package com.br.modules.biz.service.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/**
* @author lin
* @date 2022-06-08
**/
@Data
public class WmsBaseWarehousePositionInfoDto implements Serializable {

    /** 主键 */
    @ApiModelProperty("主键")
    private Long pkWarehousePosition;

    /** 所属库区 */
    @ApiModelProperty("所属库区")
    private Long fkWarehouseArea;

    /** 所属供应商(可存储多个id，逗号分隔) */
    @ApiModelProperty("所属供应商(可存储多个id，逗号分隔)")
    private String supplierIds;

    /** 库位编码 */
    @ApiModelProperty("库位编码")
    private String code;

    /** 库位名称 */
    @ApiModelProperty("库位名称")
    private String name;

    /** 所在列 */
    @ApiModelProperty("所在列")
    private Integer columns;

    /** 状态（1启用，0禁用） */
    @ApiModelProperty("状态（1启用，0禁用）")
    private Integer status;

    /** 创建人id */
    @ApiModelProperty("创建人id")
    private String createBy;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 更新人id */
    @ApiModelProperty("更新人id")
    private String updateBy;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 有效标识（1有效，0无效） */
    @ApiModelProperty("有效标识（1有效，0无效）")
    private Integer isValid;
}