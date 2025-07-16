package com.tofas.yky.service.additional.impl;
/* T40127 @ 16.10.2015. */

import com.tofas.yky.model.VPartBasePrice;
import com.tofas.yky.model.additional.IDiscardedPartAddableLoss;
import com.tofas.yky.model.decoratorbases.decorators.VPartDecorator;
import com.tofas.yky.model.decoratorbases.decorators.VSupplierDecorator;
import com.tofas.yky.model.decoratorbases.impl.VPart;
import com.tofas.yky.model.decoratorbases.impl.VSupplier;
import com.tofas.yky.model.dto.DiscardedPartBasePriceDto;
import com.tofas.yky.model.dto.DiscardedPartDto;
import com.tofas.yky.model.dto.additional.IDiscardedPartAddableDto;
import com.tofas.yky.model.losses.discards.DiscardedPart;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.repositories.VPartBasePriceRepository;
import com.tofas.yky.repositories.VPartsRepository;
import com.tofas.yky.repositories.VSupplierRepository;
import com.tofas.yky.service.additional.IDiscardedPartAddableLossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DiscardedPartAddableLossService implements IDiscardedPartAddableLossService {

    private final VPartBasePriceRepository vPartBasePriceRepository;
    private final VPartsRepository vPartsRepository;
    private final VSupplierRepository vSupplierRepository;

    @Autowired
    public DiscardedPartAddableLossService(VPartBasePriceRepository vPartBasePriceRepository, VPartsRepository vPartsRepository, VSupplierRepository vSupplierRepository) {
        this.vPartBasePriceRepository = vPartBasePriceRepository;
        this.vPartsRepository = vPartsRepository;
        this.vSupplierRepository = vSupplierRepository;
    }

    @Override
    public void addDiscardedParts(IDiscardedPartAddableDto dto, IDiscardedPartAddableLoss loss) {
        List<DiscardedPartDto> discardedPartDtos = dto.getDiscardedParts();
        Set<DiscardedPart> discardedParts = new HashSet<>();

        for(DiscardedPartDto discardedPartDto : discardedPartDtos) {
            DiscardedPartBasePriceDto baseUnitPriceDto = discardedPartDto.getBaseUnitPrice();

            DiscardedPart discardedPart = new DiscardedPart();
            discardedPart.setLoss((Loss) loss);
            discardedPart.setQty(BigDecimal.valueOf(discardedPartDto.getQty()));
            discardedPart.setBaseUnit(discardedPartDto.getBaseUnitType());
            discardedPart.setDiscardedPartVoucherNo(discardedPartDto.getDiscardedPartVoucherNo());

            VPart part = vPartsRepository.findByDisegno(discardedPartDto.getDisegno());
            discardedPart.setPart(new VPartDecorator(discardedPartDto.getDisegno(), part));

            if(baseUnitPriceDto != null && baseUnitPriceDto.getFirmCode() != null) {
                List<VPartBasePrice> basePrices = vPartBasePriceRepository
                        .findByDisegnoAndFirmCode(discardedPartDto.getDisegno(), baseUnitPriceDto.getFirmCode());

                if(basePrices.size() == 1) {
                    VSupplier supplier = vSupplierRepository.findBySapCode(baseUnitPriceDto.getFirmCode());

                    VPartBasePrice partBasePrice = basePrices.get(0);

                    discardedPart.setBasePrice(partBasePrice.getBaseUnitPriceInTl());
                    discardedPart.setPriceDate(partBasePrice.getPriceDate());
                    discardedPart.setBaseUnit(partBasePrice.getMeasureType());


                    discardedPart.setSupplier(new VSupplierDecorator(baseUnitPriceDto.getFirmCode(), supplier));
                }
            }

            discardedParts.add(discardedPart);
        }

        loss.setDiscardedParts(discardedParts);
    }
}
