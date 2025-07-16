package com.tofas.yky.model.dto;
/* T40127 @ 16.10.2015. */

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tofas.core.common.serializer.TfJsonDateDeSerializer;

import java.math.BigDecimal;
import java.util.Date;

public class DiscardedPartAddBasePriceDto {

    private Long id;

    private String firmCode;

    private BigDecimal basePrice;

    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date priceDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirmCode() {
        return firmCode;
    }

    public void setFirmCode(String firmCode) {
        this.firmCode = firmCode;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public Date getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(Date priceDate) {
        this.priceDate = priceDate;
    }
}
