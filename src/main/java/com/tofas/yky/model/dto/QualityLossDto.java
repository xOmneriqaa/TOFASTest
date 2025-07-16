package com.tofas.yky.model.dto;

import com.tofas.yky.model.dto.additional.IDiscardedPartAddableDto;
import com.tofas.yky.model.dto.additional.IOtherCostAddableLossDto;
import com.tofas.yky.model.dto.additional.IWorkDurationAddableLossDto;
import com.tofas.yky.model.losses.LossOtherCost;

import java.util.List;

public class QualityLossDto extends AbstractLossDto
            implements IWorkDurationAddableLossDto, IDiscardedPartAddableDto, IOtherCostAddableLossDto {

    private Long lossCode;

    private String model;

    private String disegno;

    private Long qty;

    private Long qualityLossTable;

    private String qualityTraceId;

    private Long sqpNo;

    private String supplier;

    private List<WorkDurationDto> workDurations;

    private List<DiscardedPartDto> discardedParts;

    private List<LossOtherCost> otherCosts;


    @Override
    public List<DiscardedPartDto> getDiscardedParts() {
        return discardedParts;
    }

    public void setDiscardedParts(List<DiscardedPartDto> discardedParts) {
        this.discardedParts = discardedParts;
    }

    public Long getLossCode() {
        return lossCode;
    }

    public void setLossCode(Long lossCode) {
        this.lossCode = lossCode;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDisegno() {
        return disegno;
    }

    public void setDisegno(String disegno) {
        this.disegno = disegno;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Long getQualityLossTable() {
        return qualityLossTable;
    }

    public void setQualityLossTable(Long qualityLossTable) {
        this.qualityLossTable = qualityLossTable;
    }

    public String getQualityTraceId() {
        return qualityTraceId;
    }

    public void setQualityTraceId(String qualityTraceId) {
        this.qualityTraceId = qualityTraceId;
    }

    public Long getSqpNo() {
        return sqpNo;
    }

    public void setSqpNo(Long sqpNo) {
        this.sqpNo = sqpNo;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public List<WorkDurationDto> getWorkDurations() {
        return workDurations;
    }

    public void setWorkDurations(List<WorkDurationDto> workDurations) {
        this.workDurations = workDurations;
    }

    public List<LossOtherCost> getOtherCosts() {
        return otherCosts;
    }

    public void setOtherCosts(List<LossOtherCost> otherCosts) {
        this.otherCosts = otherCosts;
    }

    @Override
    public String toString() {
        return "QualityLossDto{" +
                "lossCode=" + lossCode +
                ", lossDate=" + lossDate +
                ", model='" + model + '\'' +
                ", disegno='" + disegno + '\'' +
                ", qty=" + qty +
                ", qualityLossTable=" + qualityLossTable +
                ", qualityTraceId='" + qualityTraceId + '\'' +
                ", sqpNo=" + sqpNo +
                ", supplier='" + supplier + '\'' +
                ", workDurations=" + workDurations +
                '}';
    }
}
