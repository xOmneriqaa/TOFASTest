package com.tofas.yky.model.dto;

import com.tofas.yky.enums.ProductionSubLossType;
import com.tofas.yky.model.losses.production.duration.Step;

import java.util.ArrayList;
import java.util.List;

public class StepDto {

    private Long id;

    private String name;

    private List<String> productionSubLossTypes;

    public StepDto() {
        productionSubLossTypes = new ArrayList<>();
    }

    public StepDto(Step step) {
        this();

        this.id = step.getId();
        this.name = step.getName();

        for (ProductionSubLossType productionSubLossType : step.getProductionSubLossTypes()) {
            productionSubLossTypes.add(productionSubLossType.toString());
        }
    }

    public List<String> getProductionSubLossTypes() {
        return productionSubLossTypes;
    }

    public void setProductionSubLossTypes(List<String> productionSubLossTypes) {
        this.productionSubLossTypes = productionSubLossTypes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StepDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
