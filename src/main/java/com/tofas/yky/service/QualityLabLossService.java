package com.tofas.yky.service;

import com.tofas.core.common.utility.TfAuthUtility;
import com.tofas.core.restful.model.dto.announcements.TfSessionBasedFileUploader;
import com.tofas.yky.enums.LossState;
import com.tofas.yky.model.additional.IQualityLabTestAddableLoss;
import com.tofas.yky.model.additional.IWorkDurationAddableLoss;
import com.tofas.yky.model.decoratorbases.decorators.VPartDecorator;
import com.tofas.yky.model.decoratorbases.decorators.VSupplierDecorator;
import com.tofas.yky.model.decoratorbases.impl.VPart;
import com.tofas.yky.model.decoratorbases.impl.VSupplier;
import com.tofas.yky.model.dto.QualityLabLossDto;
import com.tofas.yky.model.dto.additional.IQualityLabTestAddableDto;
import com.tofas.yky.model.dto.additional.IWorkDurationAddableLossDto;
import com.tofas.yky.model.losses.LossStateChange;
import com.tofas.yky.model.losses.QualityLabApprover;
import com.tofas.yky.model.losses.qualitylab.QualityLabLoss;
import com.tofas.yky.repositories.QualityLabApproverRepository;
import com.tofas.yky.repositories.QualityLabLossRepository;
import com.tofas.yky.repositories.VPartsRepository;
import com.tofas.yky.repositories.VSupplierRepository;
import com.tofas.yky.service.additional.*;
import com.tofas.yky.service.additional.impl.QualityLabTestAddableLossService;
import com.tofas.yky.service.additional.impl.WorkDurationAddableLossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QualityLabLossService implements IWorkDurationAddableLossService, IQualityLabTestAddableLossService,
        ILossService<QualityLabLoss> {

    private final WorkDurationAddableLossService workDurationAddableLossService;

    private final QualityLabTestAddableLossService qualityLabTestAddableLossService;

    private final VPartsRepository vPartsRepository;

    private final VSupplierRepository vSupplierRepository;

    private final QualityLabLossRepository qualityLabLossRepository;

    private final CommonLossService commonLossService;

    private final QualityLabApproverRepository qualityLabApproverRepository;

    @Autowired
    public QualityLabLossService(WorkDurationAddableLossService workDurationAddableLossService, QualityLabTestAddableLossService qualityLabTestAddableLossService, VPartsRepository vPartsRepository, VSupplierRepository vSupplierRepository, QualityLabLossRepository qualityLabLossRepository, CommonLossService commonLossService, QualityLabApproverRepository qualityLabApproverRepository) {
        this.workDurationAddableLossService = workDurationAddableLossService;
        this.qualityLabTestAddableLossService = qualityLabTestAddableLossService;
        this.vPartsRepository = vPartsRepository;
        this.vSupplierRepository = vSupplierRepository;
        this.qualityLabLossRepository = qualityLabLossRepository;
        this.commonLossService = commonLossService;
        this.qualityLabApproverRepository = qualityLabApproverRepository;
    }


    public Long saveQualityLabLoss(QualityLabLossDto qualityLabLossDto, TfSessionBasedFileUploader tfSessionBasedFileUploader) {
        VPart part = vPartsRepository.findByDisegno(qualityLabLossDto.getDisegno());
        VSupplier supplier = vSupplierRepository.findBySapCode(qualityLabLossDto.getSupplier());
        QualityLabApprover qualityLabApprover = qualityLabApproverRepository.findOne(qualityLabLossDto.getApproverRoleId());

        QualityLabLoss qualityLabLoss = new QualityLabLoss();
        commonLossService.addCommonFields(qualityLabLoss, qualityLabLossDto, tfSessionBasedFileUploader);

        qualityLabLoss.setSupplier(new VSupplierDecorator(qualityLabLossDto.getSupplier(), supplier));
        qualityLabLoss.setPart(new VPartDecorator(qualityLabLossDto.getDisegno(), part));
        // Todo : entity'e requestNo eklendi.
        //qualityLabLoss.setSqpNo(qualityLabLossDto.getSqpNo());
        qualityLabLoss.setRequestNo(qualityLabLossDto.getRequestNo());
        qualityLabLoss.setWillSqpOpen("X");
        //qualityLabLoss.setApproverRole(qualityLabLossDto.getApproverRole());
        qualityLabLoss.setQualityLabApprover(qualityLabApprover);
        addWorkDurations(qualityLabLossDto, qualityLabLoss);
        addQualityLabTests(qualityLabLossDto, qualityLabLoss);

        qualityLabLossRepository.save(qualityLabLoss);

        //todo  düzenleme yapıldı muhammed eren demir
        //commonLossService.lossIsApproved(qualityLabLoss);

        return qualityLabLoss.getId();
    }


    @Override
    public void addQualityLabTests(IQualityLabTestAddableDto lossDto, IQualityLabTestAddableLoss loss) {
        qualityLabTestAddableLossService.addQualityLabTests(lossDto, loss);
    }

    @Override
    public void addWorkDurations(IWorkDurationAddableLossDto lossDto, IWorkDurationAddableLoss loss) {
        workDurationAddableLossService.addWorkDurations(lossDto, loss);
    }

    @Override
    public QualityLabLoss getLoss(Long id) {
        return commonLossService.getLoss(qualityLabLossRepository.findOne(id));
    }

    // TODO : Quality-Lab Onaylama süreci.
    public Boolean approve(long lossId, long sqpNo){
        QualityLabLoss qualityLabLoss = qualityLabLossRepository.findOne(lossId);

        if(!isUserApprover(qualityLabLoss.getQualityLabApprover())) return false;

        qualityLabLoss.setSqpNo(sqpNo);

        for (LossStateChange lossStateChange : qualityLabLoss.getStateChanges()) {
            if(lossStateChange.getLossState() == LossState.APPROVED) {
                return true;
            }
        }

        if(qualityLabLoss.getStateChanges().size() == 1) {
            LossStateChange lossStateChange = new LossStateChange();
            lossStateChange.setLoss(qualityLabLoss);
            lossStateChange.setLossState(LossState.APPROVED);

            qualityLabLoss.getStateChanges().add(lossStateChange);
        }

        qualityLabLossRepository.save(qualityLabLoss);

        commonLossService.lossIsApproved(qualityLabLoss);

        return true;

    }

    // TODO : Quality-Lab SQP NO'SUZ Onaylama süreci.
    public Boolean approve(long lossId){
        QualityLabLoss qualityLabLoss = qualityLabLossRepository.findOne(lossId);

        if(!isUserApprover(qualityLabLoss.getQualityLabApprover())) return false;

        for (LossStateChange lossStateChange : qualityLabLoss.getStateChanges()) {
            if(lossStateChange.getLossState() == LossState.APPROVED) {
                return true;
            }
        }

        if(qualityLabLoss.getStateChanges().size() == 1) {
            LossStateChange lossStateChange = new LossStateChange();
            lossStateChange.setLoss(qualityLabLoss);
            lossStateChange.setLossState(LossState.APPROVED);

            qualityLabLoss.getStateChanges().add(lossStateChange);
        }
        qualityLabLossRepository.save(qualityLabLoss);
        commonLossService.lossIsApproved(qualityLabLoss);
        return true;
    }

    public List<QualityLabApprover> getApproverRoles(){
        return qualityLabApproverRepository.findAll();
    }

    public Boolean isUserApprover(QualityLabApprover qualityLabApprover){

        if(qualityLabApprover == null) return false;

        List<String> roles = TfAuthUtility.getUserRoles();
        //roles.add("TFG_YKY_APPROVAL_QUALITYLAB_SAMPLE");
        for (String role : roles ) {
            if(role.equals(qualityLabApprover.getApproverRole())) return true;
        }

        return false;
    }
}
