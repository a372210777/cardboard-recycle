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
import cn.com.qjun.cardboard.domain.QualityCheckCert;
import cn.com.qjun.cardboard.service.QualityCheckCertService;
import cn.com.qjun.cardboard.service.dto.QualityCheckCertQueryCriteria;
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
@Api(tags = "质检单管理")
@RequestMapping("/api/qualityCheckCert")
public class QualityCheckCertController {

    private final QualityCheckCertService qualityCheckCertService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('qualityCheckCert:list')")
    public void exportQualityCheckCert(HttpServletResponse response, QualityCheckCertQueryCriteria criteria) throws IOException {
        qualityCheckCertService.download(qualityCheckCertService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询质检单")
    @ApiOperation("查询质检单")
    @PreAuthorize("@el.check('qualityCheckCert:list')")
    public ResponseEntity<Object> queryQualityCheckCert(QualityCheckCertQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(qualityCheckCertService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增质检单")
    @ApiOperation("新增质检单")
    @PreAuthorize("@el.check('qualityCheckCert:add')")
    public ResponseEntity<Object> createQualityCheckCert(@Validated @RequestBody QualityCheckCert resources){
        return new ResponseEntity<>(qualityCheckCertService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改质检单")
    @ApiOperation("修改质检单")
    @PreAuthorize("@el.check('qualityCheckCert:edit')")
    public ResponseEntity<Object> updateQualityCheckCert(@Validated @RequestBody QualityCheckCert resources){
        qualityCheckCertService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除质检单")
    @ApiOperation("删除质检单")
    @PreAuthorize("@el.check('qualityCheckCert:del')")
    public ResponseEntity<Object> deleteQualityCheckCert(@RequestBody String[] ids) {
        qualityCheckCertService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}