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

import cn.com.qjun.cardboard.domain.StockOutOrder;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import cn.com.qjun.cardboard.repository.StockOutOrderRepository;
import cn.com.qjun.cardboard.service.StockOutOrderService;
import cn.com.qjun.cardboard.service.dto.StockOutOrderDto;
import cn.com.qjun.cardboard.service.dto.StockOutOrderQueryCriteria;
import cn.com.qjun.cardboard.service.mapstruct.StockOutOrderMapper;
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
public class StockOutOrderServiceImpl implements StockOutOrderService {

    private final StockOutOrderRepository stockOutOrderRepository;
    private final StockOutOrderMapper stockOutOrderMapper;

    @Override
    public Map<String,Object> queryAll(StockOutOrderQueryCriteria criteria, Pageable pageable){
        Page<StockOutOrder> page = stockOutOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(stockOutOrderMapper::toDto));
    }

    @Override
    public List<StockOutOrderDto> queryAll(StockOutOrderQueryCriteria criteria){
        return stockOutOrderMapper.toDto(stockOutOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public StockOutOrderDto findById(String id) {
        StockOutOrder stockOutOrder = stockOutOrderRepository.findById(id).orElseGet(StockOutOrder::new);
        ValidationUtil.isNull(stockOutOrder.getId(),"StockOutOrder","id",id);
        return stockOutOrderMapper.toDto(stockOutOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StockOutOrderDto create(StockOutOrder resources) {
        resources.setId(IdUtil.simpleUUID()); 
        return stockOutOrderMapper.toDto(stockOutOrderRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StockOutOrder resources) {
        StockOutOrder stockOutOrder = stockOutOrderRepository.findById(resources.getId()).orElseGet(StockOutOrder::new);
        ValidationUtil.isNull( stockOutOrder.getId(),"StockOutOrder","id",resources.getId());
        stockOutOrder.copy(resources);
        stockOutOrderRepository.save(stockOutOrder);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            stockOutOrderRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<StockOutOrderDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StockOutOrderDto stockOutOrder : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("采购商ID", stockOutOrder.getBuyerId());
            map.put("承运商ID", stockOutOrder.getCarrierId());
            map.put("仓库ID", stockOutOrder.getWarehouseId());
            map.put("制单人", stockOutOrder.getCreateBy());
            map.put("制单时间", stockOutOrder.getCreateTime());
            map.put("出库时间", stockOutOrder.getStockOutTime());
            map.put("更新人", stockOutOrder.getUpdateBy());
            map.put("更新时间", stockOutOrder.getUpdateTime());
            map.put("是否已删除", stockOutOrder.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}