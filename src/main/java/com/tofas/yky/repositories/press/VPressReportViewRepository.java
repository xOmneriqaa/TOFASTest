package com.tofas.yky.repositories.press;
/* T40127 @ 10.06.2016. */

import com.tofas.yky.model.losses.press.VPressReportView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VPressReportViewRepository extends JpaRepository<VPressReportView, String>, JpaSpecificationExecutor<VPressReportView> {
}
