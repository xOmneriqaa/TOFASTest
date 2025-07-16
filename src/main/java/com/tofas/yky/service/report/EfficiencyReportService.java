package com.tofas.yky.service.report;

import com.tofas.core.common.utility.TfSpecifications;
import com.tofas.yky.model.dto.report.EfficiencyReportSearchParamsDto;
import com.tofas.yky.model.report.VEfficiencyReport;
import com.tofas.yky.model.report.VEfficiencyReport_;
import com.tofas.yky.repositories.EfficiencyReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class EfficiencyReportService {
    
    private final EfficiencyReportRepository efficiencyReportRepository;


    @Autowired
    public EfficiencyReportService(EfficiencyReportRepository efficiencyReportRepository) {
        this.efficiencyReportRepository = efficiencyReportRepository;
    }


    public Collection<VEfficiencyReport> listReport(EfficiencyReportSearchParamsDto efficiencyReportSearchParamsDto) {
        TfSpecifications<VEfficiencyReport> reportSpecs = TfSpecifications.getSpecification();

        if(efficiencyReportSearchParamsDto.getLossDateEnd() != null) {
            reportSpecs.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get(VEfficiencyReport_.lossDate), efficiencyReportSearchParamsDto.getLossDateEnd()));
        }

        if(efficiencyReportSearchParamsDto.getLossDateStart() != null) {
            reportSpecs.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get(VEfficiencyReport_.lossDate), efficiencyReportSearchParamsDto.getLossDateStart()));
        }

        return efficiencyReportRepository.findAll(reportSpecs);
    }
}
