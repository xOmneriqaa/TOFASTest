package com.tofas.yky.model.additional;
/* T40127 @ 16.10.2015. */

import com.fasterxml.jackson.annotation.JsonGetter;
import com.tofas.yky.model.losses.discards.DiscardedPart;

import java.math.BigDecimal;
import java.util.Set;

public interface IDiscardedPartAddableLoss {

    Set<DiscardedPart> getDiscardedParts();

    void setDiscardedParts(Set<DiscardedPart> discardedParts);

    @JsonGetter("totalDiscardedPartCost")
    BigDecimal getTotalDiscardedPartCost();
}
