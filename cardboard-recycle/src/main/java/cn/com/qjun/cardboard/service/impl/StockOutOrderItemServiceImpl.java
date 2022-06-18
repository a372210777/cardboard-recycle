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

import cn.com.qjun.cardboard.domain.StockOutOrderItem;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import cn.com.qjun.cardboard.repository.StockOutOrderItemRepository;
import cn.com.qjun.cardboard.service.StockOutOrderItemService;
import cn.com.qjun.cardboard.service.dto.StockOutOrderItemDto;
import cn.com.qjun.cardboard.service.dto.StockOutOrderItemQueryCriteria;
import cn.com.qjun.cardboard.service.mapstruct.StockOutOrderItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class StockOutOrderItemServiceImpl implements StockOutOrderItemService {

    private final StockOutOrderItemRepository stockOutOrderItemRepository;
    private final StockOutOrderItemMapper stockOutOrderItemMapper;

    @Override
    public Map<String,Object> queryAll(StockOutOrderItemQueryCriteria criteria, Pageable pageable){
        Page<StockOutOrderItem> page = stockOutOrderItemRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(stockOutOrderItemMapper::toDto));
    }

    @Override
    public List<StockOutOrderItemDto> queryAll(StockOutOrderItemQueryCriteria criteria){
        return stockOutOrderItemMapper.toDto(stockOutOrderItemRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public StockOutOrderItemDto findById(Integer id) {
        StockOutOrderItem stockOutOrderItem = stockOutOrderItemRepository.findById(id).orElseGet(StockOutOrderItem::new);
        ValidationUtil.isNull(stockOutOrderItem.getId(),"StockOutOrderItem","id",id);
        return stockOutOrderItemMapper.toDto(stockOutOrderItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StockOutOrderItemDto create(StockOutOrderItem resources) {
        return stockOutOrderItemMapper.toDto(stockOutOrderItemRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StockOutOrderItem resources) {
        StockOutOrderItem stockOutOrderItem = stockOutOrderItemRepository.findById(resources.getId()).orElseGet(StockOutOrderItem::new);
        ValidationUtil.isNull( stockOutOrderItem.getId(),"StockOutOrderItem","id",resources.getId());
        stockOutOrderItem.copy(resources);
        stockOutOrderItemRepository.save(stockOutOrderItem);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            stockOutOrderItemRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<StockOutOrderItemDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StockOutOrderItemDto stockOutOrderItem : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("出库单ID", stockOutOrderItem.getStockOutOrderId());
            map.put("物料ID", stockOutOrderItem.getMaterialId());
            map.put("出库数量", stockOutOrderItem.getQuantity());
            map.put("数量单位", stockOutOrderItem.getUnit());
            map.put("单价", stockOutOrderItem.getUnitPrice());
            map.put("备注", stockOutOrderItem.getRemark());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}