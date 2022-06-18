/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package cn.com.qjun.cardboard.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author RenQiang
* @date 2022-06-18
**/
@Entity
@Data
@Table(name="biz_stock_out_order_item")
public class StockOutOrderItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "自增主键ID")
    private Integer id;

    @Column(name = "`stock_out_order_id`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "出库单ID")
    private String stockOutOrderId;

    @Column(name = "`material_id`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "物料ID")
    private Integer materialId;

    @Column(name = "`quantity`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "出库数量")
    private Integer quantity;

    @Column(name = "`unit`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "数量单位")
    private String unit;

    @Column(name = "`unit_price`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @Column(name = "`remark`")
    @ApiModelProperty(value = "备注")
    private String remark;

    public void copy(StockOutOrderItem source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
