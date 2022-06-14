package com.br.modules.biz.service.impl;

import com.br.common.enums.StatusEnum;
import com.br.modules.biz.domain.WmsBaseFloorInfo;
import com.br.utils.ValidationUtil;
import com.br.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.br.modules.biz.repository.WmsBaseFloorInfoRepository;
import com.br.modules.biz.service.WmsBaseFloorInfoService;
import com.br.modules.biz.service.dto.WmsBaseFloorInfoDto;
import com.br.modules.biz.service.dto.WmsBaseFloorInfoQueryCriteria;
import com.br.modules.biz.service.mapstruct.WmsBaseFloorInfoMapper;
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
public class WmsBaseFloorInfoServiceImpl implements WmsBaseFloorInfoService {

    private final WmsBaseFloorInfoRepository wmsBaseFloorInfoRepository;
    private final WmsBaseFloorInfoMapper wmsBaseFloorInfoMapper;

    @Override
    public Map<String,Object> queryAll(WmsBaseFloorInfoQueryCriteria criteria, Pageable pageable){
        Page<WmsBaseFloorInfo> page = wmsBaseFloorInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(wmsBaseFloorInfoMapper::toDto));
    }

    @Override
    public List<WmsBaseFloorInfoDto> queryAll(WmsBaseFloorInfoQueryCriteria criteria){
        return wmsBaseFloorInfoMapper.toDto(wmsBaseFloorInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public WmsBaseFloorInfoDto findById(Long pkFloor) {
        WmsBaseFloorInfo wmsBaseFloorInfo = wmsBaseFloorInfoRepository.findById(pkFloor).orElseGet(WmsBaseFloorInfo::new);
        ValidationUtil.isNull(wmsBaseFloorInfo.getPkFloor(),"WmsBaseFloorInfo","pkFloor",pkFloor);
        return wmsBaseFloorInfoMapper.toDto(wmsBaseFloorInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WmsBaseFloorInfoDto create(WmsBaseFloorInfo resources) {
        return wmsBaseFloorInfoMapper.toDto(wmsBaseFloorInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(WmsBaseFloorInfo resources) {
        WmsBaseFloorInfo wmsBaseFloorInfo = wmsBaseFloorInfoRepository.findById(resources.getPkFloor()).orElseGet(WmsBaseFloorInfo::new);
        ValidationUtil.isNull( wmsBaseFloorInfo.getPkFloor(),"WmsBaseFloorInfo","id",resources.getPkFloor());
        wmsBaseFloorInfo.copy(resources);
        wmsBaseFloorInfoRepository.save(wmsBaseFloorInfo);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long pkFloor : ids) {
            wmsBaseFloorInfoRepository.deleteById(pkFloor);
        }
    }

    @Override
    public void download(List<WmsBaseFloorInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WmsBaseFloorInfoDto wmsBaseFloorInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("所属仓库", wmsBaseFloorInfo.getWarehouseInfo().getName());
            map.put("楼层编码", wmsBaseFloorInfo.getCode());
            map.put("楼层名称", wmsBaseFloorInfo.getName());
            map.put("所在楼层", wmsBaseFloorInfo.getFloorNo());
            map.put("状态", StatusEnum.findLabelByValue(wmsBaseFloorInfo.getStatus()));
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}