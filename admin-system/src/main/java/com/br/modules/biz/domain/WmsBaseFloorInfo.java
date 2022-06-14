package com.br.modules.biz.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.br.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
* @author lin
* @date 2022-06-08
**/
@Entity
@Data
@Table(name="wms_base_floor_info")
public class WmsBaseFloorInfo extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_floor")
    @ApiModelProperty(value = "主键")
    private Long pkFloor;

    @ApiModelProperty(value = "所属仓库")
    @JoinColumn(name = "fk_warehouse")
    @ManyToOne
    private WmsBaseWarehouseInfo warehouseInfo;

    @Column(name = "code",nullable = false)
    @ApiModelProperty(value = "楼层编码")
    private String code;

    @Column(name = "name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "楼层名称")
    private String name;

    @Column(name = "floor_no",nullable = false)
    @NotNull
    @ApiModelProperty(value = "所在楼层")
    private Integer floorNo;

    /**
     * 当楼层状态改为禁用时，该楼层的库区、库位将无法做入库、出库作业
     */
    @Column(name = "status",nullable = false)
    @NotNull
    @ApiModelProperty(value = "状态（1启用，0禁用）")
    private Integer status;


    @Column(name = "is_valid",nullable = false)
    @ApiModelProperty(value = "有效标识（1有效，0无效）")
    private Integer isValid;

    public void copy(WmsBaseFloorInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}