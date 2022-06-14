package com.br.modules.biz.rest;

import com.br.annotation.Log;
import com.br.common.CodeGenerator;
import com.br.modules.biz.domain.WmsBaseWarehouseAreaInfo;
import com.br.modules.biz.service.WmsBaseWarehouseAreaInfoService;
import com.br.modules.biz.service.dto.WmsBaseWarehouseAreaInfoQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
* @author lin
* @date 2022-06-08
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "库区信息管理")
@RequestMapping("/api/wmsBaseWarehouseAreaInfo")
public class WmsBaseWarehouseAreaInfoController {

    private final WmsBaseWarehouseAreaInfoService wmsBaseWarehouseAreaInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('wmsBaseWarehouseAreaInfo:list')")
    public void download(HttpServletResponse response, WmsBaseWarehouseAreaInfoQueryCriteria criteria) throws IOException {
        wmsBaseWarehouseAreaInfoService.download(wmsBaseWarehouseAreaInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询库区信息")
    @ApiOperation("查询库区信息")
    @PreAuthorize("@el.check('wmsBaseWarehouseAreaInfo:list')")
    public ResponseEntity<Object> query(WmsBaseWarehouseAreaInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wmsBaseWarehouseAreaInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增库区信息")
    @ApiOperation("新增库区信息")
    @PreAuthorize("@el.check('wmsBaseWarehouseAreaInfo:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WmsBaseWarehouseAreaInfo resources){
        List<WmsBaseWarehouseAreaInfo> list = wmsBaseWarehouseAreaInfoService.findByFloor(resources.getFloorInfo().getPkFloor());
        resources.setCode(CodeGenerator.genAreaCode(resources.getFloorInfo().getCode(), list == null? 1:list.size()));
        return new ResponseEntity<>(wmsBaseWarehouseAreaInfoService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改库区信息")
    @ApiOperation("修改库区信息")
    @PreAuthorize("@el.check('wmsBaseWarehouseAreaInfo:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WmsBaseWarehouseAreaInfo resources){
        wmsBaseWarehouseAreaInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除库区信息")
    @ApiOperation("删除库区信息")
    @PreAuthorize("@el.check('wmsBaseWarehouseAreaInfo:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        wmsBaseWarehouseAreaInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}