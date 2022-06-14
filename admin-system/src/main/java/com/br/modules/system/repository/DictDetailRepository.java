package com.br.modules.system.repository;

import com.br.modules.system.domain.DictDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
*
* @date 2019-04-10
*/
public interface DictDetailRepository extends JpaRepository<DictDetail, Long>, JpaSpecificationExecutor<DictDetail> {

    /**
     * 根据字典名称查询
     * @param name /
     * @return /
     */
    List<DictDetail> findByDictName(String name);

    @Query(value = "from DictDetail t where t.dict.name = ?1 and t.label =?2")
    DictDetail findByDictNameAndLabel(String dictName, String label);

    @Query(value = "from DictDetail t where t.dict.name = ?1 and t.value =?2")
    DictDetail findByDictNameAndValue(String dictName, String value);
}