package com.br.repository;

import com.br.domain.EmailConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @date 2018-12-26
 */
public interface EmailRepository extends JpaRepository<EmailConfig,Long> {
}
