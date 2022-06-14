package com.br.modules.system.service.dto;

import lombok.Data;
import com.br.annotation.Query;

/**
*
* @date 2019-04-10
*/
@Data
public class DictDetailQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String label;

    @Query(propName = "name",joinName = "dict")
    private String dictName;

    @Query
    private Integer entryId;

    @Query
    private Integer entryParentId;
}