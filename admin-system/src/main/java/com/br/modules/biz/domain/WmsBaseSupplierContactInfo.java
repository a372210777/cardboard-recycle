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
* @author author
* @date 2022-06-06
**/
@Entity
@Data
@Table(name="wms_base_supplier_contact_info")
public class WmsBaseSupplierContactInfo extends BaseEntity implements Serializable {

    @Id
    @Column(name = "pk_supplier_contact")
    @ApiModelProperty(value = "主键")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkSupplierContact;

    @ApiModelProperty(value = "supplier")
    @JoinColumn(name = "fk_supplier")
    @ManyToOne
    private WmsBaseSupplierInfo supplier;

    @Column(name = "station_name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "站点名称")
    private String stationName;

    @Column(name = "province_and_city")
    @ApiModelProperty(value = "省市区")
    private String provinceAndCity;

    @Column(name = "address")
    @ApiModelProperty(value = "地址")
    private String address;

    @Column(name = "contact_name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "联系人")
    private String contactName;

    @Column(name = "contact_phone")
    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @Column(name = "memo")
    @ApiModelProperty(value = "备注")
    private String memo;

    @Column(name = "is_valid")
    @ApiModelProperty(value = "有效标识（1有效，0无效）")
    private Integer isValid = 1;

    public void copy(WmsBaseSupplierContactInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}