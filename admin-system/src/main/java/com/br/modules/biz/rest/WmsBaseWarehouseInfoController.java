package com.br.modules.biz.rest;

import com.br.annotation.Log;
import com.br.modules.biz.domain.WmsBaseWarehouseInfo;
import com.br.modules.biz.service.WmsBaseWarehouseInfoService;
import com.br.modules.biz.service.dto.WmsBaseWarehouseInfoQueryCriteria;
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
* @author lin
* @date 2022-06-06
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "仓库管理")
@RequestMapping("/api/wmsBaseWarehouseInfo")
public class WmsBaseWarehouseInfoController {

    private final WmsBaseWarehouseInfoService wmsBaseWarehouseInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('wmsBaseWarehouseInfo:list')")
    public void download(HttpServletResponse response, WmsBaseWarehouseInfoQueryCriteria criteria) throws IOException {
        wmsBaseWarehouseInfoService.download(wmsBaseWarehouseInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询仓库")
    @ApiOperation("查询仓库")
    @PreAuthorize("@el.check('wmsBaseWarehouseInfo:list')")
    public ResponseEntity<Object> query(WmsBaseWarehouseInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wmsBaseWarehouseInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增仓库")
    @ApiOperation("新增仓库")
    @PreAuthorize("@el.check('wmsBaseWarehouseInfo:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WmsBaseWarehouseInfo resources){
        return new ResponseEntity<>(wmsBaseWarehouseInfoService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改仓库")
    @ApiOperation("修改仓库")
    @PreAuthorize("@el.check('wmsBaseWarehouseInfo:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WmsBaseWarehouseInfo resources){
        wmsBaseWarehouseInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除仓库")
    @ApiOperation("删除仓库")
    @PreAuthorize("@el.check('wmsBaseWarehouseInfo:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        wmsBaseWarehouseInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}