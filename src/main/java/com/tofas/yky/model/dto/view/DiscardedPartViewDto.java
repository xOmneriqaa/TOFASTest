package com.tofas.yky.model.dto.view;
/* T40127 @ 18.10.2015. */

import com.tofas.yky.model.decoratorbases.IPart;
import com.tofas.yky.model.losses.discards.DiscardedPart;

import java.math.BigDecimal;

public class DiscardedPartViewDto {

    private IPart part;

    private BigDecimal qty;

    private BigDecimal basePrice;

    public DiscardedPartViewDto(DiscardedPart discardedPart) {
        this.part = discardedPart.getPart();
        this.qty = discardedPart.getQty();
        this.basePrice = discardedPart.getBasePrice();
    }

    public IPart getPart() {
        return part;
    }

    public void setPart(IPart part) {
        this.part = part;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }
}
