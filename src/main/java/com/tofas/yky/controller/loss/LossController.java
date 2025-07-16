package com.tofas.yky.controller.loss;

    import com.tofas.core.legacy.controllers.TfAppRoleController;
import com.tofas.yky.model.VPartBasePrice;
import com.tofas.yky.model.dto.*;
import com.tofas.yky.model.losses.Loss;
    import com.tofas.yky.model.losses.QualityLabApprover;
    import com.tofas.yky.model.losses.logistics.LogisticsLoss;
import com.tofas.yky.model.losses.press.PressLoss;
import com.tofas.yky.model.losses.production.ProductionLoss;
import com.tofas.yky.model.losses.quality.QualityLoss;
import com.tofas.yky.model.losses.qualitylab.QualityLabLoss;
import com.tofas.yky.repositories.CommonLossRepository;
import com.tofas.yky.service.*;
import com.tofas.yky.service.press.PressLossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

@RestController
@RequestMapping("/loss/api")
public class LossController {

    private final QualityLossService qualityLossService;

    private final LogisticsLossService logisticsLossService;

    private final QualityLabLossService qualityLabLossService;

    private final ProductionLossService productionLossService;

    private final CommonLossRepository commonLossRepository;

    private final CommonLossService commonLossService;

    private final SessionFileService sessionFileService;

    private final LossCommentService lossCommentService;

    private final LossDiscardPartService lossDiscardPartService;

    private final PressLossService pressLossService;

    @Autowired
    public LossController(QualityLossService qualityLossService, LogisticsLossService logisticsLossService,
                          QualityLabLossService qualityLabLossService, ProductionLossService productionLossService,
                          CommonLossRepository commonLossRepository, CommonLossService commonLossService, SessionFileService sessionFileService,
                          LossCommentService lossCommentService, LossDiscardPartService lossDiscardPartService,
                          PressLossService pressLossService) {
        this.qualityLossService = qualityLossService;
        this.logisticsLossService = logisticsLossService;
        this.qualityLabLossService = qualityLabLossService;
        this.productionLossService = productionLossService;
        this.commonLossRepository = commonLossRepository;
        this.commonLossService = commonLossService;
        this.sessionFileService = sessionFileService;
        this.lossCommentService = lossCommentService;
        this.lossDiscardPartService = lossDiscardPartService;
        this.pressLossService = pressLossService;
    }


    // ATTACHMENTS

    @RequestMapping("/upload-file")
    public boolean uploadFile(@RequestParam("files") MultipartFile file) {
        return sessionFileService.addFile(file);
    }

    @RequestMapping("/persist-files/{lossId}")
    public boolean instantUploadFile(@PathVariable Long lossId) {
        return commonLossService.addFiles(lossId, sessionFileService.getSessionBasedFileUploader());
    }

    @RequestMapping("/delete-cached")
    public boolean deleteCachedFile(@ModelAttribute("fileNames") String fileNames) {
        return sessionFileService.removeFile(fileNames);
    }

    @RequestMapping("/delete-cached-all")
    public boolean deleteAllCachedFiles() {
        return sessionFileService.clearAllFiles();
    }

    // END OF ATTACHMENTS










    // ADDING NEW LOSSES


    @RequestMapping("/quality/save")
    public Long saveQualityLoss(@RequestBody QualityLossDto qualityLossDto) {
        return qualityLossService.saveQualityLoss(qualityLossDto, sessionFileService.getSessionBasedFileUploader());
    }

    @RequestMapping("/logistics/save")
    public Long saveLogisticsLoss(@RequestBody LogisticsLossDto logisticsLossDto) {
        return logisticsLossService.saveLogisticsLoss(logisticsLossDto, sessionFileService.getSessionBasedFileUploader());
    }

    @RequestMapping("/quality-lab/save")
    public Long saveQualityLabLoss(@RequestBody QualityLabLossDto logisticsLossDto) {
        return qualityLabLossService.saveQualityLabLoss(logisticsLossDto, sessionFileService.getSessionBasedFileUploader());
    }

    @RequestMapping("/production/save")
    public Long saveProductionLoss(@RequestBody ProductionLossDto productionLossDto) {
        return productionLossService.saveProductionLoss(productionLossDto, sessionFileService.getSessionBasedFileUploader());
    }


    // END OF ADDING NEW LOSSES








    @RequestMapping("/type/{lossId}")
    public String getLossType(@PathVariable("lossId") Long lossId) {
        String lossType = commonLossRepository.getLossType(lossId);
        lossType = "\"" + lossType + "\"";
        return lossType;
    }

    @RequestMapping("/blue-collar-wage")
    public Double getBlueCollarWage() {
        return productionLossService.getBlueCollarWage();
    }

    @RequestMapping("/quality/get/{lossId}")
    public QualityLoss getQualityLoss(@PathVariable("lossId") Long lossId) {
        return qualityLossService.getLoss(lossId);
    }

    @RequestMapping("/production/get/{lossId}")
    public ProductionLoss getProductionLoss(@PathVariable("lossId") Long lossId) {
        return productionLossService.getLoss(lossId);
    }

    @RequestMapping("/quality-lab/get/{lossId}")
    public QualityLabLoss getQualityLabLoss(@PathVariable("lossId") Long lossId) {
        return qualityLabLossService.getLoss(lossId);
    }

    @RequestMapping("/logistic/get/{lossId}")
    public LogisticsLoss getLogisticsLoss(@PathVariable("lossId") Long lossId) {
        return logisticsLossService.getLoss(lossId);
    }

    @RequestMapping("/press/get/{lossId}")
    public PressLoss getPressLoss(@PathVariable("lossId") Long lossId) {
        return pressLossService.getLoss(lossId);
    }




    // PRODUCTION
    @RequestMapping("/approve/{lossId}/{sqpNo}/{supplierCode}/{isSuppChain}/{lossCodeId}")
    public boolean approve(@PathVariable("lossId") Long lossId, @PathVariable("sqpNo") Long sqpNo,
                           @PathVariable("supplierCode") String supplierCode,
                           @PathVariable("isSuppChain") Boolean isSuppChain,
                           @PathVariable("lossCodeId") Long lossCodeId) {
        return productionLossService.approve(lossId, sqpNo, supplierCode, isSuppChain, lossCodeId);
    }

    @RequestMapping("/add-completion")
    public boolean addCompletion(@RequestBody ProductionLossCompletionDto completionDto) {
        return productionLossService.addCompletion(completionDto);
    }

    // END PRODUCTION


    // QUALITY-LAB

    @RequestMapping("/approve/{lossId}/{sqpNo}")
    public boolean approveQualityLab(@PathVariable("lossId") long lossId, @PathVariable("sqpNo") long sqpNo){
       return qualityLabLossService.approve(lossId, sqpNo);
    }

    @RequestMapping("/approve/{lossId}")
    public boolean approveQualityLab(@PathVariable("lossId") long lossId){
        return qualityLabLossService.approve(lossId);
    }

    @RequestMapping("/approver-roles")
    public List<QualityLabApprover> getApproverRoles(){
       /* Map<String , String> approverRoles = new HashMap<>();
        return Arrays.asList(
                "TFG_YKY_APPROVAL_QUALITYLAB_DESK",
                "TFG_YKY_APPROVAL_QUALITYLAB_SAMPLE",
                "TFG_YKY_APPROVAL_QUALITYLAB_AFTERSALES");*/
        return qualityLabLossService.getApproverRoles();
    }

    // END QUALITY-LAB



    // COMMENTS
    @RequestMapping("/add-comment/")
    public boolean addComment(@RequestBody LossCommentDto commentDto) {
        return lossCommentService.addComment(commentDto);
    }


    // END COMMENTS







    // DISCARD PARTS
    @RequestMapping("/get-part-base-price/{disegno}")
    public List<VPartBasePrice> getBasePricesForPart(@PathVariable("disegno") String disegno) {
        return lossDiscardPartService.getBasePricesForPart(disegno);
    }

    @RequestMapping("/discard-vocuher-no/{voucherNo}")
    public boolean isDiscardedPartVocuherValid(@PathVariable("voucherNo") Long voucherNo) {
        return lossDiscardPartService.isDiscardedPartVocuherValid(voucherNo);
    }

    // END DISCARD PARTSdoldur boşalt




    // CANCEL LOSS

    @RequestMapping("/cancel/{lossId}")
    public boolean cancelLoss(@PathVariable Long lossId, @RequestBody String cancelDesc){
        return commonLossRepository.cancelLoss(lossId, cancelDesc);
    }

    // END CANCEL LOSS




    // pause loss

    @RequestMapping("/pause/{lossId}")
    public boolean pauseLoss(@PathVariable Long lossId) {
        return commonLossRepository.pauseLoss(lossId);
    }

    // end pause loss



}
