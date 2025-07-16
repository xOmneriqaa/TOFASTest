package com.tofas.yky.controller.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tofas.yky.enums.LossState;
import com.tofas.yky.enums.LossType;
import com.tofas.yky.enums.ProductionSubLossType;
import com.tofas.yky.model.Model;
import com.tofas.yky.model.VShiftCode;
import com.tofas.yky.model.decoratorbases.impl.VLossCode;
import com.tofas.yky.model.decoratorbases.impl.VPart;
import com.tofas.yky.model.decoratorbases.impl.VSupplier;
import com.tofas.yky.model.decoratorbases.impl.VTut;
import com.tofas.yky.model.dto.StepForLossDto;
import com.tofas.yky.model.dto.VPartDto;
import com.tofas.yky.model.losses.LossSource;
import com.tofas.yky.model.losses.ObjectionType;
import com.tofas.yky.model.losses.WorkType;
import com.tofas.yky.model.losses.logistics.LogisticsAcceptencePoint;
import com.tofas.yky.model.losses.logistics.LogisticsLossType;
import com.tofas.yky.model.losses.production.duration.StandartStep;
import com.tofas.yky.model.losses.production.duration.Step;
import com.tofas.yky.model.losses.quality.QualityLossTable;
import com.tofas.yky.model.losses.qualitylab.QualityLabTest;
import com.tofas.yky.repositories.*;
import com.tofas.yky.service.ProductionLossService;
import com.tofas.yky.service.VPartService;
import org.hibernate.ejb.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/common/api")
public class CommonController {

    @PersistenceContext
    EntityManager entityManager;

    private @Resource
    ModelRepository modelRepository;

    private @Resource
    VTutRepository vTutRepository;

    private @Autowired
    VPartService vPartService;

    private @Resource
    StandartStepRepository standartStepRepository;

    private @Resource
    StepRepository stepRepository;

    private @Resource
    QualityLossTableRepository qualityLossTableRepository;

    private @Resource VSupplierRepository vSupplierRepository;

    private @Resource WorkTypeRepository workTypeRepository;

    private @Resource VLossCodeRepository vLossCodeRepository;

    private @Resource LogisticsAcceptencePointRepository logisticsAcceptencePointRepository;

    private @Resource LogisticsLossTypeRepository logisticsLossTypeRepository;

    private @Resource QualityLabTestRepository qualityLabTestRepository;

    private @Resource LossSourceRepository lossSourceRepository;

    private @Resource VShiftCodesRepository vShiftCodesRepository;

    private @Resource ObjectionTypeRepository objectionTypeRepository;

    private @Autowired
    ProductionLossService productionLossService;

    private List<LossType> lossTypes;

    private List<ProductionSubLossType> productionSubLossTypes;

    private List<LossState> lossStates;

    @PostConstruct
    public void init(){
        lossTypes = new ArrayList<>();
        lossTypes.add(LossType.PRODUCTION);
        lossTypes.add(LossType.LOGISTICS);
        lossTypes.add(LossType.QUALITY);
        lossTypes.add(LossType.QUALITY_LAB);
        lossTypes.add(LossType.PRESS);

        productionSubLossTypes = new ArrayList<>();
        productionSubLossTypes.add(ProductionSubLossType.ASSEMBLY);
        productionSubLossTypes.add(ProductionSubLossType.BODY);
        productionSubLossTypes.add(ProductionSubLossType.PAINT);
        productionSubLossTypes.add(ProductionSubLossType.SUSPENSION);
        productionSubLossTypes.add(ProductionSubLossType.PRESS);

        lossStates = new ArrayList<>();
        lossStates.add(LossState.OPEN);
        lossStates.add(LossState.APPROVED);
        lossStates.add(LossState.SUPP_CHAIN);
        lossStates.add(LossState.EX_FIRM_NOT_INVOICED);
        lossStates.add(LossState.INVOICE_APPROVED);
        lossStates.add(LossState.INVOICED);
        lossStates.add(LossState.CANCELED);
        lossStates.add(LossState.PAUSED);

    }

    @RequestMapping("/heart-beat")
    public boolean heartBeat() {
        return true;
    }

    @RequestMapping("/loss-types")
    public List<LossType> getLossTypes() {
        return lossTypes;
    }

    @RequestMapping("/production-sub-loss-types")
    public List<ProductionSubLossType> getProductionSubLossTypes() {
        return productionSubLossTypes;
    }

    @RequestMapping("/loss-states")
    public List<LossState> getLossStates() {
        return lossStates;
    }

    @Cacheable("models")
    @RequestMapping("/models")
    public List<Model> getModels() {
        return modelRepository.findAllActiveOrdered();
    }



    @RequestMapping(value = "/parts", produces = "text/plain;charset=UTF-8")
    public String getParts() {

       // return vPartService.listAllPartsStr();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        try {
            return objectMapper.writeValueAsString(listAllParts());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<VPartDto> listAllParts() {
        List<VPart> allParts = entityManager.createQuery("Select part from VPart part")
                .setHint(QueryHints.HINT_FETCH_SIZE, 350000)
                .getResultList();
        List<VPartDto> partDtos = new ArrayList<>();

        for(VPart part : allParts) {
            partDtos.add(new VPartDto(part.getDisegno(), part.getDescTr()));
        }

        return partDtos;
    }

    @RequestMapping("/get-part/{disegno}")
    public int getPart(@PathVariable("disegno") String disegno) {
        List<VPartDto> vPartDtos = vPartService.listAllParts();
        for(int i = 0; i < vPartDtos.size(); i++) {
            if(vPartDtos.get(i).getDisegno().equals(disegno)) {
                return i;
            }
        }
        return -1;
    }

    @Cacheable("tuts")
    @RequestMapping("/tuts")
    public List<VTut> getTuts() {
        return vTutRepository.findAllActiveOrdered();
    }

    @RequestMapping("/standart-steps")
    public List<StandartStep> getStandartSteps() {
        return standartStepRepository.findAllActiveOrdered();
    }

    @RequestMapping("/steps")
    public List<Step> getSteps() {
        return stepRepository.findAllActiveOrdered();
    }

    @RequestMapping("/quality-loss-tables")
    public List<QualityLossTable> getQualityLossTables() {
        return qualityLossTableRepository.findAllActiveOrdered();
    }

    @RequestMapping("/work-types")
    public List<WorkType> getWorkTypes() {
        return workTypeRepository.findAllActiveOrdered();
    }

    @RequestMapping("/supplier-from-disegno")
    public List<VSupplier> getSupplierFromDisegno(@RequestBody String disegno) {
        return vSupplierRepository.findByDisegno(disegno);
        //VSupplier vSupplier = vSupplierRepository.findBySapCode("4009668");
        //return vSupplierRepository.findByDisegno("00003553000E");

        //return vSupplierRepository.findByDisegno(disegno);
    }

    @RequestMapping("/loss-codes")
    public List<VLossCode> getLossCodes() {
        return vLossCodeRepository.findAll();
    }

    @RequestMapping("/logistics-acceptence-points")
    public List<LogisticsAcceptencePoint> getLogisticsAcceptencePoints() {
        return logisticsAcceptencePointRepository.findAllActiveOrdered();
    }

    @RequestMapping("/logistics-loss-types")
    public List<LogisticsLossType> getLogisticsLossTypes() {
        return logisticsLossTypeRepository.findAllActiveOrdered();
    }

    @RequestMapping("/quality-lab-tests")
    public List<QualityLabTest> getQualityLabTests() {
        return qualityLabTestRepository.findAllActiveOrdered();
    }

    @RequestMapping("/shift-codes")
    public List<VShiftCode> getShiftCodes() {
        return vShiftCodesRepository.findAll();
    }

    @RequestMapping("/shift-codes/{orgMmm}")
    public List<VShiftCode> getShiftCodesByOrgMmm(@PathVariable("orgMmm") String orgMmm) {
        return vShiftCodesRepository.findByOrgMmm(orgMmm);
    }

    @RequestMapping("/loss-sources")
    public List<LossSource> getLossSources() {
        return lossSourceRepository.findAllActiveOrdered();
    }

    @RequestMapping("/related-steps/{subType}/{model}/{disegno}/{tut}")
    public List<StepForLossDto> getRelatedSteps(@PathVariable("subType") String productionSubLossType,
                                        @PathVariable("model") String model,
                                        @PathVariable("disegno") String disegno,
                                        @PathVariable("tut") String tut) {
        return productionLossService.getRelatedStepNames(productionSubLossType, model, disegno, tut);
    }


    @RequestMapping("/objection-types")
    public List<ObjectionType> getObjectionTypes() {
        return objectionTypeRepository.findAllActiveOrdered();
    }


}
