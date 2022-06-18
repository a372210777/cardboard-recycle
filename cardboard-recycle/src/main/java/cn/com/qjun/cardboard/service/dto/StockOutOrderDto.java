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
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author RenQiang
* @date 2022-06-18
**/
@Data
public class StockOutOrderDto implements Serializable {

    /** 出库单号 */
    private String id;

    /** 采购商ID */
    private Integer buyerId;

    /** 承运商ID */
    private Integer carrierId;

    /** 仓库ID */
    private Integer warehouseId;

    /** 制单人 */
    private String createBy;

    /** 制单时间 */
    private Timestamp createTime;

    /** 出库时间 */
    private Timestamp stockOutTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    private Timestamp updateTime;

    /** 是否已删除 */
    private Integer deleted;
}