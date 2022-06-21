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

import cn.com.qjun.cardboard.common.SystemConstant;
import cn.com.qjun.cardboard.domain.BasicMaterial;
import cn.com.qjun.cardboard.domain.StockInOrder;
import cn.com.qjun.cardboard.domain.StockInOrderItem;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import cn.com.qjun.cardboard.repository.StockInOrderRepository;
import cn.com.qjun.cardboard.service.StockInOrderService;
import cn.com.qjun.cardboard.service.dto.StockInOrderDto;
import cn.com.qjun.cardboard.service.dto.StockInOrderQueryCriteria;
import cn.com.qjun.cardboard.service.mapstruct.StockInOrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author RenQiang
* @date 2022-06-18
**/
@Service
@RequiredArgsConstructor
public class StockInOrderServiceImpl implements StockInOrderService {

    private final StockInOrderRepository stockInOrderRepository;
    private final StockInOrderMapper stockInOrderMapper;

    @Override
    public Map<String,Object> queryAll(StockInOrderQueryCriteria criteria, Pageable pageable){
        Page<StockInOrder> page = stockInOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(stockInOrderMapper::toDto));
    }

    @Override
    public List<StockInOrderDto> queryAll(StockInOrderQueryCriteria criteria){
        return stockInOrderMapper.toDto(stockInOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public StockInOrderDto findById(String id) {
        StockInOrder stockInOrder = stockInOrderRepository.findById(id).orElseGet(StockInOrder::new);
        ValidationUtil.isNull(stockInOrder.getId(),"StockInOrder","id",id);
        return stockInOrderMapper.toDto(stockInOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StockInOrderDto create(StockInOrder resources) {
        resources.setDeleted(SystemConstant.DEL_FLAG_0);
        resources.getStockInOrderItems()
                .forEach(item -> item.setStockInOrder(resources));
        return stockInOrderMapper.toDto(stockInOrderRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StockInOrder resources) {
        StockInOrder stockInOrder = stockInOrderRepository.findById(resources.getId()).orElseGet(StockInOrder::new);
        ValidationUtil.isNull( stockInOrder.getId(),"StockInOrder","id",resources.getId());
        stockInOrder.copy(resources);
        stockInOrderRepository.save(stockInOrder);
    }

    @Override
    public void deleteAll(String[] ids) {
        List<StockInOrder> allById = stockInOrderRepository.findAllById(Arrays.asList(ids));
        for (StockInOrder stockInOrder : allById) {
            stockInOrder.setDeleted(SystemConstant.DEL_FLAG_1);
        }
        stockInOrderRepository.saveAll(allById);
    }

    @Override
    public void download(List<StockInOrderDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StockInOrderDto stockInOrder : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("入库时间", stockInOrder.getStockInTime());
            map.put("制单人", stockInOrder.getCreateBy());
            map.put("制单时间", stockInOrder.getCreateTime());
            map.put("更新人", stockInOrder.getUpdateBy());
            map.put("更新时间", stockInOrder.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}