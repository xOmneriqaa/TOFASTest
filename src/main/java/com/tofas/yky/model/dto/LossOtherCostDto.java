package com.tofas.yky.model.dto;


import java.math.BigDecimal;

public class LossOtherCostDto {

    private String description;

    private BigDecimal cost;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
