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
package cn.com.qjun.cardboard.rest;

import me.zhengjie.annotation.Log;
import cn.com.qjun.cardboard.domain.StockOutOrderItem;
import cn.com.qjun.cardboard.service.StockOutOrderItemService;
import cn.com.qjun.cardboard.service.dto.StockOutOrderItemQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author RenQiang
* @date 2022-06-18
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "出库单明细管理")
@RequestMapping("/api/stockOutOrderItem")
public class StockOutOrderItemController {

    private final StockOutOrderItemService stockOutOrderItemService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('stockOutOrderItem:list')")
    public void exportStockOutOrderItem(HttpServletResponse response, StockOutOrderItemQueryCriteria criteria) throws IOException {
        stockOutOrderItemService.download(stockOutOrderItemService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询出库单明细")
    @ApiOperation("查询出库单明细")
    @PreAuthorize("@el.check('stockOutOrderItem:list')")
    public ResponseEntity<Object> queryStockOutOrderItem(StockOutOrderItemQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(stockOutOrderItemService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增出库单明细")
    @ApiOperation("新增出库单明细")
    @PreAuthorize("@el.check('stockOutOrderItem:add')")
    public ResponseEntity<Object> createStockOutOrderItem(@Validated @RequestBody StockOutOrderItem resources){
        return new ResponseEntity<>(stockOutOrderItemService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改出库单明细")
    @ApiOperation("修改出库单明细")
    @PreAuthorize("@el.check('stockOutOrderItem:edit')")
    public ResponseEntity<Object> updateStockOutOrderItem(@Validated @RequestBody StockOutOrderItem resources){
        stockOutOrderItemService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除出库单明细")
    @ApiOperation("删除出库单明细")
    @PreAuthorize("@el.check('stockOutOrderItem:del')")
    public ResponseEntity<Object> deleteStockOutOrderItem(@RequestBody Integer[] ids) {
        stockOutOrderItemService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}