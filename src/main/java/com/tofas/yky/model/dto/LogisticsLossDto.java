package com.tofas.yky.model.dto;

import com.tofas.yky.model.dto.additional.IDiscardedPartAddableDto;
import com.tofas.yky.model.dto.additional.IOtherCostAddableLossDto;
import com.tofas.yky.model.dto.additional.IWorkDurationAddableLossDto;
import com.tofas.yky.model.losses.LossOtherCost;

import java.util.List;

public class LogisticsLossDto extends AbstractLossDto
        implements IWorkDurationAddableLossDto, IDiscardedPartAddableDto, IOtherCostAddableLossDto {

    private Long sqpNo;

    private String supplier;

    private String disegno;

    private Long qty;

    private Long logisticsLossType;

    private Long logisticsAcceptencePoint;

    private Long lossCode;

    private List<WorkDurationDto> workDurations;

    private List<DiscardedPartDto> discardedParts;

    private List<LossOtherCost> otherCosts;

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

    public Long getLogisticsLossType() {
        return logisticsLossType;
    }

    public void setLogisticsLossType(Long logisticsLossType) {
        this.logisticsLossType = logisticsLossType;
    }

    public Long getLogisticsAcceptencePoint() {
        return logisticsAcceptencePoint;
    }

    public void setLogisticsAcceptencePoint(Long logisticsAcceptencePoint) {
        this.logisticsAcceptencePoint = logisticsAcceptencePoint;
    }

    public Long getLossCode() {
        return lossCode;
    }

    public void setLossCode(Long lossCode) {
        this.lossCode = lossCode;
    }

    @Override
    public List<WorkDurationDto> getWorkDurations() {
        return workDurations;
    }

    public void setWorkDurations(List<WorkDurationDto> workDurations) {
        this.workDurations = workDurations;
    }


    public List<DiscardedPartDto> getDiscardedParts() {
        return discardedParts;
    }

    public void setDiscardedParts(List<DiscardedPartDto> discardedParts) {
        this.discardedParts = discardedParts;
    }

    public List<LossOtherCost> getOtherCosts() {
        return otherCosts;
    }

    public void setOtherCosts(List<LossOtherCost> otherCosts) {
        this.otherCosts = otherCosts;
    }

    @Override
    public String toString() {
        return "LogisticsLossDto{" +
                "sqpNo=" + sqpNo +
                ", supplier='" + supplier + '\'' +
                ", disegno='" + disegno + '\'' +
                ", qty=" + qty +
                ", logisticsLossType=" + logisticsLossType +
                ", logisticsAcceptencePoint=" + logisticsAcceptencePoint +
                ", lossCode=" + lossCode +
                ", workDurations=" + workDurations +
                ", discardedParts=" + discardedParts +
                '}';
    }

}
