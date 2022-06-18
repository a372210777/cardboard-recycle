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
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author RenQiang
* @date 2022-06-18
**/
@Data
public class StockInOrderItemDto implements Serializable {

    /** 主键ID */
    private Integer id;

    /** 入库单ID */
    private String stockInOrderId;

    /** 物料ID */
    private Integer materialId;

    /** 入库数量 */
    private Integer quantity;

    /** 数量单位 */
    private String unit;

    /** 备注 */
    private String remark;
}