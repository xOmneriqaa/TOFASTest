package com.tofas.yky.service;
/* T40127 @ 23.11.2015. */

import com.google.common.eventbus.EventBus;
import com.tofas.yky.events.InvoiceLossEvent;
import com.tofas.yky.model.dto.VSupplierDto;
import com.tofas.yky.model.dto.invoice.SupplierBasedInvoiceModelDto;
import com.tofas.yky.model.losses.OtherCostWoLoss;
import com.tofas.yky.model.losses.directdurations.LossDirectDurationWoLoss;
import com.tofas.yky.model.losses.discards.DiscardedPartWoLoss;
import com.tofas.yky.model.losses.production.duration.LossStdStepDurationWoLoss;
import com.tofas.yky.model.losses.production.duration.LossStepDurationWoLoss;
import com.tofas.yky.model.losses.qualitylab.QualityLabLossTestWoLoss;
import com.tofas.yky.model.losses.views.VInvoiceReadyLoss;
import com.tofas.yky.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.*;

@Service
public class InvoiceReportService {

    @Resource
    VInvoiceReadyLossRepository vInvoiceReadyLossRepository;

    @Resource
    CommonLossRepository commonLossRepository;

    @Autowired TfAppConstantsService tfAppConstantsService;

    @Resource
    QualityLabLossTestWoLossRepository qualityLabLossTestWoLossRepository;

    @Resource
    DiscardedPartWoLossRepository discardedPartWoLossRepository;

    @Resource
    LossDirectDurationWoLossRepository lossDirectDurationWoLossRepository;

    @Resource
    LossStdStepDurationWoLossRepository lossStdStepDurationWoLossRepository;

    @Resource
    LossStepDurationWoLossRepository lossStepDurationWoLossRepository;
    
    @Resource
    OtherCostWoLossRepository otherCostWoLossRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    EventBus eventBus;

    public Collection<SupplierBasedInvoiceModelDto> getInvoiceReadyLosses() {
        List<VInvoiceReadyLoss> invoiceReadyLossesDb = vInvoiceReadyLossRepository.findAll();
        Map<String, SupplierBasedInvoiceModelDto> supplierBasedInvoiceModelDtoMap = new HashMap<>();

        fillInvoiceInfo(supplierBasedInvoiceModelDtoMap, invoiceReadyLossesDb, false);

        BigDecimal invoicableMinimumCost = tfAppConstantsService.getInvoicableMinimumCost();
        List<SupplierBasedInvoiceModelDto> supplierBasedLossesHigherThanMinimum = new ArrayList<>();
        for (SupplierBasedInvoiceModelDto supplierBasedInvoiceModelDto : supplierBasedInvoiceModelDtoMap.values()) {
            if(supplierBasedInvoiceModelDto.getTotalCost().compareTo(invoicableMinimumCost) > 0) {
                supplierBasedLossesHigherThanMinimum.add(supplierBasedInvoiceModelDto);
            }
        }

        supplierBasedLossesHigherThanMinimum.sort(Comparator.comparing(o -> o.getSupplier().getSapCode()));

        return supplierBasedLossesHigherThanMinimum;
    }

    public Collection<SupplierBasedInvoiceModelDto> getInvoiceReadyLosses(Long[] lostIds) {
        Map<String, SupplierBasedInvoiceModelDto> supplierBasedInvoiceModelDtoMap = new HashMap<>();
        List<VInvoiceReadyLoss> invoiceReadyLossesDb = vInvoiceReadyLossRepository.findByIdIn(Arrays.asList(lostIds));
        fillInvoiceInfo(supplierBasedInvoiceModelDtoMap, invoiceReadyLossesDb, true);
        return supplierBasedInvoiceModelDtoMap.values();
    }

    private void fillInvoiceInfo(Map<String, SupplierBasedInvoiceModelDto> supplierBasedInvoiceModelDtoMap,
                                 List<VInvoiceReadyLoss> invoiceReadyLosses, boolean invoice) {

        List<Long> lostIds = new ArrayList<>();
        for (VInvoiceReadyLoss invoiceReadyLoss : invoiceReadyLosses) {
            lostIds.add(invoiceReadyLoss.getId());
        }

        List<QualityLabLossTestWoLoss> qualityLabLossTests = getQualityLabLossTests(lostIds);
        List<DiscardedPartWoLoss> discardedParts = getDiscardedParts(lostIds);
        List<LossDirectDurationWoLoss> lossDirectDurations = getLossDirectDurations(lostIds);
        List<LossStdStepDurationWoLoss> labourLossStdDetails = getLabourLossStdDetails(lostIds);
        List<LossStepDurationWoLoss> labourLossDetails = getLabourLossDetails(lostIds);
        List<OtherCostWoLoss> otherCostWoLosses= getOtherCostWoLossDetails(lostIds);


        for (VInvoiceReadyLoss invoiceReadyLoss : invoiceReadyLosses) {

            SupplierBasedInvoiceModelDto supplierBasedInvoiceModelDto =
                    supplierBasedInvoiceModelDtoMap.get(invoiceReadyLoss.getSupplierCode());

            if(supplierBasedInvoiceModelDto == null) {
                supplierBasedInvoiceModelDto = new SupplierBasedInvoiceModelDto();
                supplierBasedInvoiceModelDto.setSupplier(
                        new VSupplierDto(invoiceReadyLoss.getSupplierCode(), invoiceReadyLoss.getSupplierName()));
                supplierBasedInvoiceModelDtoMap.put(invoiceReadyLoss.getSupplierCode(), supplierBasedInvoiceModelDto);
            }

            supplierBasedInvoiceModelDto.addLoss(invoiceReadyLoss.getId());

            for (QualityLabLossTestWoLoss qualityLabLossTest : qualityLabLossTests) {
                if(qualityLabLossTest.getLossId().equals(invoiceReadyLoss.getId())) {
                    supplierBasedInvoiceModelDto.addQualityLabTestCost(qualityLabLossTest.getTotalCost());
                }
            }

            for (DiscardedPartWoLoss discardedPart : discardedParts) {
                if(discardedPart.getLossId().equals(invoiceReadyLoss.getId())) {
                    supplierBasedInvoiceModelDto.addDiscardedPartCost(discardedPart.getTotalCost());
                }
            }

            for (LossDirectDurationWoLoss lossDirectDuration : lossDirectDurations) {
                if(lossDirectDuration.getLossId().equals(invoiceReadyLoss.getId())) {
                    supplierBasedInvoiceModelDto.addWorkTypeCost(lossDirectDuration.getTotalCost());
                }
            }

            for (LossStdStepDurationWoLoss labourLossStdDetail : labourLossStdDetails) {
                if(labourLossStdDetail.getLossId().equals(invoiceReadyLoss.getId())) {
                    supplierBasedInvoiceModelDto.addWorkTypeCost(labourLossStdDetail.getTotalCost());
                }
            }

            for (LossStepDurationWoLoss labourLossDetail : labourLossDetails) {
                if(labourLossDetail.getLossId().equals(invoiceReadyLoss.getId())) {
                    supplierBasedInvoiceModelDto.addWorkTypeCost(labourLossDetail.getTotalCost());
                }
            }
           for (OtherCostWoLoss otherCostWoLoss : otherCostWoLosses) {
               if(otherCostWoLoss.getLossId().equals(invoiceReadyLoss.getId())) {
                        supplierBasedInvoiceModelDto.addOtherCost(otherCostWoLoss.getTotalCost());
               }                
             
               
            }

            // invoice loss async
            if(invoice) {
                eventBus.post(new InvoiceLossEvent(invoiceReadyLoss.getId()));
            }
        }

    }

    protected List<QualityLabLossTestWoLoss> getQualityLabLossTests(List<Long> lostIds) {
        return getLosses(lostIds, qualityLabLossTestWoLossRepository);
    }

    protected List<DiscardedPartWoLoss> getDiscardedParts(List<Long> lostIds) {
        return getLosses(lostIds, discardedPartWoLossRepository);
    }

    protected List<LossDirectDurationWoLoss> getLossDirectDurations(List<Long> lostIds) {
        return getLosses(lostIds, lossDirectDurationWoLossRepository);
    }

    protected List<LossStdStepDurationWoLoss> getLabourLossStdDetails(List<Long> lostIds) {
        return getLosses(lostIds, lossStdStepDurationWoLossRepository);
    }

    protected List<LossStepDurationWoLoss> getLabourLossDetails(List<Long> lostIds) {
        return getLosses(lostIds, lossStepDurationWoLossRepository);
    }
    
    protected List<OtherCostWoLoss> getOtherCostWoLossDetails(List<Long> lostIds){
    	return getLosses(lostIds, otherCostWoLossRepository);
    }

    protected <T> List<T> getLosses(List<Long> lostIds, ILossInvoiceRepository<T> repository) {
        List<T> objects = new ArrayList<>();
        int startIndex = 0;
        int window = 900;

        while(startIndex <= lostIds.size()) {
            int endingIndex = startIndex + window;
            endingIndex = Math.min(endingIndex, lostIds.size());

            List<Long> chunkedIds = lostIds.subList(startIndex, endingIndex);
            startIndex += window;
            objects.addAll(repository.findByLossIdIn(chunkedIds));
        }

        return objects;
    }

}
