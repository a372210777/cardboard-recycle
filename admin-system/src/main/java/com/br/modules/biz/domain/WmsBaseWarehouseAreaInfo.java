package com.br.modules.biz.domain;

import com.br.base.BaseEntity;
import com.br.modules.system.domain.User;
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
@Table(name="wms_base_warehouse_area_info")
public class WmsBaseWarehouseAreaInfo extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_warehouse_area")
    @ApiModelProperty(value = "主键")
    private Long pkWarehouseArea;

    @ApiModelProperty(value = "所属楼层")
    @JoinColumn(name = "fk_floor")
    @ManyToOne
    @NotNull
    private WmsBaseFloorInfo floorInfo;

    @Column(name = "code",nullable = false)
    @ApiModelProperty(value = "库区编码")
    private String code;

    @Column(name = "name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "库区名称")
    private String name;

    @Column(name = "type",nullable = false)
    @NotNull
    @ApiModelProperty(value = "库区类型")
    private Integer type;

    @Column(name = "status",nullable = false)
    @NotNull
    @ApiModelProperty(value = "状态（1启用，0禁用）")
    private Integer status;


    @Column(name = "is_valid",nullable = false)
    @ApiModelProperty(value = "有效标识（1有效，0无效）")
    private Integer isValid;

    public void copy(WmsBaseWarehouseAreaInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}