package com.tofas.yky.repositories;
/* T40127 @ 26.11.2015. */

import com.tofas.yky.model.dashboard.VDashboardData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface VDashboardDataRepository extends JpaRepository<VDashboardData, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM tfs_yky.V_DASHBOARD_DATA WHERE TO_CHAR(LOSS_DATE, 'YYYY') = TO_CHAR(SYSDATE, 'YYYY')")
    Set<VDashboardData> listYearlyData();

    @Query(nativeQuery = true, value = "SELECT LOSS_ID FROM tfs_yky.V_DASHBOARD_DATA WHERE TO_CHAR(LOSS_DATE, 'YYYY') = TO_CHAR(SYSDATE, 'YYYY')")
    Set<BigDecimal> listYearlyDataIds();

    @Query(nativeQuery = true, value = "SELECT * FROM tfs_yky.V_DASHBOARD_DATA WHERE TO_CHAR(LOSS_DATE, 'YYYY') = TO_CHAR(SYSDATE, 'YYYY') AND TO_CHAR(LOSS_DATE, 'Q') = TO_CHAR(SYSDATE, 'Q')")
    Set<VDashboardData> listQuarterData();

    @Query(nativeQuery = true, value = "SELECT LOSS_ID FROM tfs_yky.V_DASHBOARD_DATA WHERE TO_CHAR(LOSS_DATE, 'YYYY') = TO_CHAR(SYSDATE, 'YYYY') AND TO_CHAR(LOSS_DATE, 'Q') = TO_CHAR(SYSDATE, 'Q')")
    Set<BigDecimal> listQuarterDataIds();

    @Query(nativeQuery = true, value = "SELECT * FROM tfs_yky.V_DASHBOARD_DATA WHERE TO_CHAR(LOSS_DATE, 'YYYY') = TO_CHAR(SYSDATE, 'YYYY') AND TO_CHAR(LOSS_DATE, 'MM') = TO_CHAR(SYSDATE, 'MM')")
    Set<VDashboardData> listMonthData();

    @Query(nativeQuery = true, value = "SELECT LOSS_ID FROM tfs_yky.V_DASHBOARD_DATA WHERE TO_CHAR(LOSS_DATE, 'YYYY') = TO_CHAR(SYSDATE, 'YYYY') AND TO_CHAR(LOSS_DATE, 'MM') = TO_CHAR(SYSDATE, 'MM')")
    Set<BigDecimal> listMonthDataIds();

}
