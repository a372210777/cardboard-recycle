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
import java.sql.Timestamp;
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
@Table(name="biz_quality_check_cert")
public class QualityCheckCert implements Serializable {

    @Id
    @Column(name = "`id`")
    @ApiModelProperty(value = "质检单号")
    private String id;

    @Column(name = "`stock_out_order_item_id`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "所属出库单明细")
    private Integer stockOutOrderItemId;

    @Column(name = "`buyer_id`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "采购商ID")
    private Integer buyerId;

    @Column(name = "`gross_weight`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "毛重")
    private Double grossWeight;

    @Column(name = "`tare_weight`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "皮重")
    private Double tareWeight;

    @Column(name = "`net_weight`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "净重")
    private Double netWeight;

    @Column(name = "`deduct_weight`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "扣重")
    private Double deductWeight;

    @Column(name = "`weighting_time`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "称重时间")
    private Timestamp weightingTime;

    @Column(name = "`license_plate`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "车牌号")
    private String licensePlate;

    @Column(name = "`total_packs`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "总件数")
    private Integer totalPacks;

    @Column(name = "`spot_check_packs`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "抽检数")
    private Integer spotCheckPacks;

    @Column(name = "`water_percent`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "水分百分比")
    private BigDecimal waterPercent;

    @Column(name = "`impurity_percent`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "杂物百分比")
    private BigDecimal impurityPercent;

    @Column(name = "`incidental_paper_percent`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "杂纸百分比")
    private BigDecimal incidentalPaperPercent;

    @Column(name = "`total_deduct_percent`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "综合折率")
    private BigDecimal totalDeductPercent;

    @Column(name = "`weighing_attachment`")
    @ApiModelProperty(value = "称重单附件")
    private String weighingAttachment;

    @Column(name = "`attachment`")
    @ApiModelProperty(value = "质检单附件")
    private String attachment;

    @Column(name = "`remark`")
    @ApiModelProperty(value = "备注")
    private String remark;

    public void copy(QualityCheckCert source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
