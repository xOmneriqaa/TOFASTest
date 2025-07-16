package com.tofas.yky.model;
/* T40127 @ 16.11.2015. */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonTimestampSerializer;
import com.tofas.yky.enums.BaseUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(schema = "TFS_YKY", name = "V_UNPRICED_PARTS")
public class VUnpricedPart {

    @Id
    String id;

    @Column(name = "LOSS_ID")
    private Long lossId;

    @Column(name = "DISCARDEDPART_ID")
    private Long discardedPartId;

    private String disegno;

    private BigDecimal qty;

    @Column(name = "BASE_UNIT")
    private String baseUnit;

    @Column(name = "LOSS_DESCRIPTION")
    private String lossDescription;

    @Column(name = "LOSS_TYPE")
    private String lossType;

    @JsonSerialize(using = TfJsonTimestampSerializer.class)
    @Column(name = "INSDATE")
    private Timestamp insDate;

    public String getBaseUnitText() {
        return BaseUnit.getUnit(baseUnit).getLabel();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getLossId() {
        return lossId;
    }

    public void setLossId(Long lossId) {
        this.lossId = lossId;
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

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public String getLossDescription() {
        return lossDescription;
    }

    public void setLossDescription(String lossDescription) {
        this.lossDescription = lossDescription;
    }

    public String getLossType() {
        return lossType;
    }

    public void setLossType(String lossType) {
        this.lossType = lossType;
    }

    public Timestamp getInsDate() {
        return insDate;
    }

    public void setInsDate(Timestamp insDate) {
        this.insDate = insDate;
    }

    public Long getDiscardedPartId() {
        return discardedPartId;
    }

    public void setDiscardedPartId(Long discardedPartId) {
        this.discardedPartId = discardedPartId;
    }
}
