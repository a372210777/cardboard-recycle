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

import cn.com.qjun.cardboard.domain.StatementItem;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import cn.com.qjun.cardboard.repository.StatementItemRepository;
import cn.com.qjun.cardboard.service.StatementItemService;
import cn.com.qjun.cardboard.service.dto.StatementItemDto;
import cn.com.qjun.cardboard.service.dto.StatementItemQueryCriteria;
import cn.com.qjun.cardboard.service.mapstruct.StatementItemMapper;
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
public class StatementItemServiceImpl implements StatementItemService {

    private final StatementItemRepository statementItemRepository;
    private final StatementItemMapper statementItemMapper;

    @Override
    public Map<String,Object> queryAll(StatementItemQueryCriteria criteria, Pageable pageable){
        Page<StatementItem> page = statementItemRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(statementItemMapper::toDto));
    }

    @Override
    public List<StatementItemDto> queryAll(StatementItemQueryCriteria criteria){
        return statementItemMapper.toDto(statementItemRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public StatementItemDto findById(Integer id) {
        StatementItem statementItem = statementItemRepository.findById(id).orElseGet(StatementItem::new);
        ValidationUtil.isNull(statementItem.getId(),"StatementItem","id",id);
        return statementItemMapper.toDto(statementItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StatementItemDto create(StatementItem resources) {
        return statementItemMapper.toDto(statementItemRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StatementItem resources) {
        StatementItem statementItem = statementItemRepository.findById(resources.getId()).orElseGet(StatementItem::new);
        ValidationUtil.isNull( statementItem.getId(),"StatementItem","id",resources.getId());
        statementItem.copy(resources);
        statementItemRepository.save(statementItem);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            statementItemRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<StatementItemDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StatementItemDto statementItem : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("数量", statementItem.getQuantity());
            map.put("采购单价", statementItem.getPurchasePrice());
            map.put("合计金额", statementItem.getTotalAmount());
            map.put("对账结果", statementItem.getStatementResult());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}