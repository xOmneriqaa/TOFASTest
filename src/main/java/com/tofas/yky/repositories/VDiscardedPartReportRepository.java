package com.tofas.yky.repositories;

import com.tofas.core.common.utility.TfSpecifications;
import com.tofas.yky.model.dto.report.DiscardPartReportDto;
import com.tofas.yky.model.report.VDiscardedPartReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.stream.Collectors;


public interface VDiscardedPartReportRepository extends JpaRepository<VDiscardedPartReport, Long>,
    JpaSpecificationExecutor<VDiscardedPartReport> {

    default List<DiscardPartReportDto> findAllAndMapToDto(TfSpecifications<VDiscardedPartReport> discardedPartReportSpecs) {
        return findAll(discardedPartReportSpecs)
                .stream()
                .map(DiscardPartReportDto::new)
                .collect(Collectors.toList());
    }
}
