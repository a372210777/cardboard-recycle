package com.br.modules.biz.service.impl;

import com.br.modules.biz.domain.WmsBaseWarehouseInfo;
import com.br.modules.biz.repository.WmsBaseWarehouseInfoRepository;
import com.br.modules.biz.service.WmsBaseWarehouseInfoService;
import com.br.modules.biz.service.dto.WmsBaseWarehouseInfoDto;
import com.br.modules.biz.service.dto.WmsBaseWarehouseInfoQueryCriteria;
import com.br.modules.biz.service.mapstruct.WmsBaseWarehouseInfoMapper;
import com.br.utils.ValidationUtil;
import com.br.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
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
* @date 2022-06-06
**/
@Service
@RequiredArgsConstructor
public class WmsBaseWarehouseInfoServiceImpl implements WmsBaseWarehouseInfoService {

    private final WmsBaseWarehouseInfoRepository wmsBaseWarehouseInfoRepository;
    private final WmsBaseWarehouseInfoMapper wmsBaseWarehouseInfoMapper;

    @Override
    public Map<String,Object> queryAll(WmsBaseWarehouseInfoQueryCriteria criteria, Pageable pageable){
        Page<WmsBaseWarehouseInfo> page = wmsBaseWarehouseInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(wmsBaseWarehouseInfoMapper::toDto));
    }

    @Override
    public List<WmsBaseWarehouseInfoDto> queryAll(WmsBaseWarehouseInfoQueryCriteria criteria){
        return wmsBaseWarehouseInfoMapper.toDto(wmsBaseWarehouseInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public WmsBaseWarehouseInfoDto findById(Long pkWarehouse) {
        WmsBaseWarehouseInfo wmsBaseWarehouseInfo = wmsBaseWarehouseInfoRepository.findById(pkWarehouse).orElseGet(WmsBaseWarehouseInfo::new);
        ValidationUtil.isNull(wmsBaseWarehouseInfo.getPkWarehouse(),"WmsBaseWarehouseInfo","pkWarehouse",pkWarehouse);
        return wmsBaseWarehouseInfoMapper.toDto(wmsBaseWarehouseInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WmsBaseWarehouseInfoDto create(WmsBaseWarehouseInfo resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setPkWarehouse(snowflake.nextId()); 
        return wmsBaseWarehouseInfoMapper.toDto(wmsBaseWarehouseInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(WmsBaseWarehouseInfo resources) {
        WmsBaseWarehouseInfo wmsBaseWarehouseInfo = wmsBaseWarehouseInfoRepository.findById(resources.getPkWarehouse()).orElseGet(WmsBaseWarehouseInfo::new);
        ValidationUtil.isNull( wmsBaseWarehouseInfo.getPkWarehouse(),"WmsBaseWarehouseInfo","id",resources.getPkWarehouse());
        wmsBaseWarehouseInfo.copy(resources);
        wmsBaseWarehouseInfoRepository.save(wmsBaseWarehouseInfo);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long pkWarehouse : ids) {
            wmsBaseWarehouseInfoRepository.deleteById(pkWarehouse);
        }
    }

    @Override
    public void download(List<WmsBaseWarehouseInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WmsBaseWarehouseInfoDto wmsBaseWarehouseInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("仓库编码", wmsBaseWarehouseInfo.getCode());
            map.put("仓库名称", wmsBaseWarehouseInfo.getName());
            map.put("仓库负责人", wmsBaseWarehouseInfo.getPrincipal());
            map.put("联系电话", wmsBaseWarehouseInfo.getContactPhone());
            map.put("仓库地址", wmsBaseWarehouseInfo.getAddress());
            map.put("仓库地区", wmsBaseWarehouseInfo.getArea());
            map.put("有效标识（1有效，0无效）", wmsBaseWarehouseInfo.getIsValid());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}