package com.tofas.yky.service.report;

import com.tofas.core.common.utility.TfSpecifications;
import com.tofas.yky.model.dto.report.DiscardPartReportDto;
import com.tofas.yky.model.dto.report.DiscardedPartReportSearchParamsDto;
import com.tofas.yky.model.report.VDiscardedPartReport;
import com.tofas.yky.model.report.VDiscardedPartReport_;
import com.tofas.yky.repositories.VDiscardedPartReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscardedPartReportService {

    private final VDiscardedPartReportRepository vDiscardedPartReportRepository;

    @Autowired
    public DiscardedPartReportService(VDiscardedPartReportRepository vDiscardedPartReportRepository) {
        this.vDiscardedPartReportRepository = vDiscardedPartReportRepository;
    }

    public List<DiscardPartReportDto> listReport(DiscardedPartReportSearchParamsDto discardedPartReportSearchParams) {
        TfSpecifications<VDiscardedPartReport> discardedPartReportSpecs = TfSpecifications.getSpecification();

        if(discardedPartReportSearchParams.getLossDateStart() != null) {
            discardedPartReportSpecs.and((root, query, cb) -> cb
                    .greaterThanOrEqualTo(
                            root.get(VDiscardedPartReport_.lossDate),
                            discardedPartReportSearchParams.getLossDateStart()));
        }

        if(discardedPartReportSearchParams.getLossDateEnd() != null) {
            discardedPartReportSpecs.and((root, query, cb) -> cb
                    .lessThanOrEqualTo(
                            root.get(VDiscardedPartReport_.lossDate),
                            discardedPartReportSearchParams.getLossDateEnd()));
        }

        if(discardedPartReportSearchParams.getLossInsDateStart() != null) {
            discardedPartReportSpecs.and((root, query, cb) -> cb
                    .greaterThanOrEqualTo(
                            root.get(VDiscardedPartReport_.lossInsDate),
                            discardedPartReportSearchParams.getLossInsDateStart()));
        }

        if(discardedPartReportSearchParams.getLossInsDateEnd() != null) {
            discardedPartReportSpecs.and((root, query, cb) -> cb
                    .lessThanOrEqualTo(
                            root.get(VDiscardedPartReport_.lossInsDate),
                            discardedPartReportSearchParams.getLossInsDateEnd()));
        }

        if(discardedPartReportSearchParams.getLossState() != null) {
            discardedPartReportSpecs.and((root, query, cb) -> cb
                    .equal(
                            root.get(VDiscardedPartReport_.lossState),
                            discardedPartReportSearchParams.getLossState()));
        }

        if(discardedPartReportSearchParams.getLossType() != null) {
            discardedPartReportSpecs.and((root, query, cb) -> cb
                .equal(
                        root.get(VDiscardedPartReport_.lossType),
                        discardedPartReportSearchParams.getLossType()));
        }

        if(discardedPartReportSearchParams.getProductionSubLossType() != null) {
            discardedPartReportSpecs.and((root, query, cb) -> cb
                    .equal(
                            root.get(VDiscardedPartReport_.productionSubLossType),
                            discardedPartReportSearchParams.getProductionSubLossType()));
        }
        
        if(discardedPartReportSearchParams.getLossDate() != null) {
            discardedPartReportSpecs.and((root, query, cb) -> cb
                    .equal(
                            root.get(VDiscardedPartReport_.lossDate),
                            discardedPartReportSearchParams.getLossDate()));
        }
        
        if(discardedPartReportSearchParams.getLossInsDate() != null) {
            discardedPartReportSpecs.and((root, query, cb) -> cb
                    .greaterThanOrEqualTo(
                            root.get(VDiscardedPartReport_.lossInsDate),
                            discardedPartReportSearchParams.getLossInsDate()));
        }



        return vDiscardedPartReportRepository.findAllAndMapToDto(discardedPartReportSpecs);
    }

}
