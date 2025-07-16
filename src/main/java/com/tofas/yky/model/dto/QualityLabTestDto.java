package com.tofas.yky.model.dto;

public class QualityLabTestDto {

    private Long testId;

    private Double qty;

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "QualityLabTestDto{" +
                "qty=" + qty +
                ", testId=" + testId +
                '}';
    }
}
