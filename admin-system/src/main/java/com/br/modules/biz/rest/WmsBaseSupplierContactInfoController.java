package com.br.modules.biz.rest;

import com.br.annotation.Log;
import com.br.modules.biz.domain.WmsBaseSupplierContactInfo;
import com.br.modules.biz.service.WmsBaseSupplierContactInfoService;
import com.br.modules.biz.service.dto.WmsBaseSupplierContactInfoQueryCriteria;
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
@Api(tags = "供应商联系人管理")
@RequestMapping("/api/wmsBaseSupplierContactInfo")
public class WmsBaseSupplierContactInfoController {

    private final WmsBaseSupplierContactInfoService wmsBaseSupplierContactInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('wmsBaseSupplierContactInfo:list')")
    public void download(HttpServletResponse response, WmsBaseSupplierContactInfoQueryCriteria criteria) throws IOException {
        wmsBaseSupplierContactInfoService.download(wmsBaseSupplierContactInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询供应商联系人")
    @ApiOperation("查询供应商联系人")
    @PreAuthorize("@el.check('wmsBaseSupplierContactInfo:list')")
    public ResponseEntity<Object> query(WmsBaseSupplierContactInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wmsBaseSupplierContactInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增供应商联系人")
    @ApiOperation("新增供应商联系人")
    @PreAuthorize("@el.check('wmsBaseSupplierContactInfo:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WmsBaseSupplierContactInfo resources){
        return new ResponseEntity<>(wmsBaseSupplierContactInfoService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改供应商联系人")
    @ApiOperation("修改供应商联系人")
    @PreAuthorize("@el.check('wmsBaseSupplierContactInfo:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WmsBaseSupplierContactInfo resources){
        wmsBaseSupplierContactInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除供应商联系人")
    @ApiOperation("删除供应商联系人")
    @PreAuthorize("@el.check('wmsBaseSupplierContactInfo:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        wmsBaseSupplierContactInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}