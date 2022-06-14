package com.br.modules.biz.service.dto;

import com.br.modules.system.domain.User;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

import javax.persistence.Column;

/**
* @author author
* @date 2022-06-06
**/
@Data
public class WmsBaseSupplierInfoDto implements Serializable {

    /** 供应商pk */
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    @ApiModelProperty("供应商pk")
    private Long pkSupplier;

    /** 编码 */
    @ApiModelProperty("编码")
    private String code;

    /** 简称 */
    @ApiModelProperty("简称")
    private String shortName;

    /** 全称 */
    @ApiModelProperty("全称")
    private String fullName;

    @ApiModelProperty(value = "签约方式")
    private Integer signType;

    @ApiModelProperty(value = "结算方式")
    private Integer settlementType;

    /** 结算周期（月）
             */
    @ApiModelProperty("结算周期（月）")
    private Integer settlementCycle;

    /** 结算日 */
    @ApiModelProperty("结算日")
    private Integer settlementDays;

    /** 责任客服 */
    @ApiModelProperty("责任客服")
    private User user;

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