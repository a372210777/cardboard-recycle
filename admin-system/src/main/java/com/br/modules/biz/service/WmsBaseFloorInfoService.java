package com.br.modules.biz.service;

import com.br.modules.biz.domain.WmsBaseFloorInfo;
import com.br.modules.biz.service.dto.WmsBaseFloorInfoDto;
import com.br.modules.biz.service.dto.WmsBaseFloorInfoQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author lin
* @date 2022-06-08
**/
public interface WmsBaseFloorInfoService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(WmsBaseFloorInfoQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<WmsBaseFloorInfoDto>
    */
    List<WmsBaseFloorInfoDto> queryAll(WmsBaseFloorInfoQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param pkFloor ID
     * @return WmsBaseFloorInfoDto
     */
    WmsBaseFloorInfoDto findById(Long pkFloor);

    /**
    * 创建
    * @param resources /
    * @return WmsBaseFloorInfoDto
    */
    WmsBaseFloorInfoDto create(WmsBaseFloorInfo resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(WmsBaseFloorInfo resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<WmsBaseFloorInfoDto> all, HttpServletResponse response) throws IOException;
}