package com.br.modules.biz.service;


import com.br.modules.biz.domain.WmsBaseSupplierContactInfo;
import com.br.modules.biz.service.dto.WmsBaseSupplierContactInfoDto;
import com.br.modules.biz.service.dto.WmsBaseSupplierContactInfoQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author author
* @date 2022-06-06
**/
public interface WmsBaseSupplierContactInfoService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(WmsBaseSupplierContactInfoQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<WmsBaseSupplierContactInfoDto>
    */
    List<WmsBaseSupplierContactInfoDto> queryAll(WmsBaseSupplierContactInfoQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param pkSupplierContact ID
     * @return WmsBaseSupplierContactInfoDto
     */
    WmsBaseSupplierContactInfoDto findById(Long pkSupplierContact);

    /**
    * 创建
    * @param resources /
    * @return WmsBaseSupplierContactInfoDto
    */
    WmsBaseSupplierContactInfoDto create(WmsBaseSupplierContactInfo resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(WmsBaseSupplierContactInfo resources);

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
    void download(List<WmsBaseSupplierContactInfoDto> all, HttpServletResponse response) throws IOException;
}