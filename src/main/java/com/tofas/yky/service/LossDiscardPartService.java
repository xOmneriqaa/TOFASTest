package com.tofas.yky.service;
/* T40127 @ 15.10.2015. */

import com.tofas.yky.model.VPartBasePrice;
import com.tofas.yky.repositories.DiscardedPartRepository;
import com.tofas.yky.repositories.VPartBasePriceRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LossDiscardPartService {

    @Resource private VPartBasePriceRepository vPartBasePriceRepository;

    @Resource private DiscardedPartRepository discardedPartRepository;

    public List<VPartBasePrice> getBasePricesForPart(String disegno) {
        return vPartBasePriceRepository.findByDisegno(disegno);
    }

    public boolean isDiscardedPartVocuherValid(Long voucherNo) {
        return discardedPartRepository.countByDiscardedPartVoucherNo(voucherNo) == 0;
    }
}
