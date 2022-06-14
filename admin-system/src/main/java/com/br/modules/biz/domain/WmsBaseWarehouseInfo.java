package com.br.modules.biz.domain;

import com.br.base.BaseEntity;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

/**
* @author lin
* @date 2022-06-06
**/
@Entity
@Data
@Table(name="wms_base_warehouse_info")
public class WmsBaseWarehouseInfo  extends BaseEntity implements Serializable {

    @Id
    @Column(name = "pk_warehouse")
    @ApiModelProperty(value = "主键")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkWarehouse;

    @Column(name = "code",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "仓库编码")
    private String code;

    @Column(name = "name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "仓库名称")
    private String name;

    @Column(name = "principal")
    @ApiModelProperty(value = "仓库负责人")
    private String principal;

    @Column(name = "contact_phone")
    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @Column(name = "address")
    @ApiModelProperty(value = "仓库地址")
    private String address;

    @Column(name = "area")
    @NotBlank
    @ApiModelProperty(value = "仓库地区")
    private String area;

    /**
     * 当仓库状态改为禁用时，该楼层的库区、库位将无法做入库、出库作业
     */
    @Column(name = "status",nullable = false)
    @NotNull
    @ApiModelProperty(value = "状态（1启用，0禁用）")
    private Integer status;

    @Column(name = "is_valid")
    @ApiModelProperty(value = "有效标识（1有效，0无效）")
    private Integer isValid = 1;

    public void copy(WmsBaseWarehouseInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}