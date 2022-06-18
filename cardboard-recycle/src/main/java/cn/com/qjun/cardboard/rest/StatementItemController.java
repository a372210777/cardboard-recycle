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
import cn.com.qjun.cardboard.domain.StatementItem;
import cn.com.qjun.cardboard.service.StatementItemService;
import cn.com.qjun.cardboard.service.dto.StatementItemQueryCriteria;
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
@Api(tags = "结算单明细管理")
@RequestMapping("/api/statementItem")
public class StatementItemController {

    private final StatementItemService statementItemService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('statementItem:list')")
    public void exportStatementItem(HttpServletResponse response, StatementItemQueryCriteria criteria) throws IOException {
        statementItemService.download(statementItemService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询结算单明细")
    @ApiOperation("查询结算单明细")
    @PreAuthorize("@el.check('statementItem:list')")
    public ResponseEntity<Object> queryStatementItem(StatementItemQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(statementItemService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增结算单明细")
    @ApiOperation("新增结算单明细")
    @PreAuthorize("@el.check('statementItem:add')")
    public ResponseEntity<Object> createStatementItem(@Validated @RequestBody StatementItem resources){
        return new ResponseEntity<>(statementItemService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改结算单明细")
    @ApiOperation("修改结算单明细")
    @PreAuthorize("@el.check('statementItem:edit')")
    public ResponseEntity<Object> updateStatementItem(@Validated @RequestBody StatementItem resources){
        statementItemService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除结算单明细")
    @ApiOperation("删除结算单明细")
    @PreAuthorize("@el.check('statementItem:del')")
    public ResponseEntity<Object> deleteStatementItem(@RequestBody Integer[] ids) {
        statementItemService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}