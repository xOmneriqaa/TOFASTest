package com.tofas.yky.controller.loss;
/* T40127 @ 02.11.2015. */

import com.tofas.yky.model.losses.production.ProductionLoss;
import com.tofas.yky.model.losses.views.VInCompletedLosses;
import com.tofas.yky.model.losses.views.VUnApprovedLosses;
import com.tofas.yky.service.ProductionLossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/production-loss/api")
public class ProductionLossController {

    private final ProductionLossService productionLossService;

    @Autowired
    public ProductionLossController(ProductionLossService productionLossService) {
        this.productionLossService = productionLossService;
    }


    @RequestMapping("/list-non-approved")
    public List<VUnApprovedLosses> listNonApprovedLosses() {
        return productionLossService.listNontApprovedLosses();
    }

    @RequestMapping("/list-in-completed")
    public List<VInCompletedLosses> listNonCompletedLosses() {
        return productionLossService.listNonCompletedLosses();
    }


}
