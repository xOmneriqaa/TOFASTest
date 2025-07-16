package com.tofas.yky.service;

import com.tofas.core.restful.model.dto.announcements.TfSessionBasedFileUploader;
import com.tofas.yky.model.Model;
import com.tofas.yky.model.additional.IDiscardedPartAddableLoss;
import com.tofas.yky.model.additional.IOtherCostAddableLoss;
import com.tofas.yky.model.additional.IWorkDurationAddableLoss;
import com.tofas.yky.model.decoratorbases.decorators.VLossCodeDecorator;
import com.tofas.yky.model.decoratorbases.decorators.VPartDecorator;
import com.tofas.yky.model.decoratorbases.decorators.VSupplierDecorator;
import com.tofas.yky.model.decoratorbases.impl.VLossCode;
import com.tofas.yky.model.decoratorbases.impl.VPart;
import com.tofas.yky.model.decoratorbases.impl.VSupplier;
import com.tofas.yky.model.dto.QualityLossDto;
import com.tofas.yky.model.dto.additional.IDiscardedPartAddableDto;
import com.tofas.yky.model.dto.additional.IOtherCostAddableLossDto;
import com.tofas.yky.model.dto.additional.IWorkDurationAddableLossDto;
import com.tofas.yky.model.losses.quality.QualityLoss;
import com.tofas.yky.model.losses.quality.QualityLossTable;
import com.tofas.yky.repositories.*;
import com.tofas.yky.service.additional.*;
import com.tofas.yky.service.additional.impl.DiscardedPartAddableLossService;
import com.tofas.yky.service.additional.impl.OtherCostAddableLossService;
import com.tofas.yky.service.additional.impl.WorkDurationAddableLossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QualityLossService implements IWorkDurationAddableLossService, IDiscardedPartAddableLossService,
        ILossService<QualityLoss>, IOtherCostAddableLossService {

    private final QualityLossRepository qualityLossRepository;

    private final QualityLossTableRepository qualityLossTableRepository;

    private final VSupplierRepository vSupplierRepository;

    private final VPartsRepository vPartsRepository;

    private final ModelRepository modelRepository;

    private final VLossCodeRepository vLossCodeRepository;

    private final WorkDurationAddableLossService workDurationAddableLossService;

    private final DiscardedPartAddableLossService discardedPartAddableLossService;

    private final CommonLossService commonLossService;

    private final OtherCostAddableLossService otherCostAddableLossService;

    @Autowired
    public QualityLossService(QualityLossRepository qualityLossRepository, QualityLossTableRepository qualityLossTableRepository,
                              VSupplierRepository vSupplierRepository, VPartsRepository vPartsRepository,
                              ModelRepository modelRepository, VLossCodeRepository vLossCodeRepository,
                              WorkDurationAddableLossService workDurationAddableLossService,
                              DiscardedPartAddableLossService discardedPartAddableLossService,
                              CommonLossService commonLossService, OtherCostAddableLossService otherCostAddableLossService) {
        this.qualityLossRepository = qualityLossRepository;
        this.qualityLossTableRepository = qualityLossTableRepository;
        this.vSupplierRepository = vSupplierRepository;
        this.vPartsRepository = vPartsRepository;
        this.modelRepository = modelRepository;
        this.vLossCodeRepository = vLossCodeRepository;
        this.workDurationAddableLossService = workDurationAddableLossService;
        this.discardedPartAddableLossService = discardedPartAddableLossService;
        this.commonLossService = commonLossService;
        this.otherCostAddableLossService = otherCostAddableLossService;
    }

    public Long saveQualityLoss(QualityLossDto qualityLossDto, TfSessionBasedFileUploader tfSessionBasedFileUploader) {
        Model model = modelRepository.findOne(qualityLossDto.getModel());
        VPart part = vPartsRepository.findByDisegno(qualityLossDto.getDisegno());
        VSupplier supplier = vSupplierRepository.findBySapCode(qualityLossDto.getSupplier());
        QualityLossTable qualityLossTable = qualityLossTableRepository.findOne(qualityLossDto.getQualityLossTable());
        VLossCode lossCode = vLossCodeRepository.findOne(qualityLossDto.getLossCode());

        QualityLoss qualityLoss = new QualityLoss();
        commonLossService.addCommonFields(qualityLoss, qualityLossDto, tfSessionBasedFileUploader);

        qualityLoss.setModel(model);
        qualityLoss.setPart(new VPartDecorator(qualityLossDto.getDisegno(), part));
        qualityLoss.setSupplier(new VSupplierDecorator(qualityLossDto.getSupplier(), supplier));
        qualityLoss.setQualityLossTable(qualityLossTable);
        qualityLoss.setSqpNo(qualityLossDto.getSqpNo());
        qualityLoss.setLossCode(new VLossCodeDecorator(qualityLossDto.getLossCode(), lossCode));
        qualityLoss.setQualityTraceId(qualityLossDto.getQualityTraceId());
        qualityLoss.setQty(qualityLossDto.getQty());


        addWorkDurations(qualityLossDto, qualityLoss);
        addDiscardedParts(qualityLossDto, qualityLoss);
        addOtherCosts(qualityLossDto, qualityLoss);

        qualityLossRepository.save(qualityLoss);
        commonLossService.lossIsApproved(qualityLoss);

        return qualityLoss.getId();
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
    public QualityLoss getLoss(Long id) {
        return commonLossService.getLoss(qualityLossRepository.findOne(id));
    }

    @Override
    public void addOtherCosts(IOtherCostAddableLossDto dto, IOtherCostAddableLoss loss) {
        otherCostAddableLossService.addOtherCosts(dto, loss);
    }
}
