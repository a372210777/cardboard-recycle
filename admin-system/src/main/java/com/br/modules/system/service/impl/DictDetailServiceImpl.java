package com.br.modules.system.service.impl;

import lombok.RequiredArgsConstructor;
import com.br.modules.system.domain.Dict;
import com.br.modules.system.domain.DictDetail;
import com.br.modules.system.repository.DictRepository;
import com.br.modules.system.service.dto.DictDetailQueryCriteria;
import com.br.utils.PageUtil;
import com.br.utils.QueryHelp;
import com.br.utils.RedisUtils;
import com.br.utils.ValidationUtil;
import com.br.modules.system.repository.DictDetailRepository;
import com.br.modules.system.service.DictDetailService;
import com.br.modules.system.service.dto.DictDetailDto;
import com.br.modules.system.service.mapstruct.DictDetailMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

/**
*
* @date 2019-04-10
*/
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "dict")
public class DictDetailServiceImpl implements DictDetailService {

    private final DictRepository dictRepository;
    private final DictDetailRepository dictDetailRepository;
    private final DictDetailMapper dictDetailMapper;
    private final RedisUtils redisUtils;

    @Override
    public Map<String,Object> queryAll(DictDetailQueryCriteria criteria, Pageable pageable) {
        Page<DictDetail> page = dictDetailRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(dictDetailMapper::toDto));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(DictDetail resources) {
        dictDetailRepository.save(resources);
        // 清理缓存
        delCaches(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DictDetail resources) {
        DictDetail dictDetail = dictDetailRepository.findById(resources.getId()).orElseGet(DictDetail::new);
        ValidationUtil.isNull( dictDetail.getId(),"DictDetail","id",resources.getId());
        resources.setId(dictDetail.getId());
        dictDetailRepository.save(resources);
        // 清理缓存
        delCaches(resources);
    }

    @Override
    @Cacheable(key = "'name:' + #p0")
    public List<DictDetailDto> getDictByName(String name) {
        return dictDetailMapper.toDto(dictDetailRepository.findByDictName(name));
    }

    @Override
    public DictDetail findByDictNameAndLabel(String dictName, String label) {
        return dictDetailRepository.findByDictNameAndLabel(dictName, label);
    }

    @Override
    public DictDetail findByDictNameAndValue(String dictName, String value) {
        return dictDetailRepository.findByDictNameAndValue(dictName, value);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        DictDetail dictDetail = dictDetailRepository.findById(id).orElseGet(DictDetail::new);
        // 清理缓存
        delCaches(dictDetail);
        dictDetailRepository.deleteById(id);
    }

    public void delCaches(DictDetail dictDetail){
        Dict dict = dictRepository.findById(dictDetail.getDict().getId()).orElseGet(Dict::new);
        redisUtils.del("dict::name:" + dict.getName());
    }
}