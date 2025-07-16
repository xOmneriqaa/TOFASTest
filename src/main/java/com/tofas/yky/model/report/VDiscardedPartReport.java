package com.tofas.yky.model.report;

import com.tofas.yky.enums.LossState;
import com.tofas.yky.enums.LossType;
import com.tofas.yky.enums.ProductionSubLossType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Created by t40127 on 29.03.2017.
 */
@Entity
@Table(name = "V_DISCARDED_PART_REPORT", schema = "TFS_YKY")
public class VDiscardedPartReport {

    @Id
    private Long id;

    @Column(name = "LOSS_ID")
    private Long lossId;

    private String description;

    @Column(name = "LOSS_DATE")
    private Date lossDate;

    @Column(name = "LOSS_INSDATE")
    private Date lossInsDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "LOSS_STATE")
    private LossState lossState;

    @Column(name = "LAST_STATE_CHANGE")
    private Date lastStateChanges;

    @Column(name = "DISCARD_VOUCHER_NO")
    private String discardVoucherNo;

    private String disegno;

    private BigDecimal qty;

    @Column(name = "DISCARD_COST")
    private BigDecimal discardCost;

    @Enumerated(EnumType.STRING)
    @Column(name = "LOSS_TYPE")
    private LossType lossType;

    @Enumerated(EnumType.STRING)
    @Column(name = "SUB_TYPE")
    private ProductionSubLossType productionSubLossType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLossId() {
        return lossId;
    }

    public void setLossId(Long lossId) {
        this.lossId = lossId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LossState getLossState() {
        return lossState;
    }

    public void setLossState(LossState lossState) {
        this.lossState = lossState;
    }

    public Date getLastStateChanges() {
        return lastStateChanges;
    }

    public void setLastStateChanges(Date lastStateChanges) {
        this.lastStateChanges = lastStateChanges;
    }

    public String getDiscardVoucherNo() {
        return discardVoucherNo;
    }

    public void setDiscardVoucherNo(String discardVoucherNo) {
        this.discardVoucherNo = discardVoucherNo;
    }

    public String getDisegno() {
        return disegno;
    }

    public void setDisegno(String disegno) {
        this.disegno = disegno;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getDiscardCost() {
        return discardCost;
    }

    public void setDiscardCost(BigDecimal discardCost) {
        this.discardCost = discardCost;
    }

    public LossType getLossType() {
        return lossType;
    }

    public void setLossType(LossType lossType) {
        this.lossType = lossType;
    }

    public ProductionSubLossType getProductionSubLossType() {
        return productionSubLossType;
    }

    public void setProductionSubLossType(ProductionSubLossType productionSubLossType) {
        this.productionSubLossType = productionSubLossType;
    }

    public Date getLossDate() {
        return lossDate;
    }

    public void setLossDate(Date lossDate) {
        this.lossDate = lossDate;
    }

    public Date getLossInsDate() {
        return lossInsDate;
    }

    public void setLossInsDate(Date lossInsDate) {
        this.lossInsDate = lossInsDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VDiscardedPartReport that = (VDiscardedPartReport) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
