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
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author RenQiang
* @date 2022-06-18
**/
@Entity
@Data
@Table(name="biz_stock_out_order")
public class StockOutOrder implements Serializable {

    @Id
    @Column(name = "`id`")
    @ApiModelProperty(value = "出库单号")
    private String id;

    @Column(name = "`buyer_id`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "采购商ID")
    private Integer buyerId;

    @Column(name = "`carrier_id`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "承运商ID")
    private Integer carrierId;

    @Column(name = "`warehouse_id`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "仓库ID")
    private Integer warehouseId;

    @Column(name = "`create_by`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "制单人")
    private String createBy;

    @Column(name = "`create_time`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "制单时间")
    private Timestamp createTime;

    @Column(name = "`stock_out_time`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "出库时间")
    private Timestamp stockOutTime;

    @Column(name = "`update_by`")
    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @Column(name = "`update_time`")
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    @Column(name = "`deleted`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "是否已删除")
    private Integer deleted;

    public void copy(StockOutOrder source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
