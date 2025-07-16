package com.tofas.yky.repositories;
/* T40127 @ 16.11.2015. */

import com.tofas.yky.model.JobRunLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRunLogRepository extends JpaRepository<JobRunLog, Long> {
}
