package com.tofas.yky.service.report;
/* T40127 @ 26.11.2015. */

import com.tofas.yky.enums.dashboard.DashboardCostType;
import com.tofas.yky.enums.dashboard.DashboardParamType;
import com.tofas.yky.enums.dashboard.DashboardPeriodType;
import com.tofas.yky.model.dashboard.DashboardDataCached;
import com.tofas.yky.model.dashboard.VDashboardData;
import com.tofas.yky.model.dto.dashboard.DashboardTwoLevelDataDto;
import com.tofas.yky.model.dto.report.DurationDetailDto;
import com.tofas.yky.repositories.VDashboardDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
public class DashboardService {

    @Resource
    private VDashboardDataRepository vDashboardDataRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private final Collector<DashboardTwoLevelDataDto, ?, Map<String, Map<String, BigDecimal>>> threeLevelDataMapCollector =
            groupingBy(DashboardTwoLevelDataDto::getLevel1,
                    groupingBy(DashboardTwoLevelDataDto::getLevel2,
                            mapping(DashboardTwoLevelDataDto::getTotal,
                                    reducing(BigDecimal.ZERO, BigDecimal::add))));

    @Transactional
    public void generateCachedData() {
        clearCachedData();

        Map<DashboardPeriodType, Map<DashboardParamType, Map<String, Map<String, BigDecimal>>>> allData = new HashMap<>();

        Set<VDashboardData> yearData = vDashboardDataRepository.listYearlyData();
        Set<VDashboardData> quarterData = vDashboardDataRepository.listQuarterData();
        Set<VDashboardData> monthData = vDashboardDataRepository.listMonthData();


        allData.put(DashboardPeriodType.YEAR, getGeneratedCostData(yearData));
        allData.put(DashboardPeriodType.QUARTER, getGeneratedCostData(quarterData));
        allData.put(DashboardPeriodType.MONTH, getGeneratedCostData(monthData));

        allData.put(DashboardPeriodType.YEAR_DURATION, getGeneratedDurationData(yearData));
        allData.put(DashboardPeriodType.QUARTER_DURATION, getGeneratedDurationData(quarterData));
        allData.put(DashboardPeriodType.MONTH_DURATION, getGeneratedDurationData(monthData));

        saveData(allData);
    }

    private Map<DashboardParamType, Map<String, Map<String, BigDecimal>>> getGeneratedCostData(Set<VDashboardData> data) {
        Map<String, Map<String, BigDecimal>> supplierBasedCachedData = data.parallelStream()
                .filter(l -> l.getSupplierInfoObject().isValid())
                .flatMap(l -> DashboardCostType.getAllCostType().stream()
                        .map(type -> new DashboardTwoLevelDataDto(l.getSupplierInfoObject().getSupplierType().toString(),
                                type.toString(),
                                l.getCostOf(type))))
                .collect(threeLevelDataMapCollector);


        Map<String, Map<String, BigDecimal>> lossTypeBasedCachedData = data.parallelStream()
                .map(l -> new DashboardTwoLevelDataDto(l.getTotalLossType(), l.getTotalLossState(), l.getTotalCost()))
                .collect(threeLevelDataMapCollector);

        Map<String, Map<String, BigDecimal>> lossStateBasedCachedData = data.parallelStream()
                .map(l -> new DashboardTwoLevelDataDto("0", l.getTotalLossState(), l.getTotalCost()))
                .collect(threeLevelDataMapCollector);

        Map<DashboardParamType, Map<String, Map<String, BigDecimal>>> totalResult = new HashMap<>();
        totalResult.put(DashboardParamType.SUPPLIER_BASED, supplierBasedCachedData);
        totalResult.put(DashboardParamType.LOSS_BASED, lossTypeBasedCachedData);
        totalResult.put(DashboardParamType.STATUS_BASED, lossStateBasedCachedData);

        return totalResult;
    }



    private Map<DashboardParamType, Map<String, Map<String, BigDecimal>>> getGeneratedDurationData(Set<VDashboardData> data) {
        Set<String> allDurationTypes = data.stream()
                .flatMap(dd -> dd.getDurationDetails().stream())
                .map(DurationDetailDto::getWorkTypeName)
                .distinct()
                .collect(Collectors.toSet());

        Map<String, Map<String, BigDecimal>> supplierBasedCachedData = data.parallelStream()
                .filter(l -> l.getSupplierInfoObject().isValid())
                .flatMap(l -> allDurationTypes.stream()
                        .map(type -> new DashboardTwoLevelDataDto(l.getSupplierInfoObject().getSupplierType().toString(),
                                type, l.getDurationOf(type))))
                .collect(threeLevelDataMapCollector);


        Map<String, Map<String, BigDecimal>> lossTypeBasedCachedData = data.parallelStream()
                .map(l -> new DashboardTwoLevelDataDto(l.getTotalLossType(), l.getTotalLossState(), l.getTotalDuration()))
                .collect(threeLevelDataMapCollector);

        Map<String, Map<String, BigDecimal>> lossStateBasedCachedData = data.parallelStream()
                .flatMap(l -> allDurationTypes.stream()
                        .map(type -> new DashboardTwoLevelDataDto(type, l.getTotalLossState(), l.getDurationOf(type))))
                .collect(threeLevelDataMapCollector);

        Map<DashboardParamType, Map<String, Map<String, BigDecimal>>> totalResult = new HashMap<>();
        totalResult.put(DashboardParamType.SUPPLIER_BASED, supplierBasedCachedData);
        totalResult.put(DashboardParamType.LOSS_BASED, lossTypeBasedCachedData);
        totalResult.put(DashboardParamType.STATUS_BASED, lossStateBasedCachedData);

        return totalResult;
    }

    private void saveData(Map<DashboardPeriodType, Map<DashboardParamType, Map<String, Map<String, BigDecimal>>>> data) {
        List<DashboardDataCached> dashboardDataCachedList = new ArrayList<>();

        for (DashboardPeriodType dashboardPeriodType : data.keySet()) {
            for (DashboardParamType dashboardParamType : data.get(dashboardPeriodType)
                    .keySet()) {
                for (String mainParam : data.get(dashboardPeriodType)
                        .get(dashboardParamType)
                        .keySet()) {
                    for (String s : data.get(dashboardPeriodType)
                            .get(dashboardParamType)
                            .get(mainParam)
                            .keySet()) {
                        DashboardDataCached cachedData = new DashboardDataCached();
                        cachedData.setPeriodType(dashboardPeriodType);
                        cachedData.setParamType(dashboardParamType);
                        cachedData.setParamName(mainParam);
                        cachedData.setParamSubName(s);
                        cachedData.setParamValue(data.get(dashboardPeriodType)
                                .get(dashboardParamType)
                                .get(mainParam)
                                .get(s));

                        dashboardDataCachedList.add(cachedData);
                    }
                }
            }
        }

        for (DashboardDataCached dashboardDataCached : dashboardDataCachedList) {
            entityManager.persist(dashboardDataCached);
        }
    }

    public List<DashboardDataCached> getData(String dashboardPeriodType, String dashboardParamType) {
        DashboardPeriodType periodType = DashboardPeriodType.valueOf(dashboardPeriodType);
        DashboardParamType paramType = DashboardParamType.valueOf(dashboardParamType);

        return entityManager
                .createQuery(
                        "Select d from DashboardDataCached d where d.periodType = :periodType and d.paramType = :paramType")
                .setParameter("periodType", periodType)
                .setParameter("paramType", paramType)
                .getResultList();
    }

    private void clearCachedData() {
        entityManager.createNativeQuery("DELETE YKY_DASHBOARD_DATA_CACHED")
                .executeUpdate();
    }

}
