package com.tofas.yky.model.additional;


import com.tofas.yky.model.losses.LossOtherCost;

import java.math.BigDecimal;
import java.util.Set;

public interface IOtherCostAddableLoss {

    void setOtherCosts(Set<LossOtherCost> otherCosts);

    Set<LossOtherCost> getOtherCosts();

    default BigDecimal getTotalOtherCosts() {
        return getOtherCosts().stream()
                .map(LossOtherCost::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
