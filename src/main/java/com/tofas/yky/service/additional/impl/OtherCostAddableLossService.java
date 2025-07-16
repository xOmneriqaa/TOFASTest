package com.tofas.yky.service.additional.impl;

import com.tofas.yky.model.additional.IOtherCostAddableLoss;
import com.tofas.yky.model.dto.additional.IOtherCostAddableLossDto;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.model.losses.LossOtherCost;
import com.tofas.yky.service.additional.IOtherCostAddableLossService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OtherCostAddableLossService implements IOtherCostAddableLossService {

    @Override
    public void addOtherCosts(IOtherCostAddableLossDto dto, IOtherCostAddableLoss loss) {
        loss.setOtherCosts(
                dto.getOtherCosts().stream()
                        .map(ocd -> new LossOtherCost((Loss) loss, ocd.getDescription(), ocd.getCost()))
                        .collect(Collectors.toSet()));
    }
}
