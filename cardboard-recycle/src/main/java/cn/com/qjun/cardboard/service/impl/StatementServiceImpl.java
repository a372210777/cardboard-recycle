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

import cn.com.qjun.cardboard.domain.Statement;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import cn.com.qjun.cardboard.repository.StatementRepository;
import cn.com.qjun.cardboard.service.StatementService;
import cn.com.qjun.cardboard.service.dto.StatementDto;
import cn.com.qjun.cardboard.service.dto.StatementQueryCriteria;
import cn.com.qjun.cardboard.service.mapstruct.StatementMapper;
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
public class StatementServiceImpl implements StatementService {

    private final StatementRepository statementRepository;
    private final StatementMapper statementMapper;

    @Override
    public Map<String,Object> queryAll(StatementQueryCriteria criteria, Pageable pageable){
        Page<Statement> page = statementRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(statementMapper::toDto));
    }

    @Override
    public List<StatementDto> queryAll(StatementQueryCriteria criteria){
        return statementMapper.toDto(statementRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public StatementDto findById(String id) {
        Statement statement = statementRepository.findById(id).orElseGet(Statement::new);
        ValidationUtil.isNull(statement.getId(),"Statement","id",id);
        return statementMapper.toDto(statement);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StatementDto create(Statement resources) {
        resources.setId(IdUtil.simpleUUID()); 
        return statementMapper.toDto(statementRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Statement resources) {
        Statement statement = statementRepository.findById(resources.getId()).orElseGet(Statement::new);
        ValidationUtil.isNull( statement.getId(),"Statement","id",resources.getId());
        statement.copy(resources);
        statementRepository.save(statement);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            statementRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<StatementDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StatementDto statement : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("对账年份", statement.getYear());
            map.put("对账月份", statement.getMonth());
            map.put("对账人", statement.getCreateBy());
            map.put("对账时间", statement.getStatementTime());
            map.put("创建时间", statement.getCreateTime());
            map.put("更新人", statement.getUpdateBy());
            map.put("更新时间", statement.getUpdateTime());
            map.put("是否已删除", statement.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}