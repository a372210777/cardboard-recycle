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
import cn.com.qjun.cardboard.domain.Waybill;
import cn.com.qjun.cardboard.service.WaybillService;
import cn.com.qjun.cardboard.service.dto.WaybillQueryCriteria;
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
@Api(tags = "托运单管理")
@RequestMapping("/api/waybill")
public class WaybillController {

    private final WaybillService waybillService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('waybill:list')")
    public void exportWaybill(HttpServletResponse response, WaybillQueryCriteria criteria) throws IOException {
        waybillService.download(waybillService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询托运单")
    @ApiOperation("查询托运单")
    @PreAuthorize("@el.check('waybill:list')")
    public ResponseEntity<Object> queryWaybill(WaybillQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(waybillService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增托运单")
    @ApiOperation("新增托运单")
    @PreAuthorize("@el.check('waybill:add')")
    public ResponseEntity<Object> createWaybill(@Validated @RequestBody Waybill resources){
        return new ResponseEntity<>(waybillService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改托运单")
    @ApiOperation("修改托运单")
    @PreAuthorize("@el.check('waybill:edit')")
    public ResponseEntity<Object> updateWaybill(@Validated @RequestBody Waybill resources){
        waybillService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除托运单")
    @ApiOperation("删除托运单")
    @PreAuthorize("@el.check('waybill:del')")
    public ResponseEntity<Object> deleteWaybill(@RequestBody String[] ids) {
        waybillService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}