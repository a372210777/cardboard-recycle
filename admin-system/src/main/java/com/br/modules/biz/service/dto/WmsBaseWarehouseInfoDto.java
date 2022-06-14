package com.br.modules.biz.service.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

/**
* @author lin
* @date 2022-06-06
**/
@Data
public class WmsBaseWarehouseInfoDto implements Serializable {

    /** 主键 */
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    @ApiModelProperty("主键")
    private Long pkWarehouse;

    /** 仓库编码 */
    @ApiModelProperty("仓库编码")
    private String code;

    /** 仓库名称 */
    @ApiModelProperty("仓库名称")
    private String name;

    /** 仓库负责人 */
    @ApiModelProperty("仓库负责人")
    private String principal;

    /** 联系电话 */
    @ApiModelProperty("联系电话")
    private String contactPhone;

    /** 仓库地址 */
    @ApiModelProperty("仓库地址")
    private String address;

    /** 仓库地区 */
    @ApiModelProperty("仓库地区")
    private String area;

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

    @ApiModelProperty(value = "状态（1启用，0禁用）")
    private Integer status;

    /** 有效标识（1有效，0无效） */
    @ApiModelProperty("有效标识（1有效，0无效）")
    private Integer isValid;
}