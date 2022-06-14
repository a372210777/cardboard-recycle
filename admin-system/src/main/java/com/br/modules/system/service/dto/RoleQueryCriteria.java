package com.br.modules.system.service.dto;

import lombok.Data;
import com.br.annotation.Query;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * 公共查询类
 */
@Data
public class RoleQueryCriteria {

    @Query(blurry = "name,description")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
