package com.br.modules.biz.service.dto;

import com.br.modules.biz.domain.WmsBaseSupplierInfo;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

/**
* @author author
* @date 2022-06-06
**/
@Data
public class WmsBaseSupplierContactInfoDto implements Serializable {

    /** 主键 */
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    @ApiModelProperty("主键")
    private Long pkSupplierContact;

    /** 供应商 */
    @ApiModelProperty("供应商")
    private WmsBaseSupplierInfoDto supplier;

    /** 站点名称 */
    @ApiModelProperty("站点名称")
    private String stationName;

    /** 省市区 */
    @ApiModelProperty("省市区")
    private String provinceAndCity;

    /** 地址 */
    @ApiModelProperty("地址")
    private String address;

    /** 联系人 */
    @ApiModelProperty("联系人")
    private String contactName;

    /** 联系电话 */
    @ApiModelProperty("联系电话")
    private String contactPhone;

    /** 备注 */
    @ApiModelProperty("备注")
    private String memo;

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