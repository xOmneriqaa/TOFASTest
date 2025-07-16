package com.tofas.yky.service;


import com.tofas.core.common.utility.TfAuthUtility;
import com.tofas.core.restful.model.dto.announcements.TfSessionBasedFileUploader;
import com.tofas.yky.enums.LossState;
import com.tofas.yky.enums.ProductionSubLossType;
import com.tofas.yky.model.Model;
import com.tofas.yky.model.additional.IDiscardedPartAddableLoss;
import com.tofas.yky.model.additional.IOtherCostAddableLoss;
import com.tofas.yky.model.decoratorbases.decorators.VLossCodeDecorator;
import com.tofas.yky.model.decoratorbases.decorators.VPartDecorator;
import com.tofas.yky.model.decoratorbases.decorators.VSupplierDecorator;
import com.tofas.yky.model.decoratorbases.decorators.VTutDecorator;
import com.tofas.yky.model.decoratorbases.impl.VLossCode;
import com.tofas.yky.model.decoratorbases.impl.VPart;
import com.tofas.yky.model.decoratorbases.impl.VSupplier;
import com.tofas.yky.model.decoratorbases.impl.VTut;
import com.tofas.yky.model.dto.ProductionLossCompletionDto;
import com.tofas.yky.model.dto.ProductionLossDto;
import com.tofas.yky.model.dto.StepForLossDto;
import com.tofas.yky.model.dto.additional.IDiscardedPartAddableDto;
import com.tofas.yky.model.dto.additional.IOtherCostAddableLossDto;
import com.tofas.yky.model.losses.LossSource;
import com.tofas.yky.model.losses.LossStateChange;
import com.tofas.yky.model.losses.WorkType;
import com.tofas.yky.model.losses.production.LossCompletion;
import com.tofas.yky.model.losses.production.ProductionLoss;
import com.tofas.yky.model.losses.production.duration.*;
import com.tofas.yky.model.losses.views.VInCompletedLosses;
import com.tofas.yky.model.losses.views.VUnApprovedLosses;
import com.tofas.yky.repositories.*;
import com.tofas.yky.service.additional.IOtherCostAddableLossService;
import com.tofas.yky.service.additional.impl.DiscardedPartAddableLossService;
import com.tofas.yky.service.additional.IDiscardedPartAddableLossService;
import com.tofas.yky.service.additional.ILossService;
import com.tofas.yky.service.additional.impl.OtherCostAddableLossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductionLossService implements IDiscardedPartAddableLossService, ILossService<ProductionLoss>,
    IOtherCostAddableLossService {

    private final VPartsRepository vPartsRepository;

    private final VSupplierRepository vSupplierRepository;

    private final VLossCodeRepository vLossCodeRepository;

    private final LossSourceRepository lossSourceRepository;

    private final VTutRepository vTutRepository;

    private final ProductionLossRepository productionLossRepository;

    private final ModelRepository modelRepository;

    private final StepRepository stepRepository;

    private final StandartStepRepository standartStepRepository;

    private final DurationRepository durationRepository;

    private final WorkTypeRepository workTypeRepository;

    private final VInCompletedLossesRepository vInCompletedLossesRepository;

    private final VUnApprovedLossesRepository vUnApprovedLossesRepository;

    private final TfAppConstantsService tfAppConstantsService;

    private final DiscardedPartAddableLossService discardedPartAddableLossService;

    private final CommonLossService commonLossService;

    private final OtherCostAddableLossService otherCostAddableLossService;

    @Autowired
    public ProductionLossService(VSupplierRepository vSupplierRepository, VPartsRepository vPartsRepository,
                                 VLossCodeRepository vLossCodeRepository, LossSourceRepository lossSourceRepository,
                                 VInCompletedLossesRepository vInCompletedLossesRepository, VTutRepository vTutRepository,
                                 ProductionLossRepository productionLossRepository, ModelRepository modelRepository,
                                 StepRepository stepRepository, TfAppConstantsService tfAppConstantsService,
                                 StandartStepRepository standartStepRepository,
                                 DiscardedPartAddableLossService discardedPartAddableLossService,
                                 VUnApprovedLossesRepository vUnApprovedLossesRepository,
                                 DurationRepository durationRepository, WorkTypeRepository workTypeRepository,
                                 CommonLossService commonLossService, OtherCostAddableLossService otherCostAddableLossService) {
        this.vSupplierRepository = vSupplierRepository;
        this.vPartsRepository = vPartsRepository;
        this.vLossCodeRepository = vLossCodeRepository;
        this.lossSourceRepository = lossSourceRepository;
        this.vInCompletedLossesRepository = vInCompletedLossesRepository;
        this.vTutRepository = vTutRepository;
        this.productionLossRepository = productionLossRepository;
        this.modelRepository = modelRepository;
        this.stepRepository = stepRepository;
        this.tfAppConstantsService = tfAppConstantsService;
        this.standartStepRepository = standartStepRepository;
        this.discardedPartAddableLossService = discardedPartAddableLossService;
        this.vUnApprovedLossesRepository = vUnApprovedLossesRepository;
        this.durationRepository = durationRepository;
        this.workTypeRepository = workTypeRepository;
        this.commonLossService = commonLossService;
        this.otherCostAddableLossService = otherCostAddableLossService;
    }

    public Long saveProductionLoss(ProductionLossDto productionLossDto, TfSessionBasedFileUploader tfSessionBasedFileUploader) {
        VPart part = vPartsRepository.findByDisegno(productionLossDto.getDisegno());
        LossSource lossSource = lossSourceRepository.findOne(productionLossDto.getLossSource());
        VTut tut = vTutRepository.findByOrgMmm(productionLossDto.getTut());
        Set<String> effectedTuts = new HashSet<>(productionLossDto.getEffectedTuts());
        Model model = modelRepository.findOne(productionLossDto.getModel());

        // effected tuts must not contain the selected tut
        effectedTuts.remove(tut.getOrgMmm());

        double blueCollarWage = getBlueCollarWage();

        ProductionLoss productionLoss = new ProductionLoss();
        commonLossService.addCommonFields(productionLoss, productionLossDto, tfSessionBasedFileUploader);

        productionLoss.setLossSource(lossSource);
        productionLoss.setPart(new VPartDecorator(productionLossDto.getDisegno(), part));
        productionLoss.setQty(productionLossDto.getQty());
        productionLoss.setQualityTraceId(productionLossDto.getQualityTraceId());
        productionLoss.setShiftCode(productionLossDto.getShiftCode());
        productionLoss.setSqpNo(productionLossDto.getSqpNo());
        productionLoss.setSubType(ProductionSubLossType.valueOf(productionLossDto.getLossType()));
        productionLoss.setTut(new VTutDecorator(productionLossDto.getTut(), tut));
        productionLoss.setEffectedTuts(effectedTuts);
        productionLoss.setModel(model);

        Set<LossStdStepDuration> stdSteps = new HashSet<>();
        Set<LossStepDuration> steps = new HashSet<>();

        for(StepForLossDto iStep : productionLossDto.getSteps()) {
            if(iStep.isStandartStep()) {
                StandartStep standartStep = standartStepRepository.findOne(iStep.getStepId());

                LossStdStepDuration stdStepDuration = new LossStdStepDuration();
                stdStepDuration.setLoss(productionLoss);
                stdStepDuration.setQty(iStep.getQty());
                stdStepDuration.setStdStep(standartStep);
                stdStepDuration.setStepDurationCached(standartStep.getDuration());
                stdStepDuration.setStepNameCached(standartStep.getName());
                stdStepDuration.setBlueCollarWageCached(new BigDecimal(blueCollarWage));

                stdSteps.add(stdStepDuration);
            } else {
                Duration duration = durationRepository.findOne(iStep.getDurationId());

                LossStepDuration stepDuration = new LossStepDuration();
                stepDuration.setDuration(duration);
                stepDuration.setLoss(productionLoss);
                stepDuration.setQty(iStep.getQty());

                boolean found = false;
                for(StepDuration stepDurationDef : duration.getStepDurations()) {
                    if(Objects.equals(stepDurationDef.getStepDurationId().getStep().getId(), iStep.getStepId())) {
                        stepDuration.setStep(stepDurationDef.getStepDurationId().getStep());
                        stepDuration.setStepDurationCached(stepDurationDef.getDuration());
                        stepDuration.setStepNameCached(stepDurationDef.getStepDurationId().getStep().getName());
                        stepDuration.setBlueCollarWageCached(new BigDecimal(blueCollarWage));

                        found = true;
                        break;
                    }
                }

                if(found) {
                    steps.add(stepDuration);
                } else {
                    throw new RuntimeException("Referenced step can not be found in steps of duration");
                }
            }
        }

        productionLoss.setStandartDurations(stdSteps);
        productionLoss.setStepDurations(steps);

        addDiscardedParts(productionLossDto, productionLoss);
        addOtherCosts(productionLossDto, productionLoss);

        productionLossRepository.save(productionLoss);

        return productionLoss.getId();
    }

    public List<StepForLossDto> getRelatedStepNames(String productionLossType, String model, String disegno, String tut) {
        List<StepForLossDto> allSteps = new ArrayList<>();

        List<Step> steps = stepRepository.findByProductionSubLossTypesIn(new ProductionSubLossType[]{ProductionSubLossType.valueOf(productionLossType)});
        List<StandartStep> standartSteps = standartStepRepository.findByProductionSubLossTypesIn(new ProductionSubLossType[]{ProductionSubLossType.valueOf(productionLossType)});

        for (Step step : steps) {
            StepForLossDto stepForLossDto = new StepForLossDto();

            stepForLossDto.setStepName(step.getName());
            stepForLossDto.setStandartStep(false);
            stepForLossDto.setDurationExists(false);
            stepForLossDto.setStepDuration(0D);
            stepForLossDto.setStepId(step.getId());

            allSteps.add(stepForLossDto);
        }

        for (StandartStep standartStep : standartSteps) {
            StepForLossDto stepForLossDto = new StepForLossDto();

            stepForLossDto.setStepName(standartStep.getName());
            stepForLossDto.setStandartStep(true);
            stepForLossDto.setDurationExists(true);
            stepForLossDto.setStepDuration(standartStep.getDuration().doubleValue());
            stepForLossDto.setStepId(standartStep.getId());

            allSteps.add(stepForLossDto);
        }

        // find durations
        Model iModel = modelRepository.findOne(model);

        List<Duration> durations = durationRepository.findByPartDisegnoAndModelsIn(disegno, iModel);
        if (durations.size() > 0) {

            for (Duration duration : durations) {
                for (StepDuration stepDuration : duration.getStepDurations()) {
                    for (StepForLossDto stepDto : allSteps) {
                        if(!stepDto.isStandartStep() && Objects.equals(stepDto.getStepId(), stepDuration.getStepDurationId().getStep().getId())) {
                            if(stepDuration.getDuration().doubleValue() > 0) {
                                stepDto.setStepDuration(stepDuration.getDuration().doubleValue());
                                stepDto.setDurationId(stepDuration.getStepDurationId().getDurationDef().getId());
                                stepDto.setDurationExists(true);
                            }
                        }
                    }
                }
            }

        }

        return allSteps;
    }


    public boolean approve(Long lossId, Long sqpNo, String supplierCode, boolean isSuppChain, Long lossCodeId) {
        VSupplier supplier = vSupplierRepository.findBySapCode(supplierCode);
        VLossCode lossCode = vLossCodeRepository.findOne(lossCodeId);

        ProductionLoss productionLoss = productionLossRepository.findOne(lossId);

        productionLoss.setLossCode(new VLossCodeDecorator(lossCodeId, lossCode));
        productionLoss.setSqpNo(sqpNo);
        productionLoss.setSupplier(new VSupplierDecorator(supplierCode, supplier));

        for (LossStateChange lossStateChange : productionLoss.getStateChanges()) {
            if(lossStateChange.getLossState() == LossState.APPROVED) {
                return true;
            }
        }

        if(productionLoss.getStateChanges().size() == 1) {
            LossStateChange lossStateChange = new LossStateChange();
            lossStateChange.setLoss(productionLoss);
            lossStateChange.setLossState(LossState.APPROVED);

            productionLoss.getStateChanges().add(lossStateChange);

            if(isSuppChain) {
                lossStateChange = new LossStateChange();
                lossStateChange.setLoss(productionLoss);
                lossStateChange.setLossState(LossState.SUPP_CHAIN);
                productionLoss.getStateChanges().add(lossStateChange);
            }
        }

        productionLossRepository.save(productionLoss);

        commonLossService.lossIsApproved(productionLoss);


        return true;
    }



    public double getBlueCollarWage() {
        WorkType blueCollarWage = workTypeRepository.findOne(tfAppConstantsService.getBlueCollarId());

        if(blueCollarWage == null) {
            return -1;
        } else {
            return blueCollarWage.getWageInMinutes().doubleValue();
        }
    }




    public boolean addCompletion(ProductionLossCompletionDto completionDto) {
        ProductionLoss productionLoss = productionLossRepository.findOne(completionDto.getLossId());
        LossCompletion lossCompletion = new LossCompletion();

        if(completionDto.getCompletionStepIsStd().equals(0L)) {
            for (LossStepDuration lossStepDuration : productionLoss.getStepDurations()) {
                if(lossStepDuration.getStep().getId().equals(completionDto.getCompletionStepId())) {
                    lossCompletion.setCompletionDuration(lossStepDuration.getStepDurationCached().longValue() *
                            completionDto.getQty());
                    break;
                }
            }
        } else {
            for (LossStdStepDuration lossStdStepDuration : productionLoss.getStandartDurations()) {
                if(lossStdStepDuration.getStdStep().getId().equals(completionDto.getCompletionStepId())) {
                    lossCompletion.setCompletionDuration(lossStdStepDuration.getStepDurationCached().longValue() *
                        completionDto.getQty());
                    break;
                }
            }
        }

        lossCompletion.setCompletionStepId(completionDto.getCompletionStepId());
        lossCompletion.setCompletionStepIsStd(completionDto.getCompletionStepIsStd());
        lossCompletion.setLoss(productionLoss);
        lossCompletion.setCompletionDate(completionDto.getCompletionDate());
        lossCompletion.setCompletionQty(completionDto.getQty());
        lossCompletion.setShiftCode(completionDto.getCompletedShift());
        lossCompletion.setTutCode(completionDto.getCompletedTut());

        productionLoss.getLossCompletions().add(lossCompletion);

        productionLossRepository.save(productionLoss);

        return true;
    }

    @Override
    public void addDiscardedParts(IDiscardedPartAddableDto dto, IDiscardedPartAddableLoss loss) {
        discardedPartAddableLossService.addDiscardedParts(dto, loss);
    }

    public List<VUnApprovedLosses> listNontApprovedLosses() {
        List<String> userRoles = TfAuthUtility.getUserRoles();
        List<String> lossSourceShortNames = new ArrayList<>();

        if(userRoles.contains(tfAppConstantsService.getProductionLossLogisticsApprover())) {
            lossSourceShortNames.add("L");
        }

        if(userRoles.contains(tfAppConstantsService.getProductionLossQualityApprover())) {
            lossSourceShortNames.add("K");
        }

        if(lossSourceShortNames.size() == 0) {
            return new ArrayList<>();
        } else {
            List<Long> incompleteIds = vInCompletedLossesRepository.findIds();
            return vUnApprovedLossesRepository.findByLossIdInAndLossSourceIn(incompleteIds, lossSourceShortNames);
        }
    }

    public List<VInCompletedLosses> listNonCompletedLosses() {
        return vInCompletedLossesRepository.findAll();
    }


    @Override
    public ProductionLoss getLoss(Long id) {
    	ProductionLoss pl=productionLossRepository.findOne(id);
    	//TODO LossStdStepDuration verileri süreler kısmından silinebiliyor
       // ProductionLoss pl=productionLossRepository.findProductionLossById(id);
    	return  commonLossService.getLoss(pl);
    }

    @Override
    public void addOtherCosts(IOtherCostAddableLossDto dto, IOtherCostAddableLoss loss) {
        otherCostAddableLossService.addOtherCosts(dto, loss);
    }
}
