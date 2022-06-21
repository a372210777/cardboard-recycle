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

import cn.com.qjun.cardboard.utils.SerialNumberGenerator;
import me.zhengjie.annotation.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

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

    private final SerialNumberGenerator serialNumberGenerator;

    @GetMapping("/genId")
    @Log("生成托运单号")
    @ApiOperation("生成托运单号")
    @PreAuthorize("@el.check('stockOutOrder:add')")
    public ResponseEntity<String> generateOrderId() {
        return new ResponseEntity<>(serialNumberGenerator.generateWaybillId(), HttpStatus.OK);
    }
}