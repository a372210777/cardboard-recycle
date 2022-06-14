package com.br.modules.system.service.dto;

import lombok.Data;
import com.br.annotation.DataPermission;
import com.br.annotation.Query;
import java.sql.Timestamp;
import java.util.List;

/**
*
* @date 2019-03-25
*/
@Data
@DataPermission(fieldName = "id")
public class DeptQueryCriteria{

    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    @Query
    private Boolean enabled;

    @Query
    private Long pid;

    @Query(type = Query.Type.IS_NULL, propName = "pid")
    private Boolean pidIsNull;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;

}