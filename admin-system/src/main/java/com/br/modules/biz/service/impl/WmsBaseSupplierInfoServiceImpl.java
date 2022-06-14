package com.br.modules.biz.service.impl;


import com.br.common.DC;
import com.br.common.enums.StatusEnum;
import com.br.modules.biz.domain.WmsBaseSupplierInfo;
import com.br.modules.biz.repository.WmsBaseSupplierInfoRepository;
import com.br.modules.biz.service.WmsBaseSupplierInfoService;
import com.br.modules.biz.service.dto.WmsBaseSupplierInfoDto;
import com.br.modules.biz.service.dto.WmsBaseSupplierInfoQueryCriteria;
import com.br.modules.biz.service.mapstruct.WmsBaseSupplierInfoMapper;
import com.br.modules.system.domain.DictDetail;
import com.br.modules.system.service.DictDetailService;
import com.br.modules.system.service.UserService;
import com.br.modules.system.service.dto.UserDto;
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
public class WmsBaseSupplierInfoServiceImpl implements WmsBaseSupplierInfoService {

    private final WmsBaseSupplierInfoRepository wmsBaseSupplierInfoRepository;
    private final WmsBaseSupplierInfoMapper wmsBaseSupplierInfoMapper;
    private final DictDetailService dictDetailService;
    private final UserService userService;

    @Override
    public Map<String,Object> queryAll(WmsBaseSupplierInfoQueryCriteria criteria, Pageable pageable){
        Page<WmsBaseSupplierInfo> page = wmsBaseSupplierInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(wmsBaseSupplierInfoMapper::toDto));
    }

    @Override
    public List<WmsBaseSupplierInfoDto> queryAll(WmsBaseSupplierInfoQueryCriteria criteria){
        return wmsBaseSupplierInfoMapper.toDto(wmsBaseSupplierInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public WmsBaseSupplierInfoDto findById(Long pkSupplier) {
        WmsBaseSupplierInfo wmsBaseSupplierInfo = wmsBaseSupplierInfoRepository.findById(pkSupplier).orElseGet(WmsBaseSupplierInfo::new);
        ValidationUtil.isNull(wmsBaseSupplierInfo.getPkSupplier(),"WmsBaseSupplierInfo","pkSupplier",pkSupplier);
        return wmsBaseSupplierInfoMapper.toDto(wmsBaseSupplierInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WmsBaseSupplierInfoDto create(WmsBaseSupplierInfo resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setPkSupplier(snowflake.nextId()); 
        return wmsBaseSupplierInfoMapper.toDto(wmsBaseSupplierInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(WmsBaseSupplierInfo resources) {
        WmsBaseSupplierInfo wmsBaseSupplierInfo = wmsBaseSupplierInfoRepository.findById(resources.getPkSupplier()).orElseGet(WmsBaseSupplierInfo::new);
        ValidationUtil.isNull( wmsBaseSupplierInfo.getPkSupplier(),"WmsBaseSupplierInfo","id",resources.getPkSupplier());
        wmsBaseSupplierInfo.copy(resources);
        wmsBaseSupplierInfoRepository.save(wmsBaseSupplierInfo);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long pkSupplier : ids) {
            wmsBaseSupplierInfoRepository.deleteById(pkSupplier);
        }
    }

    @Override
    public void download(List<WmsBaseSupplierInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WmsBaseSupplierInfoDto wmsBaseSupplierInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("编码", wmsBaseSupplierInfo.getCode());
            map.put("简称", wmsBaseSupplierInfo.getShortName());
            map.put("全称", wmsBaseSupplierInfo.getFullName());
            DictDetail signTypeDict = null;
            if (wmsBaseSupplierInfo.getSignType() != null) {
                signTypeDict = dictDetailService.findByDictNameAndValue(DC.Supplier.SIGN_TYPE, wmsBaseSupplierInfo.getSignType().toString());
            }
            map.put("签约方式", signTypeDict == null? wmsBaseSupplierInfo.getSignType():signTypeDict.getLabel());
            DictDetail settlementTypeDict = null;
            if (wmsBaseSupplierInfo.getSignType() != null) {
                settlementTypeDict = dictDetailService.findByDictNameAndValue(DC.Supplier.SETTLEMENT_TYPE, wmsBaseSupplierInfo.getSettlementType().toString());
            }
            map.put("结算方式", settlementTypeDict == null? wmsBaseSupplierInfo.getSettlementType():settlementTypeDict.getLabel());
            map.put("结算周期（月） ", wmsBaseSupplierInfo.getSettlementCycle());
            map.put("结算日", wmsBaseSupplierInfo.getSettlementDays());
            map.put("责任客服", wmsBaseSupplierInfo.getUser() == null ? "" : wmsBaseSupplierInfo.getUser().getUsername());
            map.put("状态", StatusEnum.findLabelByValue(wmsBaseSupplierInfo.getStatus()));
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void templateDownload(HttpServletResponse response) throws IOException{
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("供应商编码*", "");
        map.put("供应商名称*", "");
        map.put("供应商全称*", "");
        map.put("签约类型(常用/合同)", "");
        map.put("结算方式(月结/月结+回单付/回单付)", "");
        map.put("结算周期(单位：月)", "");
        map.put("结算日", "");
        map.put("责任客服", "");
        list.add(map);
        FileUtil.downloadExcel(list, response);
    }
}