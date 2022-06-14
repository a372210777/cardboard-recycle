package com.br.modules.biz.service.impl;

import com.br.modules.biz.domain.WmsBaseWarehousePositionInfo;
import com.br.utils.ValidationUtil;
import com.br.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.br.modules.biz.repository.WmsBaseWarehousePositionInfoRepository;
import com.br.modules.biz.service.WmsBaseWarehousePositionInfoService;
import com.br.modules.biz.service.dto.WmsBaseWarehousePositionInfoDto;
import com.br.modules.biz.service.dto.WmsBaseWarehousePositionInfoQueryCriteria;
import com.br.modules.biz.service.mapstruct.WmsBaseWarehousePositionInfoMapper;
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
public class WmsBaseWarehousePositionInfoServiceImpl implements WmsBaseWarehousePositionInfoService {

    private final WmsBaseWarehousePositionInfoRepository wmsBaseWarehousePositionInfoRepository;
    private final WmsBaseWarehousePositionInfoMapper wmsBaseWarehousePositionInfoMapper;

    @Override
    public Map<String,Object> queryAll(WmsBaseWarehousePositionInfoQueryCriteria criteria, Pageable pageable){
        Page<WmsBaseWarehousePositionInfo> page = wmsBaseWarehousePositionInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(wmsBaseWarehousePositionInfoMapper::toDto));
    }

    @Override
    public List<WmsBaseWarehousePositionInfoDto> queryAll(WmsBaseWarehousePositionInfoQueryCriteria criteria){
        return wmsBaseWarehousePositionInfoMapper.toDto(wmsBaseWarehousePositionInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public WmsBaseWarehousePositionInfoDto findById(Long pkWarehousePosition) {
        WmsBaseWarehousePositionInfo wmsBaseWarehousePositionInfo = wmsBaseWarehousePositionInfoRepository.findById(pkWarehousePosition).orElseGet(WmsBaseWarehousePositionInfo::new);
        ValidationUtil.isNull(wmsBaseWarehousePositionInfo.getPkWarehousePosition(),"WmsBaseWarehousePositionInfo","pkWarehousePosition",pkWarehousePosition);
        return wmsBaseWarehousePositionInfoMapper.toDto(wmsBaseWarehousePositionInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WmsBaseWarehousePositionInfoDto create(WmsBaseWarehousePositionInfo resources) {
        return wmsBaseWarehousePositionInfoMapper.toDto(wmsBaseWarehousePositionInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(WmsBaseWarehousePositionInfo resources) {
        WmsBaseWarehousePositionInfo wmsBaseWarehousePositionInfo = wmsBaseWarehousePositionInfoRepository.findById(resources.getPkWarehousePosition()).orElseGet(WmsBaseWarehousePositionInfo::new);
        ValidationUtil.isNull( wmsBaseWarehousePositionInfo.getPkWarehousePosition(),"WmsBaseWarehousePositionInfo","id",resources.getPkWarehousePosition());
        wmsBaseWarehousePositionInfo.copy(resources);
        wmsBaseWarehousePositionInfoRepository.save(wmsBaseWarehousePositionInfo);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long pkWarehousePosition : ids) {
            wmsBaseWarehousePositionInfoRepository.deleteById(pkWarehousePosition);
        }
    }

    @Override
    public void download(List<WmsBaseWarehousePositionInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WmsBaseWarehousePositionInfoDto wmsBaseWarehousePositionInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("所属库区", wmsBaseWarehousePositionInfo.getFkWarehouseArea());
            map.put("所属供应商(可存储多个id，逗号分隔)", wmsBaseWarehousePositionInfo.getSupplierIds());
            map.put("库位编码", wmsBaseWarehousePositionInfo.getCode());
            map.put("库位名称", wmsBaseWarehousePositionInfo.getName());
            map.put("所在列", wmsBaseWarehousePositionInfo.getColumns());
            map.put("状态（1启用，0禁用）", wmsBaseWarehousePositionInfo.getStatus());
            map.put("创建人id", wmsBaseWarehousePositionInfo.getCreateBy());
            map.put("创建时间", wmsBaseWarehousePositionInfo.getCreateTime());
            map.put("更新人id", wmsBaseWarehousePositionInfo.getUpdateBy());
            map.put("更新时间", wmsBaseWarehousePositionInfo.getUpdateTime());
            map.put("有效标识（1有效，0无效）", wmsBaseWarehousePositionInfo.getIsValid());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}