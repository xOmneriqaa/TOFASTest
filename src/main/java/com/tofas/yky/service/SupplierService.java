package com.tofas.yky.service;
/* T40127 @ 18.10.2015. */

import com.google.common.eventbus.EventBus;
import com.tofas.yky.enums.LossState;
import com.tofas.yky.enums.LossType;
import com.tofas.yky.events.LossIsBeenObjectedEvent;
import com.tofas.yky.model.decoratorbases.impl.VSupplier;
import com.tofas.yky.model.dto.LossObjectionDto;
import com.tofas.yky.model.dto.view.LogisticsLossViewDto;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.model.losses.LossObjection;
import com.tofas.yky.model.losses.ObjectionType;
import com.tofas.yky.model.losses.logistics.LogisticsLoss;
import com.tofas.yky.model.losses.views.VSupplierBasedLoss;
import com.tofas.yky.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class SupplierService {

    @Resource private LogisticsLossRepository logisticsLossRepository;
    @Resource private ProductionLossRepository productionLossRepository;
    @Resource private QualityLossRepository qualityLossRepository;
    @Resource private QualityLabLossRepository qualityLabLossRepository;

    @Resource private ObjectionTypeRepository objectionTypeRepository;

    @Resource private LossObjectionRepository lossObjectionRepository;

    @Autowired private EventBus eventBus;

    @Resource VSupplierBasedLossRepository vSupplierBasedLossRepository;

    @Resource CommonLossRepository commonLossRepository;

    private final List<LossState> notInvoicedNotCanceledState = Arrays.asList(LossState.INVOICED, LossState.CANCELED);
    private final List<LossState> approvedState = Arrays.asList(LossState.APPROVED);

    private String getSupplierSapCode(VSupplier supplier) {
        return supplier == null ? "" : supplier.getSapCode();
    }

    public List<VSupplierBasedLoss> listNewOpenedLogisticsLosses(VSupplier supplier) {
        return vSupplierBasedLossRepository.findBySupplierCodeAndLossType(supplier.getSapCode(), LossType.LOGISTICS);
    }

    public List<VSupplierBasedLoss> listNewOpenedProductionLosses(VSupplier supplier) {
        return vSupplierBasedLossRepository.findBySupplierCodeAndLossType(supplier.getSapCode(), LossType.PRODUCTION);
    }

    public List<VSupplierBasedLoss> listNewOpenQualityLosses(VSupplier supplier) {
        return vSupplierBasedLossRepository.findBySupplierCodeAndLossType(supplier.getSapCode(), LossType.QUALITY);
    }

    public List<VSupplierBasedLoss> listNewOpenQualityLabLosses(VSupplier supplier) {
        return vSupplierBasedLossRepository.findBySupplierCodeAndLossType(supplier.getSapCode(), LossType.QUALITY_LAB);
    }


    public LogisticsLossViewDto getLogisticsLoss(Long id) {
        LogisticsLoss loss = logisticsLossRepository.findOne(id);
        return loss == null ? null : new LogisticsLossViewDto(loss);
    }

    public boolean makeObjection(LossObjectionDto dto) {
        Loss loss = commonLossRepository.getLoss(dto.getLossId());

        if(loss != null) {
            if(loss.getObjections().size() == 0 && loss.getCurrentState() == LossState.APPROVED) {
                ObjectionType type = objectionTypeRepository.findOne(dto.getObjectionTypeId());

                LossObjection objection = new LossObjection();
                objection.setLoss(loss);
                objection.setObjectionType(type);
                objection.setObjectionDescription(dto.getObjectionMessage());

                lossObjectionRepository.save(objection);
                loss.getObjections().add(objection);
            }

            eventBus.post(new LossIsBeenObjectedEvent(loss));
        }

        return true;
    }


}
