package com.br.modules.biz.service;


import com.br.modules.biz.domain.WmsBaseSupplierInfo;
import com.br.modules.biz.service.dto.WmsBaseSupplierInfoDto;
import com.br.modules.biz.service.dto.WmsBaseSupplierInfoQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author author
* @date 2022-06-06
**/
public interface WmsBaseSupplierInfoService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(WmsBaseSupplierInfoQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<WmsBaseSupplierInfoDto>
    */
    List<WmsBaseSupplierInfoDto> queryAll(WmsBaseSupplierInfoQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param pkSupplier ID
     * @return WmsBaseSupplierInfoDto
     */
    WmsBaseSupplierInfoDto findById(Long pkSupplier);

    /**
    * 创建
    * @param resources /
    * @return WmsBaseSupplierInfoDto
    */
    WmsBaseSupplierInfoDto create(WmsBaseSupplierInfo resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(WmsBaseSupplierInfo resources);

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
    void download(List<WmsBaseSupplierInfoDto> all, HttpServletResponse response) throws IOException;

    void templateDownload(HttpServletResponse response) throws IOException;
}