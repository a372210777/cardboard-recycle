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
@Table(name="biz_statement")
public class Statement implements Serializable {

    @Id
    @Column(name = "`id`")
    @ApiModelProperty(value = "对账单号")
    private String id;

    @Column(name = "`year_`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "对账年份")
    private Integer year;

    @Column(name = "`month_`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "对账月份")
    private Integer month;

    @Column(name = "`create_by`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "对账人")
    private String createBy;

    @Column(name = "`statement_time`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "对账时间")
    private Timestamp statementTime;

    @Column(name = "`create_time`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

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

    public void copy(Statement source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
