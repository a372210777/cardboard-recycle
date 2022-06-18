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
import cn.com.qjun.cardboard.domain.StockOutOrder;
import cn.com.qjun.cardboard.service.StockOutOrderService;
import cn.com.qjun.cardboard.service.dto.StockOutOrderQueryCriteria;
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
@Api(tags = "出库单管理")
@RequestMapping("/api/stockOutOrder")
public class StockOutOrderController {

    private final StockOutOrderService stockOutOrderService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('stockOutOrder:list')")
    public void exportStockOutOrder(HttpServletResponse response, StockOutOrderQueryCriteria criteria) throws IOException {
        stockOutOrderService.download(stockOutOrderService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询出库单")
    @ApiOperation("查询出库单")
    @PreAuthorize("@el.check('stockOutOrder:list')")
    public ResponseEntity<Object> queryStockOutOrder(StockOutOrderQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(stockOutOrderService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增出库单")
    @ApiOperation("新增出库单")
    @PreAuthorize("@el.check('stockOutOrder:add')")
    public ResponseEntity<Object> createStockOutOrder(@Validated @RequestBody StockOutOrder resources){
        return new ResponseEntity<>(stockOutOrderService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改出库单")
    @ApiOperation("修改出库单")
    @PreAuthorize("@el.check('stockOutOrder:edit')")
    public ResponseEntity<Object> updateStockOutOrder(@Validated @RequestBody StockOutOrder resources){
        stockOutOrderService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除出库单")
    @ApiOperation("删除出库单")
    @PreAuthorize("@el.check('stockOutOrder:del')")
    public ResponseEntity<Object> deleteStockOutOrder(@RequestBody String[] ids) {
        stockOutOrderService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}