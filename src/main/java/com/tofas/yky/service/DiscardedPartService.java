package com.tofas.yky.service;
/* T40127 @ 16.10.2015. */

import com.tofas.core.common.utility.TfAuthUtility;
import com.tofas.yky.model.decoratorbases.decorators.VSupplierDecorator;
import com.tofas.yky.model.decoratorbases.impl.VSupplier;
import com.tofas.yky.model.dto.DiscardedPartAddBasePriceDto;
import com.tofas.yky.model.losses.discards.DiscardedPart;
import com.tofas.yky.repositories.DiscardedPartRepository;
import com.tofas.yky.repositories.VSupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class DiscardedPartService {

    @Resource private DiscardedPartRepository discardedPartRepository;
    @Resource private VSupplierRepository vSupplierRepository;

    public boolean saveDiscardPart(DiscardedPartAddBasePriceDto discardedPartDto) {
        DiscardedPart discardedPart = discardedPartRepository.findOne(discardedPartDto.getId());
        List<DiscardedPart> undefinedBasePrices = discardedPartRepository.findByPartDisegno(discardedPart.getPart().getDisegno());

        VSupplier supplier = vSupplierRepository.findBySapCode(discardedPartDto.getFirmCode());

        for (DiscardedPart undefinedBasePrice : undefinedBasePrices) {
            undefinedBasePrice.setBasePrice(discardedPartDto.getBasePrice());
            undefinedBasePrice.setPriceDate(discardedPartDto.getPriceDate());
            undefinedBasePrice.setProvidedBy(TfAuthUtility.getUsername());
            undefinedBasePrice.setSupplier(new VSupplierDecorator(discardedPartDto.getFirmCode(), supplier));

            discardedPartRepository.save(discardedPart);
        }



        return true;
    }

    public List<DiscardedPart> listDiscardedPartsWithoutBasePrice() {
        return discardedPartRepository.findByBasePriceIsNull();
    }


    public DiscardedPart getDiscardPart(Long id) {
        return discardedPartRepository.findOne(id);
    }

    public List<VSupplier> getSuppliers() {
        return vSupplierRepository.findBySapCodeIsNotNullOrderBySapCode();
    }
}
