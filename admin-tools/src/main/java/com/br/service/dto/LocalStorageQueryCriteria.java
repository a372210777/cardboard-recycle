package com.br.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

import com.br.annotation.Query;

/**
*
* @date 2019-09-05
*/
@Data
public class LocalStorageQueryCriteria{

    @Query(blurry = "name,suffix,type,createBy,size")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}