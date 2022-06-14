package com.br.modules.biz.rest;

import com.br.annotation.Log;
import com.br.modules.biz.domain.WmsBaseWarehousePositionInfo;
import com.br.modules.biz.service.WmsBaseWarehousePositionInfoService;
import com.br.modules.biz.service.dto.WmsBaseWarehousePositionInfoQueryCriteria;
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
* @date 2022-06-08
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "库位信息管理")
@RequestMapping("/api/wmsBaseWarehousePositionInfo")
public class WmsBaseWarehousePositionInfoController {

    private final WmsBaseWarehousePositionInfoService wmsBaseWarehousePositionInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('wmsBaseWarehousePositionInfo:list')")
    public void download(HttpServletResponse response, WmsBaseWarehousePositionInfoQueryCriteria criteria) throws IOException {
        wmsBaseWarehousePositionInfoService.download(wmsBaseWarehousePositionInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询库位信息")
    @ApiOperation("查询库位信息")
    @PreAuthorize("@el.check('wmsBaseWarehousePositionInfo:list')")
    public ResponseEntity<Object> query(WmsBaseWarehousePositionInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wmsBaseWarehousePositionInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增库位信息")
    @ApiOperation("新增库位信息")
    @PreAuthorize("@el.check('wmsBaseWarehousePositionInfo:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WmsBaseWarehousePositionInfo resources){
        return new ResponseEntity<>(wmsBaseWarehousePositionInfoService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改库位信息")
    @ApiOperation("修改库位信息")
    @PreAuthorize("@el.check('wmsBaseWarehousePositionInfo:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WmsBaseWarehousePositionInfo resources){
        wmsBaseWarehousePositionInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除库位信息")
    @ApiOperation("删除库位信息")
    @PreAuthorize("@el.check('wmsBaseWarehousePositionInfo:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        wmsBaseWarehousePositionInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}