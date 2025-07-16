package com.tofas.yky.service.press;
/* t40127 @ 26.05.2016. */

import com.google.common.base.Strings;
import com.tofas.core.common.utility.TfSpecifications;
import com.tofas.yky.enums.LossState;
import com.tofas.yky.enums.PressLossType;
import com.tofas.yky.model.dto.press.report.PressMainReportSearchParameters;
import com.tofas.yky.model.losses.press.PressLoss;
import com.tofas.yky.model.losses.press.VPressReportView;
import com.tofas.yky.model.losses.press.VPressReportView_;
import com.tofas.yky.repositories.press.PressLossRepository;
import com.tofas.yky.repositories.press.VPressReportViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class PressReportService {

    @Autowired
    private PressLossRepository pressLossRepository;

    @Autowired
    private VPressReportViewRepository vPressReportViewRepository;

    public List<PressLoss> getPressLoss(final PressLossType pressLossType, final PressMainReportSearchParameters pressMainReportSearchParameters) {
        TfSpecifications<VPressReportView> reportSearchSpecs = TfSpecifications.getSpecification();

        reportSearchSpecs.and((root, query, cb) -> cb.equal(root.get(VPressReportView_.pressLossType), pressLossType));

        if(pressMainReportSearchParameters.getLossDateStart() != null) {
            reportSearchSpecs.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get(VPressReportView_.lossDate), pressMainReportSearchParameters.getLossDateStart()));
        }

        if(pressMainReportSearchParameters.getLossDateEnd() != null) {
            reportSearchSpecs.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get(VPressReportView_.lossDate), pressMainReportSearchParameters.getLossDateEnd()));
        }

        if(!Strings.isNullOrEmpty(pressMainReportSearchParameters.getDisegno())) {
            reportSearchSpecs.and(
                    (root, query, cb) -> cb.equal(root.get(VPressReportView_.disegno), pressMainReportSearchParameters.getDisegno()));
        }

        if(!Strings.isNullOrEmpty(pressMainReportSearchParameters.getSupplierCode())) {
            reportSearchSpecs.and((root, query, cb) -> cb.equal(root.get(VPressReportView_.supplierCode), pressMainReportSearchParameters.getSupplierCode()));
        }

        if(!Strings.isNullOrEmpty(pressMainReportSearchParameters.getLossIds())) {
            String[] lossIdsStrArr = pressMainReportSearchParameters.getLossIds().split("-");
            final List<Long> lostIds = new ArrayList<>();
            for (String s : lossIdsStrArr) {
                try {
                    lostIds.add(Long.parseLong(s));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            reportSearchSpecs.and((root, query, cb) -> root.get(VPressReportView_.lossId).in(lostIds));
        }

        if(!Strings.isNullOrEmpty(pressMainReportSearchParameters.getLossState())) {
            reportSearchSpecs.and(
                    (root, query, cb) -> cb.equal(root.get(VPressReportView_.lossState), LossState.valueOf(pressMainReportSearchParameters.getLossState())));
        }

        if(!Strings.isNullOrEmpty(pressMainReportSearchParameters.getRollNo())) {
            reportSearchSpecs.and(
                    (root, query, cb) -> cb.equal(root.get(VPressReportView_.rollNo), pressMainReportSearchParameters.getRollNo()));
        }

        List<VPressReportView> pressReportViews = vPressReportViewRepository.findAll(reportSearchSpecs);
        List<Long> lossIds = new ArrayList<>();
        for (VPressReportView pressReportView : pressReportViews) {
            lossIds.add(pressReportView.getLossId());
        }


        return pressLossRepository.findAll(lossIds);
    }

}
