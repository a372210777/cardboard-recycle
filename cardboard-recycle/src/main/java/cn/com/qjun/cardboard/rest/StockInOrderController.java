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
import cn.com.qjun.cardboard.domain.StockInOrder;
import cn.com.qjun.cardboard.service.StockInOrderService;
import cn.com.qjun.cardboard.service.dto.StockInOrderQueryCriteria;
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
@Api(tags = "入库单管理")
@RequestMapping("/api/stockInOrder")
public class StockInOrderController {

    private final StockInOrderService stockInOrderService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('stockInOrder:list')")
    public void exportStockInOrder(HttpServletResponse response, StockInOrderQueryCriteria criteria) throws IOException {
        stockInOrderService.download(stockInOrderService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询入库单")
    @ApiOperation("查询入库单")
    @PreAuthorize("@el.check('stockInOrder:list')")
    public ResponseEntity<Object> queryStockInOrder(StockInOrderQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(stockInOrderService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增入库单")
    @ApiOperation("新增入库单")
    @PreAuthorize("@el.check('stockInOrder:add')")
    public ResponseEntity<Object> createStockInOrder(@Validated @RequestBody StockInOrder resources){
        return new ResponseEntity<>(stockInOrderService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改入库单")
    @ApiOperation("修改入库单")
    @PreAuthorize("@el.check('stockInOrder:edit')")
    public ResponseEntity<Object> updateStockInOrder(@Validated @RequestBody StockInOrder resources){
        stockInOrderService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除入库单")
    @ApiOperation("删除入库单")
    @PreAuthorize("@el.check('stockInOrder:del')")
    public ResponseEntity<Object> deleteStockInOrder(@RequestBody String[] ids) {
        stockInOrderService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}