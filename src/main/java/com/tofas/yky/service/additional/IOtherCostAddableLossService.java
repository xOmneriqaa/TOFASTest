package com.tofas.yky.service.additional;

import com.tofas.yky.model.additional.IOtherCostAddableLoss;
import com.tofas.yky.model.dto.additional.IOtherCostAddableLossDto;

/**
 * Created by t40127 on 31.03.2017.
 */
public interface IOtherCostAddableLossService {

    void addOtherCosts(IOtherCostAddableLossDto dto, IOtherCostAddableLoss loss);

}
