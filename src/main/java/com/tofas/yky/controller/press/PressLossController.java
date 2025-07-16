package com.tofas.yky.controller.press;
/* t40127 @ 26.04.2016. */

import com.tofas.core.common.repository.TfVEmployeeRepository;
import com.tofas.yky.enums.PressFirmType;
import com.tofas.yky.enums.PressLossType;
import com.tofas.yky.model.decoratorbases.impl.VSupplier;
import com.tofas.yky.model.dto.press.PressLossDto;
import com.tofas.yky.model.dto.press.config.PressParameters;
import com.tofas.yky.model.dto.press.detail.AbstractPressLossDetailDto;
import com.tofas.yky.model.dto.press.detail.PressLogisticsLossDetailDto;
import com.tofas.yky.model.dto.press.detail.PressQualityLossDetailDto;
import com.tofas.yky.model.losses.press.VPressOpenLosses;
import com.tofas.yky.model.losses.press.details.AbstractPressLoss;
import com.tofas.yky.model.losses.press.details.PressLogisticsLoss;
import com.tofas.yky.model.losses.press.details.PressQualityLoss;
import com.tofas.yky.service.press.PressLossService;
import com.tofas.yky.service.press.upload.TfExcelError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/loss/press/api")
public class PressLossController {


    @Autowired
    private PressLossService pressLossService;

    @Autowired
    private TfVEmployeeRepository tfVEmployeeRepository;

    @RequestMapping("/get-details")
    public List<AbstractPressLossDetailDto> returnDetails() {
        return pressLossService.returnDetails();
    }

    @RequestMapping("/get-errors")
    public List<TfExcelError> returnErrors() {
        return pressLossService.returnErrors();
    }

    @RequestMapping("/logistics/upload")
    public boolean uploadLogisticsFile(@RequestParam("files") MultipartFile file) {
        return pressLossService.uploadFile(file, PressLossType.LOGISTICS);
    }

    @RequestMapping("/quality/upload")
    public boolean uploadQualityFile(@RequestParam("files") MultipartFile file) {
        return pressLossService.uploadFile(file, PressLossType.QUALITY);
    }


    // UPDATE

    @RequestMapping("/update-details-q")
    public List<PressQualityLossDetailDto> updateDetailsQ(@RequestBody List<PressQualityLossDetailDto> lossDetails) {
        return pressLossService.updateDetailsQ(lossDetails);
    }

    @RequestMapping("/update-details-l")
    public List<PressLogisticsLossDetailDto> updateDetailsL(@RequestBody List<PressLogisticsLossDetailDto> lossDetails) {
        return pressLossService.updateDetailsL(lossDetails);
    }

    @RequestMapping("/easy-update-details-q")
    public List<PressQualityLoss> easyUpdateDetailsQ(@RequestBody List<PressQualityLoss> lossDetails) {
        return lossDetails;
    }

    @RequestMapping("/easy-update-details-l")
    public List<PressLogisticsLoss> easyUpdateDetailsL(@RequestBody List<PressLogisticsLoss> lossDetails) {
        return lossDetails;
    }

    @RequestMapping("/save-details-l")
    public boolean saveDetailsL(@RequestBody List<PressLogisticsLoss> lossDetails) {
        return pressLossService.saveDetailsL(lossDetails);
    }

    @RequestMapping("/save-details-q")
    public boolean saveDetailsQ(@RequestBody List<PressQualityLoss> lossDetails) {
        return pressLossService.saveDetailsQ(lossDetails);
    }

    // END UPDATE


    @RequestMapping("/save")
    public Long savePressLoss(@RequestBody PressLossDto pressLossDto) {
        return pressLossService.savePressLoss(pressLossDto);
    }


    @RequestMapping("/hrd-params")
    public List<Map<String, Object>> getHrdParams() {
        return pressLossService.getHrdParams();
    }


    @RequestMapping("/firm-types")
    public List<PressFirmType> getFirmTypes() {
        return new ArrayList<PressFirmType>(){{
            add(PressFirmType.FIRM);
            add(PressFirmType.INSURANCE);
            add(PressFirmType.LOGISTICS);
        }};
    }

    @RequestMapping("/suppliers")
    public List<VSupplier> getPressSuppliers() {
        return pressLossService.getPressSuppliers();
    }

    @RequestMapping("/atrsc-params")
    public List<String> getAtrscParams() {
        return new ArrayList<String>(){{
            add("A");
            add("T");
            add("R");
            add("S");
            add("C");
        }};
    }



    @RequestMapping("/list-open")
    public List<VPressOpenLosses> listOpenLosses() {
        return pressLossService.listOpenLosses();
    }

    @RequestMapping("/approve/{lossId}")
    public boolean approveLoss(@RequestBody List<AbstractPressLoss> details, @PathVariable("lossId") Long lossId) {
        return pressLossService.approveLoss(lossId, details);
    }


    // edit



    // edit


    // configs

    @RequestMapping("/get-parameters")
    public PressParameters getParameters() {
        return pressLossService.getParameters();
    }

    @RequestMapping("/set-parameters")
    public boolean setParameters(@RequestBody PressParameters pressParameters) {
        return pressLossService.setParameters(pressParameters);
    }

    // end configs
}
