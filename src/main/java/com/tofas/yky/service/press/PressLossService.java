package com.tofas.yky.service.press;
/* t40127 @ 26.04.2016. */

import com.tofas.yky.enums.LossType;
import com.tofas.yky.enums.PressLossType;
import com.tofas.yky.model.VPartBasePrice;
import com.tofas.yky.model.decoratorbases.decorators.VAlternativePartDecorator;
import com.tofas.yky.model.decoratorbases.decorators.VSupplierDecorator;
import com.tofas.yky.model.decoratorbases.impl.VPart;
import com.tofas.yky.model.decoratorbases.impl.VSupplier;
import com.tofas.yky.model.dto.press.PressLossDto;
import com.tofas.yky.model.dto.press.config.PressParameters;
import com.tofas.yky.model.dto.press.detail.AbstractPressLossDetailDto;
import com.tofas.yky.model.dto.press.detail.AtrscField;
import com.tofas.yky.model.dto.press.detail.PressLogisticsLossDetailDto;
import com.tofas.yky.model.dto.press.detail.PressQualityLossDetailDto;
import com.tofas.yky.model.dto.press.pos.PressPosDisegnoDetailDto;
import com.tofas.yky.model.dto.press.pos.PressPosRollDetailDto;
import com.tofas.yky.model.losses.press.PressLoss;
import com.tofas.yky.model.losses.press.VPressOpenLosses;
import com.tofas.yky.model.losses.press.details.AbstractPressLoss;
import com.tofas.yky.model.losses.press.details.PressLogisticsLoss;
import com.tofas.yky.model.losses.press.details.PressQualityLoss;
import com.tofas.yky.model.losses.press.pos.VPosDisegnoDetail;
import com.tofas.yky.model.losses.press.pos.VPosRollDetail;
import com.tofas.yky.repositories.VPartBasePriceRepository;
import com.tofas.yky.repositories.VPartsRepository;
import com.tofas.yky.repositories.VSupplierRepository;
import com.tofas.yky.repositories.press.*;
import com.tofas.yky.service.ProductionLossService;
import com.tofas.yky.service.TfAppConstantsService;
import com.tofas.yky.service.press.upload.TfExcelError;
import com.tofas.yky.service.press.upload.TfRow;
import com.tofas.yky.service.press.upload.TfSheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PressLossService {


    @Autowired
    private PressUploadComponent uploadComponent;

    @Autowired
    private VPosRollDetailRepository vPosRollDetailRepository;

    @Autowired
    private VPosDisegnoDetailRepository vPosDisegnoDetailRepository;

    @Autowired
    private VPartBasePriceRepository vPartBasePriceRepository;

    @Autowired
    private TfAppConstantsService tfAppConstantsService;

    @Autowired
    private VSupplierRepository vSupplierRepository;

    @Autowired
    private ProductionLossService productionLossService;

    @Autowired
    private VPartsRepository vPartRepository;

    @Autowired
    private VPressOpenLossesRepository vPressOpenLossesRepository;


    @Autowired
    private PressLossRepository pressLossRepository;

    @Autowired
    private PressQualityLossRepository pressQualityLossRepository;

    @Autowired
    private PressLogisticsLossRepository pressLogisticsLossRepository;


    public boolean uploadFile(MultipartFile file, PressLossType pressLossType) {
        TfSheet sheet = null;
        try {
            Workbook wb = WorkbookFactory.create(file.getInputStream());
            sheet = new TfSheet(wb.getSheetAt(0));

            switch (pressLossType) {
                case LOGISTICS:
                    uploadLogisticsFile(sheet);
                    break;
                case QUALITY:
                    uploadQualityFile(sheet);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        } finally {
            uploadComponent.getErrors().addAll(sheet.getErrors());
        }

        return true;
    }

    public List<AbstractPressLossDetailDto> returnDetails() {
        return uploadComponent.returnDetails();
    }

    public List<Map<String, Object>> getHrdParams() {
        List<Map<String, Object>> hrdParams = new ArrayList<>();

        hrdParams.add(new HashMap<String, Object>(){{
            put("name", "HRD03");
            put("value", tfAppConstantsService.getScrapSalesParamHrd033());
        }});
        hrdParams.add(new HashMap<String, Object>(){{
            put("name", "HRD088");
            put("value", tfAppConstantsService.getScrapSalesParamHrd088());
        }});

        return hrdParams;
    }

    public List<PressQualityLossDetailDto> updateDetailsQ(List<PressQualityLossDetailDto> lossDetails) {

        updateCommonFields(lossDetails);

        BigDecimal customsCostParam = tfAppConstantsService.getCustomsCostParam();
        BigDecimal logisticsCostParam = tfAppConstantsService.getLogisticsCostParam();

        for (PressQualityLossDetailDto lossDetail : lossDetails) {
            lossDetail.setCustomsCostParam(customsCostParam);
            lossDetail.setLogisticsCostParam(logisticsCostParam);
        }

        return lossDetails;
    }

    public List<PressLogisticsLossDetailDto> updateDetailsL(List<PressLogisticsLossDetailDto> lossDetails) {

        updateCommonFields(lossDetails);

        for (PressLogisticsLossDetailDto lossDetail : lossDetails) {
            if(lossDetail.isAlternateDisegnoChanged()) {
                VPosDisegnoDetail vAlternativeDisegnoDetail =
                        getDisegnoDetail(lossDetail.getCustomAlternatePartDisegno());

                lossDetail.setAltDisegnoDetail(new PressPosDisegnoDetailDto(vAlternativeDisegnoDetail,
                        getBasePrice(lossDetail.getCustomAlternatePartDisegno())));
            }
        }


        return lossDetails;
    }

    protected void updateCommonFields(List<? extends AbstractPressLossDetailDto> lossDetails) {
        List<AbstractPressLossDetailDto> toBeRemoved = new ArrayList<>();
        BigDecimal blueCollarWage = tfAppConstantsService.getBlueCollarBaseWageAsEur();

        for (AbstractPressLossDetailDto lossDetail : lossDetails) {
            lossDetail.setBlueCollarBaseWage(blueCollarWage);
            if(lossDetail.isRollNoChanged()) {
                VPosRollDetail newRollDetail = getRollDetail(lossDetail.getRollDetail().getCustomRollNo());

                if(newRollDetail != null) {
                    lossDetail.setRollDetail(new PressPosRollDetailDto(newRollDetail,
                            getBasePrice(newRollDetail.getDisegnoDetail().getDisegno())));
                } else {
                    toBeRemoved.add(lossDetail);
                }
            }
        }

        lossDetails.removeAll(toBeRemoved);
    }



    protected void uploadLogisticsFile(TfSheet sheet) throws RuntimeException {
        int nLastRow = sheet.getLastRowNum();
        BigDecimal blueCollarWage = tfAppConstantsService.getBlueCollarBaseWageAsEur();
        BigDecimal hrd033 = tfAppConstantsService.getScrapSalesParamHrd033();
        BigDecimal hrd088 = tfAppConstantsService.getScrapSalesParamHrd088();

        for (int i = 1; i <= nLastRow; i++) {
            TfRow row = sheet.getRow(i);

            int nColIndex = 0;

            PressLogisticsLossDetailDto lossDetailDto = new PressLogisticsLossDetailDto(blueCollarWage);



            String rollNo = row.getCell(nColIndex++).getStringCellValue();

            lossDetailDto.setAtrscField(new AtrscField(row.getCell(nColIndex++).getStringCellValue()));
            lossDetailDto.setFirmApprover(row.getCell(nColIndex++).getStringCellValue());
            lossDetailDto.setTransportedFrom(row.getCell(nColIndex++).getStringCellValue());
            lossDetailDto.setTransportedTo(row.getCell(nColIndex++).getStringCellValue());
            lossDetailDto.setTransportDate(row.getCell(nColIndex++).getDateCellValue());
            lossDetailDto.setCsmInvoiceNo(row.getCell(nColIndex++).getStringCellValue());
            lossDetailDto.setCsmInvoiceAmount(BigDecimal.valueOf(row.getCell(nColIndex++).getNumericCellValue()));

            String alternativeDisegno = row.getCell(nColIndex++).getStringCellValue();

            lossDetailDto.setAltDisegnoSliceWidth(BigDecimal.valueOf(row.getCell(nColIndex++).getNumericCellValue()));
            lossDetailDto.setAltDiscardReason(row.getCell(nColIndex++).getStringCellValue());
            lossDetailDto.setRollTofasTransDate(row.getCell(nColIndex++).getDateCellValue());

            lossDetailDto.setLogisticsLabourHours(BigDecimal.valueOf(row.getCell(nColIndex++)
                    .getNumericCellValue()).longValue());
            lossDetailDto.setProductionLabourHours(BigDecimal.valueOf(row.getCell(nColIndex++)
                    .getNumericCellValue()).longValue());

            lossDetailDto.setSlicePerTonne(BigDecimal.valueOf(row.getCell(nColIndex++).getNumericCellValue()));
            lossDetailDto.setFirmTransportationCost(BigDecimal.valueOf(row.getCell(nColIndex++).getNumericCellValue()));
            lossDetailDto.setWarehouseCost(BigDecimal.valueOf(row.getCell(nColIndex++).getNumericCellValue()));

            String hrdParam = row.getCell(nColIndex++).getStringCellValue();
            if(hrdParam.contains("88")) {
                lossDetailDto.setScrapHrdParam(hrd088);
            } else {
                lossDetailDto.setScrapHrdParam(hrd033);
            }



            VPosRollDetail vPosRollDetail = getRollDetail(rollNo);
            VPosDisegnoDetail vAlternativeDisegnoDetail = getDisegnoDetail(alternativeDisegno);

            if(vPosRollDetail == null) {
                continue;
            }



            lossDetailDto.setRollDetail(new PressPosRollDetailDto(vPosRollDetail,
                    getBasePrice(vPosRollDetail.getDisegnoDetail().getDisegno())));



            if(vAlternativeDisegnoDetail != null) {
                lossDetailDto.setAltDisegnoDetail(new PressPosDisegnoDetailDto(vAlternativeDisegnoDetail,
                        getBasePrice(vAlternativeDisegnoDetail.getDisegno())));
            } else {
                lossDetailDto.setAltDisegnoDetail(new PressPosDisegnoDetailDto());
            }


            uploadComponent.getDetails().add(lossDetailDto);
        }
    }

    protected void uploadQualityFile(TfSheet sheet) {
        int nLastRow = sheet.getLastRowNum();

        BigDecimal customsCostParam = tfAppConstantsService.getCustomsCostParam();
        BigDecimal logisticsCostParam = tfAppConstantsService.getLogisticsCostParam();

        BigDecimal blueCollarWage = tfAppConstantsService.getBlueCollarBaseWageAsEur();
        BigDecimal hrd033 = tfAppConstantsService.getScrapSalesParamHrd033();
        BigDecimal hrd088 = tfAppConstantsService.getScrapSalesParamHrd088();

        for (int i = 1; i <= nLastRow; i++) {
            TfRow row = sheet.getRow(i);

            int nColIndex = 0;

            PressQualityLossDetailDto lossDetailDto = new PressQualityLossDetailDto(blueCollarWage);

            String rollNo = row.getCell(nColIndex++).getStringCellValue();


            lossDetailDto.setQualityTraceId(row.getCell(nColIndex++).getStringCellValue());
            lossDetailDto.setCutDate(row.getCell(nColIndex++).getDateCellValue());
            lossDetailDto.setMoldNo(row.getCell(nColIndex++).getStringCellValue());
            lossDetailDto.setRollScrapWeight(BigDecimal.valueOf(row.getCell(nColIndex++).getNumericCellValue()));
            lossDetailDto.setCutScrapQty(BigDecimal.valueOf(row.getCell(nColIndex++).getNumericCellValue()));
            lossDetailDto.setPressedScrapQty(BigDecimal.valueOf(row.getCell(nColIndex++).getNumericCellValue()));
            lossDetailDto.setRepairQty(BigDecimal.valueOf(row.getCell(nColIndex++).getNumericCellValue()));
            lossDetailDto.setProductionLabourHours(BigDecimal.valueOf(row.getCell(nColIndex++).getNumericCellValue()).longValue());
            lossDetailDto.setLogisticsLabourHours(BigDecimal.valueOf(row.getCell(nColIndex++).getNumericCellValue()).longValue());
            lossDetailDto.setLogisticsTransportPlace(row.getCell(nColIndex++).getStringCellValue());
            lossDetailDto.setNotes(row.getCell(nColIndex++).getStringCellValue());
            lossDetailDto.setReportDate(row.getCell(nColIndex++).getDateCellValue());
            lossDetailDto.setReportKeeper(row.getCell(nColIndex++).getStringCellValue());
            lossDetailDto.setCutPartBaseWeight(BigDecimal.valueOf(row.getCell(nColIndex++).getNumericCellValue()));
            lossDetailDto.setPressedPartBaseWeight(BigDecimal.valueOf(row.getCell(nColIndex++).getNumericCellValue()));
            lossDetailDto.setScrapSoldOut(BigDecimal.valueOf(row.getCell(nColIndex++).getNumericCellValue()));
            lossDetailDto.setScrapSalePrice(BigDecimal.valueOf(row.getCell(nColIndex++).getNumericCellValue()));

            String hrdParam = row.getCell(nColIndex++).getStringCellValue();
            if(hrdParam.contains("88")) {
                lossDetailDto.setScrapHrdParam(hrd088);
            } else {
                lossDetailDto.setScrapHrdParam(hrd033);
            }


            VPosRollDetail vPosRollDetail = getRollDetail(rollNo);

            if(vPosRollDetail == null) {
                continue;
            }

            lossDetailDto.setRollDetail(new PressPosRollDetailDto(vPosRollDetail,
                    getBasePrice(vPosRollDetail.getDisegnoDetail().getDisegno())));



            lossDetailDto.setCustomsCostParam(customsCostParam);
            lossDetailDto.setLogisticsCostParam(logisticsCostParam);

            uploadComponent.getDetails().add(lossDetailDto);

        }
    }




    protected BigDecimal getBasePrice(String disegno) {
        BigDecimal basePrice = BigDecimal.ZERO;
        List<VPartBasePrice> basePricesByDisegno = vPartBasePriceRepository.findFirstByDisegnoOrderByPriceDateDesc(disegno);
        if(basePricesByDisegno != null && basePricesByDisegno.size() == 1) {
            basePrice = basePricesByDisegno.get(0).getBaseUnitPriceInEur()
                .multiply(BigDecimal.valueOf(1000));
        }

        return basePrice;
    }

    protected VPosRollDetail getRollDetail(String rollNo) {
     //   return vPosRollDetailRepository.findOne(rollNo);
      //  return vPosRollDetailRepository.findByRollNo(rollNo);
        return vPosRollDetailRepository.vPosRollDetail(rollNo);


    }

    protected VPosDisegnoDetail getDisegnoDetail(String disegno) {
        return vPosDisegnoDetailRepository.findOne(disegno);
    }

    public List<VSupplier> getPressSuppliers() {
        return vSupplierRepository.findBySheetFlagOrderBySapCode("Y");
    }

    public Long savePressLoss(PressLossDto pressLossDto) {
        PressLoss pressLoss = new PressLoss();

        updateProperties(pressLoss, pressLossDto);


        return pressLoss.getId();
    }

    private void updateProperties(PressLoss pressLoss, PressLossDto pressLossDto) {
        VSupplier vSupplier = vSupplierRepository.findBySapCode(pressLossDto.getSupplierDto().getSapCode());

        pressLoss.setFirmDescription(pressLossDto.getFirmDescription());
        pressLoss.setFirmType(pressLossDto.getFirmType());
        pressLoss.setPressLossType(pressLossDto.getPressLossType());
        pressLoss.setSupplier(new VSupplierDecorator(pressLossDto.getSupplierDto().getSapCode(), vSupplier));
        pressLoss.setLossType(LossType.PRESS);
        pressLoss.setDescription(pressLossDto.getDescription());
        pressLoss.setLossDate(pressLossDto.getLossDate());
        pressLossRepository.save(pressLoss);

        switch (pressLoss.getPressLossType()) {
            case LOGISTICS:
                updateLogisticsDetails(pressLoss, pressLossDto);
                break;
            case QUALITY:
                updateQualityDetails(pressLoss, pressLossDto);
                break;
        }




    }

    EntityManager em;

    private void updateQualityDetails(PressLoss pressLoss, PressLossDto pressLossDto) {
        for (AbstractPressLossDetailDto abstractPressLossDetailDto : pressLossDto.getDetails()) {
            PressQualityLossDetailDto pressQualityLossDetailDto = (PressQualityLossDetailDto) abstractPressLossDetailDto;

            VPosRollDetail vRollDetail = getRollDetail(abstractPressLossDetailDto.getRollDetail().getRollNo());
            PressQualityLoss pressQualityLoss = new PressQualityLoss();
            pressQualityLoss.setLoss(pressLoss);

            pressQualityLoss.setFromDto(pressQualityLossDetailDto, vRollDetail);

            pressQualityLossRepository.save(pressQualityLoss);
        }
    }

    private void updateLogisticsDetails(PressLoss pressLoss, PressLossDto pressLossDto) {
        for (AbstractPressLossDetailDto abstractPressLossDetailDto : pressLossDto.getDetails()) {
            PressLogisticsLossDetailDto pressLogisticsLossDetailDto = (PressLogisticsLossDetailDto) abstractPressLossDetailDto;

            PressLogisticsLoss pressLogisticsLoss = new PressLogisticsLoss();
            pressLogisticsLoss.setLoss(pressLoss);

            VPosRollDetail vRollDetail = getRollDetail(abstractPressLossDetailDto.getRollDetail().getRollNo());

            String alternativeDisegno = pressLogisticsLossDetailDto.getAltDisegnoDetail().getDisegno();
            VPosDisegnoDetail vAlternativeDisegnoDetail = getDisegnoDetail(alternativeDisegno);
            VPart vAlternativePart = vPartRepository.findByDisegno(alternativeDisegno);

            pressLogisticsLoss.setFromDto(pressLogisticsLossDetailDto, vRollDetail, vAlternativeDisegnoDetail,
                    new VAlternativePartDecorator(alternativeDisegno, vAlternativePart));


            pressLogisticsLossRepository.save(pressLogisticsLoss);
        }
    }

    @Transactional
    public PressLoss getLoss(Long lossId) {
        PressLoss pressLoss = pressLossRepository.findOne(lossId);
        Hibernate.initialize(pressLoss.getDetails());
        return pressLoss;
    }

    public List<TfExcelError> returnErrors() {
        return uploadComponent.returnErrors();
    }


    @Transactional
    public List<VPressOpenLosses> listOpenLosses() {
        return vPressOpenLossesRepository.findAll(new Sort("id"));
    }

    public boolean approveLoss(Long lossId, List<AbstractPressLoss> details) {
        PressLoss dbLoss = pressLossRepository.findOne(lossId);
        dbLoss.approveLoss();

        for (AbstractPressLoss dbLossDetail : dbLoss.getDetails()) {
            for (AbstractPressLoss clientLossDetail : details) {
                if(clientLossDetail.getId().equals(dbLossDetail.getId())) {

                    dbLossDetail.setSapOrderNo(clientLossDetail.getSapOrderNo());
                    dbLossDetail.setCreditNote(clientLossDetail.getCreditNote());
                    dbLossDetail.setCreditNoteAmount(clientLossDetail.getCreditNoteAmount());
                    dbLossDetail.setCreditNoteDate(clientLossDetail.getCreditNoteDate());
                    dbLossDetail.setPaymentInformer(clientLossDetail.getPaymentInformer());
                    dbLossDetail.setPaymentMailDate(clientLossDetail.getPaymentMailDate());

                    if(dbLoss.getPressLossType().equals(PressLossType.LOGISTICS)) {
                        pressLogisticsLossRepository.save((PressLogisticsLoss) dbLossDetail);
                    } else {
                        pressQualityLossRepository.save((PressQualityLoss) dbLossDetail);
                    }

                }
            }
        }

        pressLossRepository.save(dbLoss);

        return true;
    }

    public PressParameters getParameters() {
        return tfAppConstantsService.getPressParameters();
    }

    public boolean setParameters(PressParameters pressParameters) {
        return tfAppConstantsService.setPressParameters(pressParameters);
    }

    public boolean saveDetailsL(List<PressLogisticsLoss> lossDetails) {
        List<Long> ids = new ArrayList<>();


        for (PressLogisticsLoss lossDetail : lossDetails) {
            ids.add(lossDetail.getId());
        }

        List<PressLogisticsLoss> selectedObjects = pressLogisticsLossRepository.findAll(ids);
        List<PressLogisticsLoss> updatedObjects = new ArrayList<>();

        for (PressLogisticsLoss selectedObject : selectedObjects) {
            for (PressLogisticsLoss lossDetail : lossDetails) {
                if(selectedObject.getId().equals(lossDetail.getId())) {
                    selectedObject.updateFields(lossDetail);

                    updatedObjects.add(selectedObject);
                    continue;
                }
            }
        }

        pressLogisticsLossRepository.save(updatedObjects);

        return false;
    }

    public boolean saveDetailsQ(List<PressQualityLoss> lossDetails) {
        List<Long> ids = new ArrayList<>();


        for (PressQualityLoss lossDetail : lossDetails) {
            ids.add(lossDetail.getId());
        }

        List<PressQualityLoss> selectedObjects = pressQualityLossRepository.findAll(ids);
        List<PressQualityLoss> updatedObjects = new ArrayList<>();

        for (PressQualityLoss selectedObject : selectedObjects) {
            for (PressQualityLoss lossDetail : lossDetails) {
                if(selectedObject.getId().equals(lossDetail.getId())) {
                    selectedObject.updateFields(lossDetail);

                    updatedObjects.add(selectedObject);
                    continue;
                }
            }
        }

        pressQualityLossRepository.save(updatedObjects);


        return false;
    }
}
