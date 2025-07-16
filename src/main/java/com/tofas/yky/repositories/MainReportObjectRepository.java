package com.tofas.yky.repositories;
/* T40127 @ 11.11.2015. */

import com.tofas.yky.model.report.MainReportObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MainReportObjectRepository extends JpaRepository<MainReportObject, Long>, JpaSpecificationExecutor<MainReportObject> {

}
