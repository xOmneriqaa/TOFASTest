package com.tofas.yky.model.dto.view;
/* T40127 @ 18.10.2015. */

import com.tofas.yky.model.decoratorbases.IPart;
import com.tofas.yky.model.losses.discards.DiscardedPart;
import com.tofas.yky.model.losses.directdurations.LossDirectDuration;
import com.tofas.yky.model.losses.logistics.LogisticsLoss;

import java.util.ArrayList;
import java.util.List;

public class LogisticsLossViewDto extends AbstractLossViewDto {

    private Long qty;

    private Long sqpNo;

    private IPart part;

    private String logisticsLossType;

    private String logisticsAcceptencePoint;

    private String lossCode;

    private List<LossDirectDurationViewDto> directDurations;

    private List<DiscardedPartViewDto> discardedParts;

    public LogisticsLossViewDto(LogisticsLoss loss) {
        super(loss);

        this.qty = loss.getQty();
        this.sqpNo = loss.getSqpNo();
        this.part = loss.getPart();
        this.logisticsLossType = loss.getLogisticsLossType().getName();
        this.logisticsAcceptencePoint = loss.getLogisticsAcceptencePoint().getName();
        this.lossCode = loss.getLossCode().getDescription();

        directDurations = new ArrayList<>();
        discardedParts = new ArrayList<>();

        for(LossDirectDuration directDuration : loss.getDirectDurations()) {
            directDurations.add(new LossDirectDurationViewDto(directDuration));
        }

        for(DiscardedPart discardedPart : loss.getDiscardedParts()) {
            discardedParts.add(new DiscardedPartViewDto(discardedPart));
        }
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Long getSqpNo() {
        return sqpNo;
    }

    public void setSqpNo(Long sqpNo) {
        this.sqpNo = sqpNo;
    }

    public IPart getPart() {
        return part;
    }

    public void setPart(IPart part) {
        this.part = part;
    }

    public String getLogisticsLossType() {
        return logisticsLossType;
    }

    public void setLogisticsLossType(String logisticsLossType) {
        this.logisticsLossType = logisticsLossType;
    }

    public String getLogisticsAcceptencePoint() {
        return logisticsAcceptencePoint;
    }

    public void setLogisticsAcceptencePoint(String logisticsAcceptencePoint) {
        this.logisticsAcceptencePoint = logisticsAcceptencePoint;
    }

    public String getLossCode() {
        return lossCode;
    }

    public void setLossCode(String lossCode) {
        this.lossCode = lossCode;
    }

    public List<LossDirectDurationViewDto> getDirectDurations() {
        return directDurations;
    }

    public void setDirectDurations(List<LossDirectDurationViewDto> directDurations) {
        this.directDurations = directDurations;
    }

    public List<DiscardedPartViewDto> getDiscardedParts() {
        return discardedParts;
    }

    public void setDiscardedParts(List<DiscardedPartViewDto> discardedParts) {
        this.discardedParts = discardedParts;
    }

    @Override
    public String toString() {
        return "LogisticsLossForSupplierDto{" +
                "qty=" + qty +
                ", sqpNo=" + sqpNo +
                ", part=" + part +
                ", logisticsLossType='" + logisticsLossType + '\'' +
                ", logisticsAcceptencePoint='" + logisticsAcceptencePoint + '\'' +
                ", lossCode='" + lossCode + '\'' +
                ", directDurations=" + directDurations +
                ", discardedParts=" + discardedParts +
                '}';
    }
}
