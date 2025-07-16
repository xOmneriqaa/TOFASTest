package com.tofas.yky.repositories;
/* T40127 @ 11.11.2015. */

import com.tofas.yky.model.report.LossDetailReportObject;
import com.tofas.yky.model.report.MainReportObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LossDetailReportRepository extends JpaRepository<LossDetailReportObject, Integer>, JpaSpecificationExecutor<LossDetailReportObject> {
}
