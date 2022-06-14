package com.br.modules.biz.service.impl;

import com.br.common.DC;
import com.br.common.enums.StatusEnum;
import com.br.modules.biz.domain.WmsBaseWarehouseAreaInfo;
import com.br.modules.system.domain.DictDetail;
import com.br.modules.system.repository.DictDetailRepository;
import com.br.modules.system.service.DictDetailService;
import com.br.utils.ValidationUtil;
import com.br.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.br.modules.biz.repository.WmsBaseWarehouseAreaInfoRepository;
import com.br.modules.biz.service.WmsBaseWarehouseAreaInfoService;
import com.br.modules.biz.service.dto.WmsBaseWarehouseAreaInfoDto;
import com.br.modules.biz.service.dto.WmsBaseWarehouseAreaInfoQueryCriteria;
import com.br.modules.biz.service.mapstruct.WmsBaseWarehouseAreaInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.br.utils.PageUtil;
import com.br.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @author lin
* @date 2022-06-08
**/
@Service
@RequiredArgsConstructor
public class WmsBaseWarehouseAreaInfoServiceImpl implements WmsBaseWarehouseAreaInfoService {

    private final WmsBaseWarehouseAreaInfoRepository wmsBaseWarehouseAreaInfoRepository;
    private final WmsBaseWarehouseAreaInfoMapper wmsBaseWarehouseAreaInfoMapper;
    private final DictDetailService dictDetailService;
    @Override
    public Map<String,Object> queryAll(WmsBaseWarehouseAreaInfoQueryCriteria criteria, Pageable pageable){
        Page<WmsBaseWarehouseAreaInfo> page = wmsBaseWarehouseAreaInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(wmsBaseWarehouseAreaInfoMapper::toDto));
    }

    @Override
    public List<WmsBaseWarehouseAreaInfoDto> queryAll(WmsBaseWarehouseAreaInfoQueryCriteria criteria){
        return wmsBaseWarehouseAreaInfoMapper.toDto(wmsBaseWarehouseAreaInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public WmsBaseWarehouseAreaInfoDto findById(Long pkWarehouseArea) {
        WmsBaseWarehouseAreaInfo wmsBaseWarehouseAreaInfo = wmsBaseWarehouseAreaInfoRepository.findById(pkWarehouseArea).orElseGet(WmsBaseWarehouseAreaInfo::new);
        ValidationUtil.isNull(wmsBaseWarehouseAreaInfo.getPkWarehouseArea(),"WmsBaseWarehouseAreaInfo","pkWarehouseArea",pkWarehouseArea);
        return wmsBaseWarehouseAreaInfoMapper.toDto(wmsBaseWarehouseAreaInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WmsBaseWarehouseAreaInfoDto create(WmsBaseWarehouseAreaInfo resources) {
        return wmsBaseWarehouseAreaInfoMapper.toDto(wmsBaseWarehouseAreaInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(WmsBaseWarehouseAreaInfo resources) {
        WmsBaseWarehouseAreaInfo wmsBaseWarehouseAreaInfo = wmsBaseWarehouseAreaInfoRepository.findById(resources.getPkWarehouseArea()).orElseGet(WmsBaseWarehouseAreaInfo::new);
        ValidationUtil.isNull( wmsBaseWarehouseAreaInfo.getPkWarehouseArea(),"WmsBaseWarehouseAreaInfo","id",resources.getPkWarehouseArea());
        wmsBaseWarehouseAreaInfo.copy(resources);
        wmsBaseWarehouseAreaInfoRepository.save(wmsBaseWarehouseAreaInfo);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long pkWarehouseArea : ids) {
            wmsBaseWarehouseAreaInfoRepository.deleteById(pkWarehouseArea);
        }
    }

    @Override
    public void download(List<WmsBaseWarehouseAreaInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WmsBaseWarehouseAreaInfoDto wmsBaseWarehouseAreaInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("所属楼层", wmsBaseWarehouseAreaInfo.getFloorInfo().getName());
            map.put("库区编码", wmsBaseWarehouseAreaInfo.getCode());
            map.put("库区名称", wmsBaseWarehouseAreaInfo.getName());
            DictDetail dictDetail = dictDetailService.findByDictNameAndValue(DC.WarehouseArea.TYPE, wmsBaseWarehouseAreaInfo.getType().toString());
            map.put("库区类型", dictDetail==null? wmsBaseWarehouseAreaInfo.getType():dictDetail.getLabel());
            map.put("状态", StatusEnum.findLabelByValue(wmsBaseWarehouseAreaInfo.getStatus()));
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public List<WmsBaseWarehouseAreaInfo> findByFloor(Long pkFloor) {
        return wmsBaseWarehouseAreaInfoRepository.findByFloorInfo_PkFloor(pkFloor);
    }
}