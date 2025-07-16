package com.tofas.yky.model.losses.qualitylab;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tofas.yky.enums.LossState;
import com.tofas.yky.model.additional.*;
import com.tofas.yky.model.decoratorbases.decorators.VPartDecorator;
import com.tofas.yky.model.decoratorbases.decorators.VSupplierDecorator;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.model.losses.QualityLabApprover;
import com.tofas.yky.model.losses.directdurations.LossDirectDuration;
import com.tofas.yky.model.losses.LossStateChange;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_QUALITY_LAB_LOSSES")
@DiscriminatorValue("QUALITY_LAB")
@PrimaryKeyJoinColumn(name = "LOSS_ID")
public class QualityLabLoss extends Loss implements ILaborCostAddableLoss, IWorkDurationAddableLoss, IQualityLabTestAddableLoss, ISupplierReferencedLoss, IPartReferencedLoss {

    @Embedded
    private VPartDecorator part;

    @OneToMany(mappedBy = "loss", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<QualityLabLossTest> qualityLabTests;

    @OneToMany(mappedBy = "loss", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<LossDirectDuration> directDurations;

    @Embedded
    private VSupplierDecorator supplier;

    @Column(name = "SQP_NO")
    private Long sqpNo;

    @Column(name = "REQUEST_NO")
    private String requestNo;

    @Column(name = "SQP_OPEN")
    private String willSqpOpen;

    @JsonIgnore
    @Transient
    private BigDecimal totalTestCost;

    @ManyToOne
    @JoinColumn(name = "approver_role_id")
    private QualityLabApprover qualityLabApprover;

    @PostLoad
    public void calculateTotalDiscardedPartCost() {
        totalTestCost = BigDecimal.ZERO;

        for (QualityLabLossTest qualityLabTest : qualityLabTests) {
            totalTestCost = totalTestCost.add(qualityLabTest.getTotalCost());
        }
    }

    // TODO : currentState approved atılması engellendi. MUHAMMED EREN 14/08/2021
   /* @PrePersist
    public void beforeInsert() {
        super.beforeInsert();
        LossStateChange lossStateChange = new LossStateChange();
        lossStateChange.setLoss(this);
        lossStateChange.setLossState(LossState.APPROVED);
        stateChanges.add(lossStateChange);
    }*/

    @Override
    public BigDecimal getTotalCost() {
        BigDecimal cost = getTotalWorkDurationCost();

        for(QualityLabLossTest test : this.qualityLabTests) {
            cost = cost.add(test.getTotalCost());
        }

        return cost;
    }

    @Override
    public BigDecimal getTotalWorkDurationCost() {
        BigDecimal cost = BigDecimal.ZERO;

        for(LossDirectDuration directDuration: this.directDurations) {
            cost = cost.add(directDuration.getTotalCost());
        }

        return cost;
    }

    @Override
    public void putApprovedMailParams(Map<String, Object> map) {
        super.putApprovedMailParams(map);
        map.put("disegno", part.getDisegno());
        map.put("disegnoDescTr", part.getDescTr());
        map.put("disegnoDescEn", part.getDescEn());
    }

    @Override
    public Set<QualityLabLossTest> getQualityLabTests() {
        return qualityLabTests;
    }

    @Override
    public void setQualityLabTests(Set<QualityLabLossTest> qualityLabTests) {
        this.qualityLabTests = qualityLabTests;
    }

    @Override
    public Set<LossDirectDuration> getDirectDurations() {
        return directDurations;
    }

    @Override
    public void setDirectDurations(Set<LossDirectDuration> directDurations) {
        this.directDurations = directDurations;
    }

    public BigDecimal getTotalTestCost() {
        return totalTestCost;
    }

    public void setTotalTestCost(BigDecimal totalTestCost) {
        this.totalTestCost = totalTestCost;
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

    public Long getSqpNo() { return sqpNo; }

    public void setSqpNo(Long sqpNo) { this.sqpNo = sqpNo; }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getWillSqpOpen() {
        return willSqpOpen;
    }

    public void setWillSqpOpen(String willSqpOpen) {
        this.willSqpOpen = willSqpOpen;
    }

    public QualityLabApprover getQualityLabApprover() {
        return qualityLabApprover;
    }

    public void setQualityLabApprover(QualityLabApprover qualityLabApprover) {
        this.qualityLabApprover = qualityLabApprover;
    }

    @Override
    public String toString() {
        return "QualityLabLoss{" +
                "part=" + part +
                ", qualityLabTests=" + qualityLabTests +
                ", directDurations=" + directDurations +
                ", sqpNo=" + sqpNo +
                ", requestNo=" + requestNo +
                ", willSqpOpen=" + willSqpOpen +
                ", supplier=" + supplier +
                ", qualityLabApprover=" + qualityLabApprover +
                '}';
    }


}
