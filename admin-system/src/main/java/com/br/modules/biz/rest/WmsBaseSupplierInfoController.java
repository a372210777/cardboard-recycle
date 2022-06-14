package com.br.modules.biz.rest;

import com.alibaba.excel.EasyExcel;
import com.br.annotation.Log;
import com.br.modules.biz.domain.WmsBaseSupplierInfo;
import com.br.modules.biz.excel.ExcelConsumer;
import com.br.modules.biz.excel.ExcelListener;
import com.br.modules.biz.excel.consumer.WmsBaseSupplierInfoConsumer;
import com.br.modules.biz.excel.excelBean.WmsBaseSupplierInfoEB;
import com.br.modules.biz.service.WmsBaseSupplierInfoService;
import com.br.modules.biz.service.dto.WmsBaseSupplierInfoQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* @author lin
* @date 2022-06-06
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "供应商管理")
@RequestMapping("/api/wmsBaseSupplierInfo")
public class WmsBaseSupplierInfoController {

    private final WmsBaseSupplierInfoService wmsBaseSupplierInfoService;
    private final WmsBaseSupplierInfoConsumer wmsBaseSupplierInfoConsumer;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('wmsBaseSupplierInfo:list')")
    public void download(HttpServletResponse response, WmsBaseSupplierInfoQueryCriteria criteria) throws IOException {
        wmsBaseSupplierInfoService.download(wmsBaseSupplierInfoService.queryAll(criteria), response);
    }

    @ApiOperation("模板导出")
    @GetMapping(value = "/templateDownload")
    @PreAuthorize("@el.check('wmsBaseSupplierInfo:list')")
    public void templateDownload(HttpServletResponse response, WmsBaseSupplierInfoQueryCriteria criteria) throws IOException {
        wmsBaseSupplierInfoService.templateDownload(response);
    }

    @ApiOperation("供应商信息导入")
    @PostMapping(value = "/upload")
    @PreAuthorize("@el.check('wmsBaseSupplierInfo:list')")
    public void upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), WmsBaseSupplierInfoEB.class, new ExcelListener(wmsBaseSupplierInfoConsumer)).sheet().doRead();
    }

    @GetMapping
    @Log("查询供应商")
    @ApiOperation("查询供应商")
    @PreAuthorize("@el.check('wmsBaseSupplierInfo:list')")
    public ResponseEntity<Object> query(WmsBaseSupplierInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wmsBaseSupplierInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增供应商")
    @ApiOperation("新增供应商")
    @PreAuthorize("@el.check('wmsBaseSupplierInfo:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WmsBaseSupplierInfo resources){
        return new ResponseEntity<>(wmsBaseSupplierInfoService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改供应商")
    @ApiOperation("修改供应商")
    @PreAuthorize("@el.check('wmsBaseSupplierInfo:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WmsBaseSupplierInfo resources){
        wmsBaseSupplierInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除供应商")
    @ApiOperation("删除供应商")
    @PreAuthorize("@el.check('wmsBaseSupplierInfo:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        wmsBaseSupplierInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}