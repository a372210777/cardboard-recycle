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
package cn.com.qjun.cardboard.service.impl;

import cn.com.qjun.cardboard.domain.Waybill;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import cn.com.qjun.cardboard.repository.WaybillRepository;
import cn.com.qjun.cardboard.service.WaybillService;
import cn.com.qjun.cardboard.service.dto.WaybillDto;
import cn.com.qjun.cardboard.service.dto.WaybillQueryCriteria;
import cn.com.qjun.cardboard.service.mapstruct.WaybillMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author RenQiang
* @date 2022-06-18
**/
@Service
@RequiredArgsConstructor
public class WaybillServiceImpl implements WaybillService {

    private final WaybillRepository waybillRepository;
    private final WaybillMapper waybillMapper;

    @Override
    public Map<String,Object> queryAll(WaybillQueryCriteria criteria, Pageable pageable){
        Page<Waybill> page = waybillRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(waybillMapper::toDto));
    }

    @Override
    public List<WaybillDto> queryAll(WaybillQueryCriteria criteria){
        return waybillMapper.toDto(waybillRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public WaybillDto findById(String id) {
        Waybill waybill = waybillRepository.findById(id).orElseGet(Waybill::new);
        ValidationUtil.isNull(waybill.getId(),"Waybill","id",id);
        return waybillMapper.toDto(waybill);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WaybillDto create(Waybill resources) {
        resources.setId(IdUtil.simpleUUID()); 
        return waybillMapper.toDto(waybillRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Waybill resources) {
        Waybill waybill = waybillRepository.findById(resources.getId()).orElseGet(Waybill::new);
        ValidationUtil.isNull( waybill.getId(),"Waybill","id",resources.getId());
        waybill.copy(resources);
        waybillRepository.save(waybill);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            waybillRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<WaybillDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WaybillDto waybill : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("出库单ID", waybill.getStockOutOrderId());
            map.put("采购商ID", waybill.getBuyerId());
            map.put("承运商ID", waybill.getCarrierId());
            map.put("托运车数", waybill.getConsignmentVehicles());
            map.put("每车价格", waybill.getPricePerVehicle());
            map.put("备注", waybill.getRemark());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}