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
* @date 2022-06-08
**/
@Entity
@Data
@Table(name="wms_base_warehouse_position_info")
public class WmsBaseWarehousePositionInfo extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_warehouse_position")
    @ApiModelProperty(value = "主键")
    private Long pkWarehousePosition;

    @Column(name = "fk_warehouse_area",nullable = false)
    @NotNull
    @ApiModelProperty(value = "所属库区")
    private Long fkWarehouseArea;

    @Column(name = "supplier_ids",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "所属供应商(可存储多个id，逗号分隔)")
    private String supplierIds;

    @Column(name = "code",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "库位编码")
    private String code;

    @Column(name = "name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "库位名称")
    private String name;

    @Column(name = "columns",nullable = false)
    @NotNull
    @ApiModelProperty(value = "所在列")
    private Integer columns;

    @Column(name = "status",nullable = false)
    @NotNull
    @ApiModelProperty(value = "状态（1启用，0禁用）")
    private Integer status;


    @Column(name = "is_valid",nullable = false)
    @ApiModelProperty(value = "有效标识（1有效，0无效）")
    private Integer isValid;

    public void copy(WmsBaseWarehousePositionInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}