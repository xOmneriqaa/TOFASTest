package com.tofas.yky.model.dto;

import com.tofas.yky.model.dto.additional.IDiscardedPartAddableDto;
import com.tofas.yky.model.dto.additional.IOtherCostAddableLossDto;
import com.tofas.yky.model.losses.LossOtherCost;

import java.util.List;

public class ProductionLossDto extends AbstractLossDto
        implements IDiscardedPartAddableDto, IOtherCostAddableLossDto {

    private Long sqpNo;

    private String supplier;

    private String disegno;

    private Long qty;

    private Long lossCode;

    private Long lossSource;

    private String lossType;

    private String shiftCode;

    private String qualityTraceId;

    private String model;

    private List<String> effectedTuts;

    private String tut;

    private List<StepForLossDto> steps;

    private List<DiscardedPartDto> discardedParts;

    private List<LossOtherCost> otherCosts;


    public List<DiscardedPartDto> getDiscardedParts() {
        return discardedParts;
    }

    public void setDiscardedParts(List<DiscardedPartDto> discardedParts) {
        this.discardedParts = discardedParts;
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

    public Long getLossCode() {
        return lossCode;
    }

    public void setLossCode(Long lossCode) {
        this.lossCode = lossCode;
    }

    public Long getLossSource() {
        return lossSource;
    }

    public void setLossSource(Long lossSource) {
        this.lossSource = lossSource;
    }

    public String getLossType() {
        return lossType;
    }

    public void setLossType(String lossType) {
        this.lossType = lossType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(String shiftCode) {
        this.shiftCode = shiftCode;
    }

    public String getQualityTraceId() {
        return qualityTraceId;
    }

    public void setQualityTraceId(String qualityTraceId) {
        this.qualityTraceId = qualityTraceId;
    }

    public List<String> getEffectedTuts() {
        return effectedTuts;
    }

    public void setEffectedTuts(List<String> effectedTuts) {
        this.effectedTuts = effectedTuts;
    }

    public String getTut() {
        return tut;
    }

    public void setTut(String tut) {
        this.tut = tut;
    }

    public List<StepForLossDto> getSteps() {
        return steps;
    }

    public void setSteps(List<StepForLossDto> steps) {
        this.steps = steps;
    }

    public List<LossOtherCost> getOtherCosts() {
        return otherCosts;
    }

    public void setOtherCosts(List<LossOtherCost> otherCosts) {
        this.otherCosts = otherCosts;
    }

    @Override
    public String toString() {
        return "ProductionLossDto{" +
                "sqpNo=" + sqpNo +
                ", supplier='" + supplier + '\'' +
                ", disegno='" + disegno + '\'' +
                ", qty=" + qty +
                ", lossCode=" + lossCode +
                ", lossSource=" + lossSource +
                ", lossType='" + lossType + '\'' +
                ", model='" + model + '\'' +
                ", shiftCode='" + shiftCode + '\'' +
                ", qualityTraceId='" + qualityTraceId + '\'' +
                ", effectedTuts=" + effectedTuts +
                ", tut='" + tut + '\'' +
                ", steps=" + steps +
                '}';
    }
}
