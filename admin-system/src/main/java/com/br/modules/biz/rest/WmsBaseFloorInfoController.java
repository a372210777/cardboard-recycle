package com.br.modules.biz.rest;

import com.br.annotation.Log;
import com.br.common.CodeGenerator;
import com.br.modules.biz.domain.WmsBaseFloorInfo;
import com.br.modules.biz.service.WmsBaseFloorInfoService;
import com.br.modules.biz.service.dto.WmsBaseFloorInfoQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
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
@Api(tags = "楼层信息管理")
@RequestMapping("/api/wmsBaseFloorInfo")
public class WmsBaseFloorInfoController {

    private final WmsBaseFloorInfoService wmsBaseFloorInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('wmsBaseFloorInfo:list')")
    public void download(HttpServletResponse response, WmsBaseFloorInfoQueryCriteria criteria) throws IOException {
        wmsBaseFloorInfoService.download(wmsBaseFloorInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询楼层信息")
    @ApiOperation("查询楼层信息")
    @PreAuthorize("@el.check('wmsBaseFloorInfo:list')")
    public ResponseEntity<Object> query(WmsBaseFloorInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wmsBaseFloorInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增楼层信息")
    @ApiOperation("新增楼层信息")
    @PreAuthorize("@el.check('wmsBaseFloorInfo:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WmsBaseFloorInfo resources){
        Assert.notNull(resources.getWarehouseInfo(), "请选择所属仓库");
        resources.setCode(CodeGenerator.genFloorCode(resources.getWarehouseInfo().getCode(), resources.getFloorNo()));
        return new ResponseEntity<>(wmsBaseFloorInfoService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改楼层信息")
    @ApiOperation("修改楼层信息")
    @PreAuthorize("@el.check('wmsBaseFloorInfo:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WmsBaseFloorInfo resources){
        Assert.notNull(resources.getWarehouseInfo(), "请选择所属仓库");
        //resources.setCode(CodeGenerator.genFloorCode(resources.getWarehouseInfo().getCode(), resources.getFloorNo()));
        wmsBaseFloorInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除楼层信息")
    @ApiOperation("删除楼层信息")
    @PreAuthorize("@el.check('wmsBaseFloorInfo:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        wmsBaseFloorInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}