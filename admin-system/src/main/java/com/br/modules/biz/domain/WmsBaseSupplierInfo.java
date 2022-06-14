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
* @author author
* @date 2022-06-06
**/
@Entity
@Data
@Table(name="wms_base_supplier_info")
public class WmsBaseSupplierInfo  extends BaseEntity implements Serializable {

    @Id
    @Column(name = "pk_supplier")
    @ApiModelProperty(value = "供应商pk")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkSupplier;

    @Column(name = "code",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "编码")
    private String code;

    @Column(name = "short_name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "简称")
    private String shortName;

    @Column(name = "full_name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "全称")
    private String fullName;

    @Column(name = "sign_type")
    @ApiModelProperty(value = "签约方式")
    private Integer signType;

    @Column(name = "settlement_type")
    @ApiModelProperty(value = "结算方式")
    private Integer settlementType;

    @Column(name = "settlement_cycle")
    @ApiModelProperty(value = "结算周期（月）")
    private Integer settlementCycle;

    @Column(name = "settlement_days")
    @ApiModelProperty(value = "结算日")
    private Integer settlementDays;

    @ApiModelProperty(value = "责任客服")
    @JoinColumn(name = "fk_user")
    @ManyToOne
    private User user;

    /**
     * 被禁用的供应商，将无法新建对应的出入库单
     */
    @Column(name = "status",nullable = false)
    @NotNull
    @ApiModelProperty(value = "状态（1启用，0禁用）")
    private Integer status;

    @Column(name = "is_valid")
    @ApiModelProperty(value = "有效标识（1有效，0无效）")
    private Integer isValid = 1;

    public void copy(WmsBaseSupplierInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}