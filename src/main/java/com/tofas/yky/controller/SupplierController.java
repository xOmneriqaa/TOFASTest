package com.tofas.yky.controller;
/* T40127 @ 16.10.2015. */

import com.tofas.yky.components.SupplierUserInfo;
import com.tofas.yky.model.dto.LossObjectionDto;
import com.tofas.yky.model.losses.views.VSupplierBasedLoss;
import com.tofas.yky.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/supplier/api")
public class SupplierController {

    @Autowired private SupplierUserInfo supplierUserInfo;

    @Autowired private SupplierService supplierService;


    @RequestMapping("/open/logistics")
    public List<VSupplierBasedLoss> listOpenLogisticsLosses(HttpServletRequest request) {
        return supplierService.listNewOpenedLogisticsLosses(supplierUserInfo.getSupplier());
    }

    @RequestMapping("/open/production")
    public List<VSupplierBasedLoss> listOpenProductionLosses() {
        return supplierService.listNewOpenedProductionLosses(supplierUserInfo.getSupplier());
    }

    @RequestMapping("/open/quality")
    public List<VSupplierBasedLoss> listOpenQualityLosses() {
        return supplierService.listNewOpenQualityLosses(supplierUserInfo.getSupplier());
    }

    @RequestMapping("/open/quality-lab")
    public List<VSupplierBasedLoss> listOpenQualityLabLosses() {
        return supplierService.listNewOpenQualityLabLosses(supplierUserInfo.getSupplier());
    }


    @RequestMapping("/make-objection")
    public boolean makeObjection(@RequestBody LossObjectionDto dto){
        return supplierService.makeObjection(dto);
    }


}
