package com.br.modules.biz.service;

import com.br.modules.biz.domain.WmsBaseWarehouseAreaInfo;
import com.br.modules.biz.service.dto.WmsBaseWarehouseAreaInfoDto;
import com.br.modules.biz.service.dto.WmsBaseWarehouseAreaInfoQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author lin
* @date 2022-06-08
**/
public interface WmsBaseWarehouseAreaInfoService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(WmsBaseWarehouseAreaInfoQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<WmsBaseWarehouseAreaInfoDto>
    */
    List<WmsBaseWarehouseAreaInfoDto> queryAll(WmsBaseWarehouseAreaInfoQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param pkWarehouseArea ID
     * @return WmsBaseWarehouseAreaInfoDto
     */
    WmsBaseWarehouseAreaInfoDto findById(Long pkWarehouseArea);

    /**
    * 创建
    * @param resources /
    * @return WmsBaseWarehouseAreaInfoDto
    */
    WmsBaseWarehouseAreaInfoDto create(WmsBaseWarehouseAreaInfo resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(WmsBaseWarehouseAreaInfo resources);

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
    void download(List<WmsBaseWarehouseAreaInfoDto> all, HttpServletResponse response) throws IOException;

    /**
     * 获取某楼层的库区列表
     * @param pkFloor
     * @return
     */
    List<WmsBaseWarehouseAreaInfo> findByFloor(Long pkFloor);
}