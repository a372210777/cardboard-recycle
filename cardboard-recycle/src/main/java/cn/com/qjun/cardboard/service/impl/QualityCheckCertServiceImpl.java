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

import cn.com.qjun.cardboard.domain.QualityCheckCert;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import cn.com.qjun.cardboard.repository.QualityCheckCertRepository;
import cn.com.qjun.cardboard.service.QualityCheckCertService;
import cn.com.qjun.cardboard.service.dto.QualityCheckCertDto;
import cn.com.qjun.cardboard.service.dto.QualityCheckCertQueryCriteria;
import cn.com.qjun.cardboard.service.mapstruct.QualityCheckCertMapper;
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
public class QualityCheckCertServiceImpl implements QualityCheckCertService {

    private final QualityCheckCertRepository qualityCheckCertRepository;
    private final QualityCheckCertMapper qualityCheckCertMapper;

    @Override
    public Map<String,Object> queryAll(QualityCheckCertQueryCriteria criteria, Pageable pageable){
        Page<QualityCheckCert> page = qualityCheckCertRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(qualityCheckCertMapper::toDto));
    }

    @Override
    public List<QualityCheckCertDto> queryAll(QualityCheckCertQueryCriteria criteria){
        return qualityCheckCertMapper.toDto(qualityCheckCertRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public QualityCheckCertDto findById(String id) {
        QualityCheckCert qualityCheckCert = qualityCheckCertRepository.findById(id).orElseGet(QualityCheckCert::new);
        ValidationUtil.isNull(qualityCheckCert.getId(),"QualityCheckCert","id",id);
        return qualityCheckCertMapper.toDto(qualityCheckCert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QualityCheckCertDto create(QualityCheckCert resources) {
        resources.setId(IdUtil.simpleUUID()); 
        return qualityCheckCertMapper.toDto(qualityCheckCertRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(QualityCheckCert resources) {
        QualityCheckCert qualityCheckCert = qualityCheckCertRepository.findById(resources.getId()).orElseGet(QualityCheckCert::new);
        ValidationUtil.isNull( qualityCheckCert.getId(),"QualityCheckCert","id",resources.getId());
        qualityCheckCert.copy(resources);
        qualityCheckCertRepository.save(qualityCheckCert);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            qualityCheckCertRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<QualityCheckCertDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QualityCheckCertDto qualityCheckCert : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("所属出库单明细", qualityCheckCert.getStockOutOrderItemId());
            map.put("采购商ID", qualityCheckCert.getBuyerId());
            map.put("毛重", qualityCheckCert.getGrossWeight());
            map.put("皮重", qualityCheckCert.getTareWeight());
            map.put("净重", qualityCheckCert.getNetWeight());
            map.put("扣重", qualityCheckCert.getDeductWeight());
            map.put("称重时间", qualityCheckCert.getWeightingTime());
            map.put("车牌号", qualityCheckCert.getLicensePlate());
            map.put("总件数", qualityCheckCert.getTotalPacks());
            map.put("抽检数", qualityCheckCert.getSpotCheckPacks());
            map.put("水分百分比", qualityCheckCert.getWaterPercent());
            map.put("杂物百分比", qualityCheckCert.getImpurityPercent());
            map.put("杂纸百分比", qualityCheckCert.getIncidentalPaperPercent());
            map.put("综合折率", qualityCheckCert.getTotalDeductPercent());
            map.put("称重单附件", qualityCheckCert.getWeighingAttachment());
            map.put("质检单附件", qualityCheckCert.getAttachment());
            map.put("备注", qualityCheckCert.getRemark());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}