package com.tofas.yky.repositories;
/* T40127 @ 24.10.2015. */

import com.tofas.yky.model.MailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MailLogRepository extends JpaRepository<MailLog, Long>, JpaSpecificationExecutor<MailLog> { }
