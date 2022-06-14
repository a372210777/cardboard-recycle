package com.br.modules.biz.service;


import com.br.modules.biz.domain.WmsBaseWarehouseInfo;
import com.br.modules.biz.service.dto.WmsBaseWarehouseInfoDto;
import com.br.modules.biz.service.dto.WmsBaseWarehouseInfoQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author lin
* @date 2022-06-06
**/
public interface WmsBaseWarehouseInfoService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(WmsBaseWarehouseInfoQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<WmsBaseWarehouseInfoDto>
    */
    List<WmsBaseWarehouseInfoDto> queryAll(WmsBaseWarehouseInfoQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param pkWarehouse ID
     * @return WmsBaseWarehouseInfoDto
     */
    WmsBaseWarehouseInfoDto findById(Long pkWarehouse);

    /**
    * 创建
    * @param resources /
    * @return WmsBaseWarehouseInfoDto
    */
    WmsBaseWarehouseInfoDto create(WmsBaseWarehouseInfo resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(WmsBaseWarehouseInfo resources);

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
    void download(List<WmsBaseWarehouseInfoDto> all, HttpServletResponse response) throws IOException;
}