package com.tofas.yky.repositories;
/* t40127 @ 14.06.2016. */

import com.tofas.yky.model.report.VEfficiencyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EfficiencyReportRepository extends JpaRepository<VEfficiencyReport, String>, JpaSpecificationExecutor<VEfficiencyReport> {
}
