package com.tofas.yky.controller;
/* T40127 @ 16.10.2015. */

import com.tofas.yky.model.VUnpricedPart;
import com.tofas.yky.model.decoratorbases.impl.VSupplier;
import com.tofas.yky.model.dto.DiscardedPartAddBasePriceDto;
import com.tofas.yky.model.losses.discards.DiscardedPart;
import com.tofas.yky.repositories.VUnpricedPartsRepository;
import com.tofas.yky.service.DiscardedPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/buyer/api")
public class BuyerController {


    @Autowired
    private DiscardedPartService discardedPartService;

    @Resource
    VUnpricedPartsRepository vUnpricedPartsRepository;

    @RequestMapping("/d-parts-wo-base-price")
    public List<VUnpricedPart> listDiscardedPartsWithoutBasePrice() {
        return vUnpricedPartsRepository.findAll();
    }

    @RequestMapping("/get-discard-part/{id}")
    public DiscardedPart getDiscardPart(@PathVariable("id") Long id) {
        return discardedPartService.getDiscardPart(id);
    }

    @RequestMapping("/get-suppliers")
    public List<VSupplier> getSuppliers() {
        return discardedPartService.getSuppliers();
    }

    @RequestMapping("/save-discard-part")
    public boolean saveDiscardPart(@RequestBody DiscardedPartAddBasePriceDto discardedPart) {
        return discardedPartService.saveDiscardPart(discardedPart);
    }

}
