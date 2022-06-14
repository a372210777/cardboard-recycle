package com.br.common.utils;

import java.io.Serializable;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

/**
 *  自定义的主键生成策略，如果填写了主键id，如果数据库中没有这条记录，则新增指定id的记录；否则更新记录
 *  如果不填写主键id，则利用数据库本身的自增策略指定id
 */
public class ManulInsertGenerator extends IdentityGenerator {

    @Override
	public Serializable generate(SharedSessionContractImplementor s, Object obj) {
		Serializable id = s.getEntityPersister(null, obj).getClassMetadata().getIdentifier(obj, s);

        if (id != null && Long.valueOf(id.toString()) > 0) {
            return id;
        } else {
            return super.generate(s, obj);
        }
	}
}
