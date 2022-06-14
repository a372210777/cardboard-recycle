package com.br.repository;

import com.br.domain.AlipayConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @date 2018-12-31
 */
public interface AliPayRepository extends JpaRepository<AlipayConfig,Long> {
}
