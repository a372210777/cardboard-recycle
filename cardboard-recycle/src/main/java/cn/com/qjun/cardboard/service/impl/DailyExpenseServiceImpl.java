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
import cn.com.qjun.cardboard.domain.DailyExpense;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import cn.com.qjun.cardboard.repository.DailyExpenseRepository;
import cn.com.qjun.cardboard.service.DailyExpenseService;
import cn.com.qjun.cardboard.service.dto.DailyExpenseDto;
import cn.com.qjun.cardboard.service.dto.DailyExpenseQueryCriteria;
import cn.com.qjun.cardboard.service.mapstruct.DailyExpenseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author RenQiang
* @date 2022-06-25
**/
@Service
@RequiredArgsConstructor
public class DailyExpenseServiceImpl implements DailyExpenseService {

    private final DailyExpenseRepository dailyExpenseRepository;
    private final DailyExpenseMapper dailyExpenseMapper;

    @Override
    public Map<String,Object> queryAll(DailyExpenseQueryCriteria criteria, Pageable pageable){
        Page<DailyExpense> page = dailyExpenseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(dailyExpenseMapper::toDto));
    }

    @Override
    public List<DailyExpenseDto> queryAll(DailyExpenseQueryCriteria criteria){
        return dailyExpenseMapper.toDto(dailyExpenseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public DailyExpenseDto findById(Integer id) {
        DailyExpense dailyExpense = dailyExpenseRepository.findById(id).orElseGet(DailyExpense::new);
        ValidationUtil.isNull(dailyExpense.getId(),"DailyExpense","id",id);
        return dailyExpenseMapper.toDto(dailyExpense);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DailyExpenseDto create(DailyExpense resources) {
        resources.setDeleted(SystemConstant.DEL_FLAG_0);
        return dailyExpenseMapper.toDto(dailyExpenseRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DailyExpense resources) {
        DailyExpense dailyExpense = dailyExpenseRepository.findById(resources.getId()).orElseGet(DailyExpense::new);
        ValidationUtil.isNull( dailyExpense.getId(),"DailyExpense","id",resources.getId());
        dailyExpense.copy(resources);
        dailyExpenseRepository.save(dailyExpense);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        List<DailyExpense> allById = dailyExpenseRepository.findAllById(Arrays.asList(ids));
        for (DailyExpense dailyExpense : allById) {
            dailyExpense.setDeleted(SystemConstant.DEL_FLAG_0);
        }
        dailyExpenseRepository.saveAll(allById);
    }

    @Override
    public void download(List<DailyExpenseDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DailyExpenseDto dailyExpense : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("开销分类", dailyExpense.getCategory());
            map.put("开销金额", dailyExpense.getMoney());
            map.put("开销日期", dailyExpense.getDate());
            map.put("备注", dailyExpense.getRemark());
            map.put("是否已删除", dailyExpense.getDeleted());
            map.put("创建人", dailyExpense.getCreateBy());
            map.put("更新人", dailyExpense.getUpdateBy());
            map.put("创建时间", dailyExpense.getCreateTime());
            map.put("更新时间", dailyExpense.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}