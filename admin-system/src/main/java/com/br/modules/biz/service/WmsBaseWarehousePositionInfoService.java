package com.br.modules.biz.service;

import com.br.modules.biz.domain.WmsBaseWarehousePositionInfo;
import com.br.modules.biz.service.dto.WmsBaseWarehousePositionInfoDto;
import com.br.modules.biz.service.dto.WmsBaseWarehousePositionInfoQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author lin
* @date 2022-06-08
**/
public interface WmsBaseWarehousePositionInfoService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(WmsBaseWarehousePositionInfoQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<WmsBaseWarehousePositionInfoDto>
    */
    List<WmsBaseWarehousePositionInfoDto> queryAll(WmsBaseWarehousePositionInfoQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param pkWarehousePosition ID
     * @return WmsBaseWarehousePositionInfoDto
     */
    WmsBaseWarehousePositionInfoDto findById(Long pkWarehousePosition);

    /**
    * 创建
    * @param resources /
    * @return WmsBaseWarehousePositionInfoDto
    */
    WmsBaseWarehousePositionInfoDto create(WmsBaseWarehousePositionInfo resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(WmsBaseWarehousePositionInfo resources);

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
    void download(List<WmsBaseWarehousePositionInfoDto> all, HttpServletResponse response) throws IOException;
}