package com.tofas.yky.service;

import com.tofas.core.restful.model.dto.announcements.TfSessionBasedFileUploader;
import com.tofas.yky.model.additional.IDiscardedPartAddableLoss;
import com.tofas.yky.model.additional.IOtherCostAddableLoss;
import com.tofas.yky.model.additional.IWorkDurationAddableLoss;
import com.tofas.yky.model.decoratorbases.decorators.VLossCodeDecorator;
import com.tofas.yky.model.decoratorbases.decorators.VPartDecorator;
import com.tofas.yky.model.decoratorbases.decorators.VSupplierDecorator;
import com.tofas.yky.model.decoratorbases.impl.VLossCode;
import com.tofas.yky.model.decoratorbases.impl.VPart;
import com.tofas.yky.model.decoratorbases.impl.VSupplier;
import com.tofas.yky.model.dto.LogisticsLossDto;
import com.tofas.yky.model.dto.additional.IDiscardedPartAddableDto;
import com.tofas.yky.model.dto.additional.IOtherCostAddableLossDto;
import com.tofas.yky.model.dto.additional.IWorkDurationAddableLossDto;
import com.tofas.yky.model.losses.logistics.LogisticsAcceptencePoint;
import com.tofas.yky.model.losses.logistics.LogisticsLoss;
import com.tofas.yky.model.losses.logistics.LogisticsLossType;
import com.tofas.yky.repositories.*;
import com.tofas.yky.service.additional.*;
import com.tofas.yky.service.additional.impl.DiscardedPartAddableLossService;
import com.tofas.yky.service.additional.impl.OtherCostAddableLossService;
import com.tofas.yky.service.additional.impl.WorkDurationAddableLossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogisticsLossService implements IWorkDurationAddableLossService,
        IDiscardedPartAddableLossService, ILossService<LogisticsLoss>, IOtherCostAddableLossService {

    private final VPartsRepository vPartsRepository;

    private final VSupplierRepository vSupplierRepository;

    private final VLossCodeRepository vLossCodeRepository;

    private final LogisticsLossTypeRepository logisticsLossTypeRepository;

    private final LogisticsAcceptencePointRepository logisticsAcceptencePointRepository;

    private final LogisticsLossRepository logisticsLossRepository;

    private final WorkDurationAddableLossService workDurationAddableLossService;

    private final DiscardedPartAddableLossService discardedPartAddableLossService;

    private final CommonLossService commonLossService;

    private final OtherCostAddableLossService otherCostAddableLossService;

    @Autowired
    public LogisticsLossService(VPartsRepository vPartsRepository, VSupplierRepository vSupplierRepository,
                                VLossCodeRepository vLossCodeRepository, LogisticsLossTypeRepository logisticsLossTypeRepository,
                                LogisticsAcceptencePointRepository logisticsAcceptencePointRepository,
                                LogisticsLossRepository logisticsLossRepository,
                                WorkDurationAddableLossService workDurationAddableLossService,
                                DiscardedPartAddableLossService discardedPartAddableLossService,
                                CommonLossService commonLossService, OtherCostAddableLossService otherCostAddableLossService) {
        this.vPartsRepository = vPartsRepository;
        this.vSupplierRepository = vSupplierRepository;
        this.vLossCodeRepository = vLossCodeRepository;
        this.logisticsLossTypeRepository = logisticsLossTypeRepository;
        this.logisticsAcceptencePointRepository = logisticsAcceptencePointRepository;
        this.logisticsLossRepository = logisticsLossRepository;
        this.workDurationAddableLossService = workDurationAddableLossService;
        this.discardedPartAddableLossService = discardedPartAddableLossService;
        this.commonLossService = commonLossService;
        this.otherCostAddableLossService = otherCostAddableLossService;
    }

    public Long saveLogisticsLoss(LogisticsLossDto logisticsLossDto, TfSessionBasedFileUploader tfSessionBasedFileUploader) {
        VPart part = vPartsRepository.findByDisegno(logisticsLossDto.getDisegno());
        VSupplier supplier = vSupplierRepository.findBySapCode(logisticsLossDto.getSupplier());
        VLossCode lossCode = vLossCodeRepository.findOne(logisticsLossDto.getLossCode());
        LogisticsLossType logisticsLossType = logisticsLossTypeRepository.findOne(logisticsLossDto.getLogisticsLossType());
        LogisticsAcceptencePoint logisticsAcceptencePoint = logisticsAcceptencePointRepository.findOne(logisticsLossDto.getLogisticsAcceptencePoint());

        LogisticsLoss logisticsLoss = new LogisticsLoss();
        commonLossService.addCommonFields(logisticsLoss, logisticsLossDto, tfSessionBasedFileUploader);
        logisticsLoss.setSupplier(new VSupplierDecorator(logisticsLossDto.getSupplier(), supplier));
        logisticsLoss.setQty(logisticsLossDto.getQty());
        logisticsLoss.setSqpNo(logisticsLossDto.getQty());
        logisticsLoss.setSqpNo(logisticsLossDto.getSqpNo());
        logisticsLoss.setPart(new VPartDecorator(logisticsLossDto.getDisegno(), part));
        logisticsLoss.setLogisticsLossType(logisticsLossType);
        logisticsLoss.setLogisticsAcceptencePoint(logisticsAcceptencePoint);
        logisticsLoss.setLossCode(new VLossCodeDecorator(logisticsLossDto.getLossCode(), lossCode));

        addWorkDurations(logisticsLossDto, logisticsLoss);
        addDiscardedParts(logisticsLossDto, logisticsLoss);
        addOtherCosts(logisticsLossDto, logisticsLoss);

        logisticsLossRepository.save(logisticsLoss);

        commonLossService.lossIsApproved(logisticsLoss);

        return logisticsLoss.getId();
    }

    @Override
    public void addWorkDurations(IWorkDurationAddableLossDto lossDto, IWorkDurationAddableLoss loss) {
        workDurationAddableLossService.addWorkDurations(lossDto, loss);
    }


    @Override
    public void addDiscardedParts(IDiscardedPartAddableDto dto, IDiscardedPartAddableLoss loss) {
        discardedPartAddableLossService.addDiscardedParts(dto, loss);
    }

    @Override
    public LogisticsLoss getLoss(Long id) {
        return commonLossService.getLoss(logisticsLossRepository.findOne(id));
    }

    @Override
    public void addOtherCosts(IOtherCostAddableLossDto dto, IOtherCostAddableLoss loss) {
        otherCostAddableLossService.addOtherCosts(dto, loss);
    }
}
