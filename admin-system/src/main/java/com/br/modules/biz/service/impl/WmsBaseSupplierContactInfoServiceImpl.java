package com.br.modules.biz.service.impl;

import com.br.modules.biz.domain.WmsBaseSupplierContactInfo;
import com.br.modules.biz.repository.WmsBaseSupplierContactInfoRepository;
import com.br.modules.biz.service.WmsBaseSupplierContactInfoService;
import com.br.modules.biz.service.dto.WmsBaseSupplierContactInfoDto;
import com.br.modules.biz.service.dto.WmsBaseSupplierContactInfoQueryCriteria;
import com.br.modules.biz.service.mapstruct.WmsBaseSupplierContactInfoMapper;
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
* @author author
* @date 2022-06-06
**/
@Service
@RequiredArgsConstructor
public class WmsBaseSupplierContactInfoServiceImpl implements WmsBaseSupplierContactInfoService {

    private final WmsBaseSupplierContactInfoRepository wmsBaseSupplierContactInfoRepository;
    private final WmsBaseSupplierContactInfoMapper wmsBaseSupplierContactInfoMapper;

    @Override
    public Map<String,Object> queryAll(WmsBaseSupplierContactInfoQueryCriteria criteria, Pageable pageable){
        Page<WmsBaseSupplierContactInfo> page = wmsBaseSupplierContactInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(wmsBaseSupplierContactInfoMapper::toDto));
    }

    @Override
    public List<WmsBaseSupplierContactInfoDto> queryAll(WmsBaseSupplierContactInfoQueryCriteria criteria){
        return wmsBaseSupplierContactInfoMapper.toDto(wmsBaseSupplierContactInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public WmsBaseSupplierContactInfoDto findById(Long pkSupplierContact) {
        WmsBaseSupplierContactInfo wmsBaseSupplierContactInfo = wmsBaseSupplierContactInfoRepository.findById(pkSupplierContact).orElseGet(WmsBaseSupplierContactInfo::new);
        ValidationUtil.isNull(wmsBaseSupplierContactInfo.getPkSupplierContact(),"WmsBaseSupplierContactInfo","pkSupplierContact",pkSupplierContact);
        return wmsBaseSupplierContactInfoMapper.toDto(wmsBaseSupplierContactInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WmsBaseSupplierContactInfoDto create(WmsBaseSupplierContactInfo resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setPkSupplierContact(snowflake.nextId()); 
        return wmsBaseSupplierContactInfoMapper.toDto(wmsBaseSupplierContactInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(WmsBaseSupplierContactInfo resources) {
        WmsBaseSupplierContactInfo wmsBaseSupplierContactInfo = wmsBaseSupplierContactInfoRepository.findById(resources.getPkSupplierContact()).orElseGet(WmsBaseSupplierContactInfo::new);
        ValidationUtil.isNull( wmsBaseSupplierContactInfo.getPkSupplierContact(),"WmsBaseSupplierContactInfo","id",resources.getPkSupplierContact());
        wmsBaseSupplierContactInfo.copy(resources);
        wmsBaseSupplierContactInfoRepository.save(wmsBaseSupplierContactInfo);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long pkSupplierContact : ids) {
            wmsBaseSupplierContactInfoRepository.deleteById(pkSupplierContact);
        }
    }

    @Override
    public void download(List<WmsBaseSupplierContactInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WmsBaseSupplierContactInfoDto wmsBaseSupplierContactInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("站点名称", wmsBaseSupplierContactInfo.getStationName());
            map.put("省市区", wmsBaseSupplierContactInfo.getProvinceAndCity());
            map.put("地址", wmsBaseSupplierContactInfo.getAddress());
            map.put("联系人", wmsBaseSupplierContactInfo.getContactName());
            map.put("联系电话", wmsBaseSupplierContactInfo.getContactPhone());
            map.put("备注", wmsBaseSupplierContactInfo.getMemo());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}