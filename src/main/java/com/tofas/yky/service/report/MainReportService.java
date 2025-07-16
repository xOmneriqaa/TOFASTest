package com.tofas.yky.service.report;
/* T40127 @ 11.11.2015. */

import com.tofas.core.common.utility.TfAuthUtility;
import com.tofas.core.common.utility.TfSpecifications;
import com.tofas.core.common.utility.TfStringUtility;
import com.tofas.yky.components.SupplierUserInfo;
import com.tofas.yky.model.dto.report.MainReportObjectDto;
import com.tofas.yky.model.dto.report.MainReportSearchParamsDto;
import com.tofas.yky.model.report.MainReportObject;
import com.tofas.yky.model.report.MainReportObject_;
import com.tofas.yky.repositories.MainReportObjectRepository;
import com.tofas.yky.repositories.QualityLabLossRepository;
import com.tofas.yky.repositories.WorkTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MainReportService {

    private final MainReportObjectRepository mainReportObjectRepository;

    private final TfStringUtility stringUtility;

    private final WorkTypeRepository workTypeRepository;

    private final SupplierUserInfo supplierUserInfo;

    private final QualityLabLossRepository qualityLabLossRepository;

    @Autowired
    public MainReportService(MainReportObjectRepository mainReportObjectRepository, TfStringUtility stringUtility,
                             WorkTypeRepository workTypeRepository, SupplierUserInfo supplierUserInfo,
                             QualityLabLossRepository qualityLabLossRepository) {
        this.mainReportObjectRepository = mainReportObjectRepository;
        this.stringUtility = stringUtility;
        this.workTypeRepository = workTypeRepository;
        this.supplierUserInfo = supplierUserInfo;
        this.qualityLabLossRepository = qualityLabLossRepository;
    }

    public List<MainReportObjectDto> listMainReport(final MainReportSearchParamsDto searchParams) {
        TfSpecifications<MainReportObject> specs = TfSpecifications.getSpecification();

        if(searchParams.getStateChangeDateStart() != null) {
            specs.and((root, query, cb)
                    -> cb.greaterThanOrEqualTo(root.get(MainReportObject_.lossStateDate), searchParams.getStateChangeDateStart()));
        }

        if(searchParams.getStateChangeDateEnd() != null) {
            specs.and((root, query, cb)
                    -> cb.lessThanOrEqualTo(root.get(MainReportObject_.lossStateDate), searchParams.getStateChangeDateEnd()));
        }

        //test
        if(searchParams.getApprovalDateStart() != null) {
            specs.and((root, query, cb)
                    -> cb.greaterThanOrEqualTo(root.get(MainReportObject_.approvalDate), searchParams.getApprovalDateStart()));
        }

        if(searchParams.getApprovalDateEnd() != null) {
            specs.and((root, query, cb)
                    -> cb.lessThanOrEqualTo(root.get(MainReportObject_.approvalDate), searchParams.getApprovalDateEnd()));
        }
        //test

        if(searchParams.getLossState() != null && searchParams.getLossState().trim().length() > 0) {
            specs.and((root, query, cb) -> cb.equal(root.get(MainReportObject_.lossState), searchParams.getLossState()));
        }

        if(searchParams.getSupplierCode() != null && searchParams.getSupplierCode().trim().length() > 0) {
            specs.and((root, query, cb)
                    -> cb.equal(root.get(MainReportObject_.supplierCode), searchParams.getSupplierCode()));
        }

        if(searchParams.getLossIds() != null && searchParams.getLossIds().trim().length() > 0) {
            String[] lossIdsStr = searchParams.getLossIds().split(",");
            final List<Long> lossIds = new ArrayList<>();
            for(String lossIdStr : lossIdsStr) {
                lossIds.add(stringUtility.parseLong(lossIdStr.trim(), -1L));
            }

            specs.and((root, query, cb) -> root.get(MainReportObject_.id).in(lossIds));
        }

        if(searchParams.getSqpNo() != null && searchParams.getSqpNo().trim().length() > 0) {
            specs.and((root, query, cb) -> cb.equal(root.get(MainReportObject_.sqpNo), searchParams.getSqpNo()));
        }


        if(searchParams.getLossDateStart() != null){
            specs.and(
                    (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(MainReportObject_.lossDate), searchParams.getLossDateStart()));
        }

        if(searchParams.getLossDateEnd() != null) {
            specs.and(
                    (root, query, cb) -> cb.lessThanOrEqualTo(root.get(MainReportObject_.lossDate), searchParams.getLossDateEnd()));
        }

        if(searchParams.getModel() != null && searchParams.getModel().trim().length() > 0) {
            specs.and((root, query, cb) -> cb.equal(root.get(MainReportObject_.modelCode), searchParams.getModel()));
        }

        if(searchParams.getLossType() != null && searchParams.getLossType().trim().length() > 0) {
            specs.and((root, query, cb) -> cb.equal(root.get(MainReportObject_.lossType), searchParams.getLossType()));
        }

        if(searchParams.getDisegno() != null && searchParams.getDisegno().trim().length() > 0) {
            specs.and(
                    (root, query, cb) -> cb.like(root.get(MainReportObject_.disegno), ("%" + searchParams.getDisegno() + "%")));
        }

        if(searchParams.getTut() != null && searchParams.getTut().trim().length() > 0) {
            specs.and(
                    (root, query, cb) -> cb.like(root.get(MainReportObject_.tutCode), ("%" + searchParams.getTut() + "%")));
        }

        if(searchParams.getLossSourceId() != null) {
            specs.and(
                    (root, query, cb) -> cb.equal(root.get(MainReportObject_.lossSourceId), searchParams.getLossSourceId()));
        }

        if(searchParams.getApproverRoleId() != null) {
            specs.and(
                    (root, query, cb) -> cb.equal(root.get(MainReportObject_.approverRoleId), searchParams.getApproverRoleId()));
        }


        //firma kendi verilerini goruyor
        // supplier user spec
        String username = TfAuthUtility.getUsername();
        if(username != null && username.trim().length() > 0 && username.charAt(0) == 'S') {
            specs.and((root, query, cb) -> cb.equal(root.get(MainReportObject_.supplierCode), supplierUserInfo.getSupplier().getSapCode()));
        }

        List<MainReportObject> reportResult =  mainReportObjectRepository.findAll(specs);

        // put default zeros for every work type
        List<String> workTypes = new ArrayList<>(getWorkTypes());

        // generate dtos
        List<MainReportObjectDto> resultDto = new ArrayList<>();
        for (MainReportObject mainReportObject : reportResult) {
            resultDto.add(new MainReportObjectDto(mainReportObject, workTypes));
        }

        return resultDto;
    }

    public Collection<String> getWorkTypes() {
        return workTypeRepository.getActiveWorkTypeNames();
    }
}
