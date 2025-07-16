package com.tofas.yky.service;
/* T40127 @ 12.11.2015. */

import com.tofas.core.common.utility.TfSpecifications;
import com.tofas.yky.model.dto.report.SupplierBasedReportObjectDto;
import com.tofas.yky.model.dto.report.SupplierBasedReportSearchParams;
import com.tofas.yky.model.report.MainReportObject;
import com.tofas.yky.model.report.MainReportObject_;
import com.tofas.yky.repositories.MainReportObjectRepository;
import com.tofas.yky.repositories.WorkTypeRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupplierBasedReportService {

    private @Resource
    MainReportObjectRepository mainReportObjectRepository;


    private @PersistenceContext
    EntityManager entityManager;

    private @Resource
    WorkTypeRepository workTypeRepository;

    public Collection<SupplierBasedReportObjectDto> getReport(SupplierBasedReportSearchParams supplierBasedReportSearchParams) {
        List<String> activeWorkTypes = workTypeRepository.getActiveWorkTypeNames();
        List<MainReportObject> reportData = generateReport(supplierBasedReportSearchParams);

        Map<String, SupplierBasedReportObjectDto> convertedResultMap = new HashMap<>();
        for (MainReportObject mainReportObject : reportData) {
            if(convertedResultMap.containsKey(mainReportObject.getSupplierCode())) {
                convertedResultMap.get(mainReportObject.getSupplierCode()).add(mainReportObject);
            } else {
                SupplierBasedReportObjectDto result = new SupplierBasedReportObjectDto(mainReportObject, activeWorkTypes);
                result.add(mainReportObject);
                convertedResultMap.put(mainReportObject.getSupplierCode(), result);
            }
        }


        return convertedResultMap.values();
    }

    private List<MainReportObject> generateReport(final SupplierBasedReportSearchParams supplierBasedReportSearchParams) {
        TfSpecifications<MainReportObject> specs = TfSpecifications.getSpecification();

        if(supplierBasedReportSearchParams.getLossDateStart() != null) {
            specs.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get(MainReportObject_.lossDate), supplierBasedReportSearchParams.getLossDateStart()));
        }

        if(supplierBasedReportSearchParams.getLossDateEnd() != null) {
            specs.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get(MainReportObject_.lossDate), supplierBasedReportSearchParams.getLossDateEnd()));
        }

        /*if(supplierBasedReportSearchParams.getSupplierTypes() != null && supplierBasedReportSearchParams.getSupplierTypes().size() > 0) {

            specs.and(new Specification<MainReportObject>() {
                @Override
                public Predicate toPredicate(Root<MainReportObject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Subquery<VSupplier> subQuery = query.subquery(VSupplier.class);
                    Root<VSupplier> subQueryRoot = subQuery.from(VSupplier.class);
                    subQuery.select(subQueryRoot.<VSupplier>get("sapCode"));

                    if(supplierBasedReportSearchParams.getSupplierTypes().contains(SupplierType.EXTRA_SERIE)) {
                        subQuery.where(cb.equal(subQueryRoot.get(VSupplier_.extraSerieFlag), "N"));
                    }

                    if(supplierBasedReportSearchParams.getSupplierTypes().contains(SupplierType.FIAT_POLO)) {
                        subQuery.where(cb.isNotNull(subQueryRoot.get(VSupplier_.poloFlag)));
                    }

                    return root.get(MainReportObject_.supplierCode).in(subQuery);
                }
            });

        }
        */

        return mainReportObjectRepository.findAll(specs);
    }

}
