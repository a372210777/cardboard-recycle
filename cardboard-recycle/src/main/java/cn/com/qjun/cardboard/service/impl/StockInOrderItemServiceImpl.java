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

import cn.com.qjun.cardboard.domain.StockInOrderItem;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import cn.com.qjun.cardboard.repository.StockInOrderItemRepository;
import cn.com.qjun.cardboard.service.StockInOrderItemService;
import cn.com.qjun.cardboard.service.dto.StockInOrderItemDto;
import cn.com.qjun.cardboard.service.dto.StockInOrderItemQueryCriteria;
import cn.com.qjun.cardboard.service.mapstruct.StockInOrderItemMapper;
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
public class StockInOrderItemServiceImpl implements StockInOrderItemService {

    private final StockInOrderItemRepository stockInOrderItemRepository;
    private final StockInOrderItemMapper stockInOrderItemMapper;

    @Override
    public Map<String,Object> queryAll(StockInOrderItemQueryCriteria criteria, Pageable pageable){
        Page<StockInOrderItem> page = stockInOrderItemRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(stockInOrderItemMapper::toDto));
    }

    @Override
    public List<StockInOrderItemDto> queryAll(StockInOrderItemQueryCriteria criteria){
        return stockInOrderItemMapper.toDto(stockInOrderItemRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public StockInOrderItemDto findById(Integer id) {
        StockInOrderItem stockInOrderItem = stockInOrderItemRepository.findById(id).orElseGet(StockInOrderItem::new);
        ValidationUtil.isNull(stockInOrderItem.getId(),"StockInOrderItem","id",id);
        return stockInOrderItemMapper.toDto(stockInOrderItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StockInOrderItemDto create(StockInOrderItem resources) {
        return stockInOrderItemMapper.toDto(stockInOrderItemRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StockInOrderItem resources) {
        StockInOrderItem stockInOrderItem = stockInOrderItemRepository.findById(resources.getId()).orElseGet(StockInOrderItem::new);
        ValidationUtil.isNull( stockInOrderItem.getId(),"StockInOrderItem","id",resources.getId());
        stockInOrderItem.copy(resources);
        stockInOrderItemRepository.save(stockInOrderItem);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            stockInOrderItemRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<StockInOrderItemDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StockInOrderItemDto stockInOrderItem : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("入库单ID", stockInOrderItem.getStockInOrderId());
            map.put("物料ID", stockInOrderItem.getMaterialId());
            map.put("入库数量", stockInOrderItem.getQuantity());
            map.put("数量单位", stockInOrderItem.getUnit());
            map.put("备注", stockInOrderItem.getRemark());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}