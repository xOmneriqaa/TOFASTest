package com.tofas.yky.model.losses.logistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tofas.yky.enums.LossState;
import com.tofas.yky.model.additional.*;
import com.tofas.yky.model.decoratorbases.decorators.VLossCodeDecorator;
import com.tofas.yky.model.decoratorbases.decorators.VPartDecorator;
import com.tofas.yky.model.decoratorbases.decorators.VSupplierDecorator;
import com.tofas.yky.model.losses.LossOtherCost;
import com.tofas.yky.model.losses.discards.DiscardedPart;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.model.losses.directdurations.LossDirectDuration;
import com.tofas.yky.model.losses.LossStateChange;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_LOGISTICS_LOSSES")
@DiscriminatorValue("LOGISTICS")
@PrimaryKeyJoinColumn(name = "LOSS_ID")
public class LogisticsLoss extends Loss implements ILaborCostAddableLoss, IWorkDurationAddableLoss,
    IDiscardedPartAddableLoss, ISupplierReferencedLoss, IPartReferencedLoss, IOtherCostAddableLoss {

    @Embedded
    private VSupplierDecorator supplier;

    @Column
    private Long qty;

    @Column(name = "SQP_NO")
    private Long sqpNo;

    @Embedded
    private VPartDecorator part;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LOGISTICS_LOSS_TYPE_ID")
    private LogisticsLossType logisticsLossType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LOGISTICS_ACP_PNT_ID")
    private LogisticsAcceptencePoint logisticsAcceptencePoint;

    @Embedded
    private VLossCodeDecorator lossCode;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "loss", cascade = CascadeType.ALL)
    private Set<LossDirectDuration> directDurations;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "loss", cascade = CascadeType.ALL)
    private Set<DiscardedPart> discardedParts;

    @OneToMany(mappedBy = "loss", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<LossOtherCost> otherCosts;

    @JsonIgnore
    @Transient
    private BigDecimal totalDiscardedPartCost;

    @PostLoad
    public void calculateTotalDiscardedPartCost() {
        totalDiscardedPartCost = BigDecimal.ZERO;

        for (DiscardedPart discardedPart : discardedParts) {
            totalDiscardedPartCost = totalDiscardedPartCost.add(discardedPart.getTotalCost());
        }
    }

    @PrePersist
    @Override
    public void beforeInsert() {
        super.beforeInsert();
        LossStateChange lossStateChange = new LossStateChange();
        lossStateChange.setLoss(this);
        lossStateChange.setLossState(LossState.APPROVED);

        stateChanges.add(lossStateChange);
    }

    @Override
    public void putApprovedMailParams(Map<String, Object> map) {
        super.putApprovedMailParams(map);
        map.put("sqp", sqpNo);
        map.put("disegno", part.getDisegno());
        map.put("disegnoDescTr", part.getDescTr());
        map.put("disegnoDescEn", part.getDescEn());
        map.put("qty", qty);
    }

    @Override
    public BigDecimal getTotalCost() {
        BigDecimal cost = getTotalWorkDurationCost();

        cost = cost.add(totalDiscardedPartCost)
                .add(getTotalOtherCosts());


        return cost;
    }

    @Override
    public BigDecimal getTotalWorkDurationCost() {
        BigDecimal cost = BigDecimal.ZERO;

        for(LossDirectDuration directDuration : directDurations) {
            cost = cost.add(directDuration.getTotalCost());
        }

        return cost;
    }

    @Override
    public Set<DiscardedPart> getDiscardedParts() {
        return discardedParts;
    }

    @Override
    public void setDiscardedParts(Set<DiscardedPart> discardedParts) {
        this.discardedParts = discardedParts;
    }

    @Override
    public BigDecimal getTotalDiscardedPartCost() {
        return totalDiscardedPartCost;
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

    public LogisticsLossType getLogisticsLossType() {
        return logisticsLossType;
    }

    public void setLogisticsLossType(LogisticsLossType logisticsLossType) {
        this.logisticsLossType = logisticsLossType;
    }

    public LogisticsAcceptencePoint getLogisticsAcceptencePoint() {
        return logisticsAcceptencePoint;
    }

    public void setLogisticsAcceptencePoint(LogisticsAcceptencePoint logisticsAcceptencePoint) {
        this.logisticsAcceptencePoint = logisticsAcceptencePoint;
    }

    @Override
    public VSupplierDecorator getSupplier() {
        return supplier;
    }

    public void setSupplier(VSupplierDecorator supplier) {
        this.supplier = supplier;
    }

    @Override
    public VPartDecorator getPart() {
        return part;
    }

    public void setPart(VPartDecorator part) {
        this.part = part;
    }

    public VLossCodeDecorator getLossCode() {
        return lossCode;
    }

    public void setLossCode(VLossCodeDecorator lossCode) {
        this.lossCode = lossCode;
    }

    @Override
    public Set<LossDirectDuration> getDirectDurations() {
        return directDurations;
    }

    @Override
    public void setDirectDurations(Set<LossDirectDuration> directDurations) {
        this.directDurations = directDurations;
    }

    @Override
    public void setOtherCosts(Set<LossOtherCost> otherCosts) {
        this.otherCosts = otherCosts;
    }

    @Override
    public Set<LossOtherCost> getOtherCosts() {
        return otherCosts;
    }

    @Override
    public String toString() {
        return "LogisticsLoss{" +
                "supplier=" + supplier +
                ", qty=" + qty +
                ", sqpNo=" + sqpNo +
                ", part=" + part +
                ", logisticsLossType=" + logisticsLossType +
                ", logisticsAcceptencePoint=" + logisticsAcceptencePoint +
                ", lossCode=" + lossCode +
                ", directDurations=" + directDurations +
                '}';
    }


}
