package com.tofas.yky.model.losses.production;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tofas.yky.enums.ProductionSubLossType;
import com.tofas.yky.model.Model;
import com.tofas.yky.model.additional.*;
import com.tofas.yky.model.decoratorbases.decorators.VLossCodeDecorator;
import com.tofas.yky.model.decoratorbases.decorators.VPartDecorator;
import com.tofas.yky.model.decoratorbases.decorators.VSupplierDecorator;
import com.tofas.yky.model.decoratorbases.decorators.VTutDecorator;
import com.tofas.yky.model.losses.LossOtherCost;
import com.tofas.yky.model.losses.discards.DiscardedPart;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.model.losses.LossSource;
import com.tofas.yky.model.losses.production.duration.LossStdStepDuration;
import com.tofas.yky.model.losses.production.duration.LossStepDuration;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_PRODUCTION_LOSSES")
@DiscriminatorValue("PRODUCTION")
@PrimaryKeyJoinColumn(name = "LOSS_ID")
public class ProductionLoss extends Loss implements ILaborCostAddableLoss, IDiscardedPartAddableLoss,
    ISupplierReferencedLoss, IPartReferencedLoss, IOtherCostAddableLoss {

    @Enumerated(EnumType.STRING)
    @Column(name = "SUB_TYPE")
    private ProductionSubLossType subType;

    @Embedded
    private VPartDecorator part;

    @Embedded
    private VSupplierDecorator supplier;

    @Column
    private Long qty;

    @Column(name = "SQP_NO")
    private Long sqpNo;

    @Column(name = "QUALITY_TRACE_ID")
    private String qualityTraceId;

    @Column(name = "SHIFT_CODE")
    private String shiftCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LOSS_SOURCE_ID")
    private LossSource lossSource;

    @Embedded
    private VLossCodeDecorator lossCode;

    @Embedded
    private VTutDecorator tut;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MODEL_CODE")
    private Model model;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "YKY_PRD_LOSS_EFCT_TS", schema = "TFS_YKY",
            joinColumns = {@JoinColumn(name = "LOSS_ID")}
    )
    @Column(name = "TUT_CODE")
    private Set<String> effectedTuts;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "loss", cascade = CascadeType.ALL)
    private Set<LossStdStepDuration> standartDurations;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "loss", cascade = CascadeType.ALL)
    private Set<LossStepDuration> stepDurations;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "loss", cascade = CascadeType.ALL)
    private Set<LossCompletion> lossCompletions;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "loss", cascade = CascadeType.ALL)
    private Set<DiscardedPart> discardedParts;

    @LazyCollection(LazyCollectionOption.FALSE)
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

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Set<DiscardedPart> getDiscardedParts() {
        return discardedParts;
    }

    public void setDiscardedParts(Set<DiscardedPart> discardedParts) {
        this.discardedParts = discardedParts;
    }

    public ProductionSubLossType getSubType() {
        return subType;
    }

    public void setSubType(ProductionSubLossType subType) {
        this.subType = subType;
    }

    @Override
    public VPartDecorator getPart() {
        return part;
    }

    public void setPart(VPartDecorator part) {
        this.part = part;
    }

    @Override
    public VSupplierDecorator getSupplier() {
        return supplier;
    }

    public void setSupplier(VSupplierDecorator supplier) {
        this.supplier = supplier;
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

    public String getQualityTraceId() {
        return qualityTraceId;
    }

    public void setQualityTraceId(String qualityTraceId) {
        this.qualityTraceId = qualityTraceId;
    }

    public String getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(String shiftCode) {
        this.shiftCode = shiftCode;
    }

    public LossSource getLossSource() {
        return lossSource;
    }

    public void setLossSource(LossSource lossSource) {
        this.lossSource = lossSource;
    }

    public VLossCodeDecorator getLossCode() {
        return lossCode;
    }

    public void setLossCode(VLossCodeDecorator lossCode) {
        this.lossCode = lossCode;
    }

    public VTutDecorator getTut() {
        return tut;
    }

    public void setTut(VTutDecorator tut) {
        this.tut = tut;
    }

    public Set<String> getEffectedTuts() {
        return effectedTuts;
    }

    public void setEffectedTuts(Set<String> effectedTuts) {
        this.effectedTuts = effectedTuts;
    }

    public Set<LossStdStepDuration> getStandartDurations() {
        return standartDurations;
    }

    public void setStandartDurations(Set<LossStdStepDuration> standartDurations) {
        this.standartDurations = standartDurations;
    }

    public Set<LossStepDuration> getStepDurations() {
        return stepDurations;
    }

    public void setStepDurations(Set<LossStepDuration> stepDurations) {
        this.stepDurations = stepDurations;
    }

    public Set<LossCompletion> getLossCompletions() {
        return lossCompletions;
    }

    public void setLossCompletions(Set<LossCompletion> lossCompletions) {
        this.lossCompletions = lossCompletions;
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
    public BigDecimal getTotalCost() {
        BigDecimal cost = getTotalWorkDurationCost();

        cost = cost.add(this.totalDiscardedPartCost).add(getTotalOtherCosts());

        return cost;
    }

    @Override
    public BigDecimal getTotalWorkDurationCost() {
        BigDecimal cost = BigDecimal.ZERO;

        for(LossStdStepDuration stdStepDuration : this.standartDurations) {
            cost = cost.add(stdStepDuration.getTotalCost());
        }

        for(LossStepDuration stepDuration : this.stepDurations) {
            cost = cost.add(stepDuration.getTotalCost());
        }

        return cost;
    }

    @Override
    public BigDecimal getTotalDiscardedPartCost() {
        return totalDiscardedPartCost;
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
    public String toString() {
        return "ProductionLoss{" +
                "subType=" + subType +
                ", part=" + part +
                ", supplier=" + supplier +
                ", qty=" + qty +
                ", sqpNo=" + sqpNo +
                ", qualityTraceId='" + qualityTraceId + '\'' +
                ", shiftCode='" + shiftCode + '\'' +
                ", lossSource=" + lossSource +
                ", lossCode=" + lossCode +
                ", tut=" + tut +
                ", effectedTuts=" + effectedTuts +
                ", standartDurations=" + standartDurations +
                ", stepDurations=" + stepDurations +
                ", lossCompletions=" + lossCompletions +
                '}';
    }
}
