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
package cn.com.qjun.cardboard.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;

/**
 * @author RenQiang
 * @website https://el-admin.vip
 * @description /
 * @date 2022-06-18
 **/
@Data
public class WaybillDto implements Serializable {

    /**
     * 托运单号
     */
    private String id;

    /**
     * 出库单ID
     */
    private String stockOutOrderId;

    /**
     * 采购商
     */
    private BasicBuyerDto buyer;

    /**
     * 承运商
     */
    private BasicCarrierDto carrier;

    /**
     * 托运车数
     */
    private Integer consignmentVehicles;

    /**
     * 每车价格
     */
    private BigDecimal pricePerVehicle;

    /**
     * 备注
     */
    private String remark;
}