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
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author RenQiang
* @date 2022-06-17
**/
@Entity
@Data
@Table(name="basic_vehicle")
public class BasicVehicle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "自增主键ID")
    private Integer id;

    @Column(name = "`license_plate`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "车牌号")
    private String licensePlate;

    @Column(name = "`type`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "车辆类型")
    private String type;

    @Column(name = "`driver_name`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "司机姓名")
    private String driverName;

    @Column(name = "`driver_phone`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "司机电话")
    private String driverPhone;

    @Column(name = "`load_`")
    @ApiModelProperty(value = "载重（吨）")
    private Integer load;

    @Column(name = "`volume`")
    @ApiModelProperty(value = "体积")
    private String volume;

    @Column(name = "`status_`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "车辆状态")
    private String status;

    @Column(name = "`deleted`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "是否已删除")
    private Integer deleted;

    @Column(name = "`create_by`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "创建人")
    private String createBy;

    @Column(name = "`update_by`")
    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @Column(name = "`create_time`",nullable = false)
    @NotNull
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "`update_time`")
    @UpdateTimestamp
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    public void copy(BasicVehicle source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
