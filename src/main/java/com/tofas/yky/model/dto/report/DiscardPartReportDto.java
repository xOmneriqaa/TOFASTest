package com.tofas.yky.model.dto.report;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateSerializer;
import com.tofas.core.common.serializer.TfJsonDateTimeSerializer;
import com.tofas.yky.model.report.VDiscardedPartReport;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by t40127 on 29.03.2017.
 */
public class DiscardPartReportDto {

    private Long id;

    private Long lossId;

    private String description;

    @JsonSerialize(using = TfJsonDateSerializer.class)
    private Date lossDate;

    @JsonSerialize(using = TfJsonDateTimeSerializer.class)
    private Date lossInsDate;

    private String lossState;

    @JsonSerialize(using = TfJsonDateTimeSerializer.class)
    private Date lastStateChanges;

    private String discardVoucherNo;

    private String disegno;

    private BigDecimal qty;

    private BigDecimal discardCost;

    private String lossType;

    private String subType;

    public DiscardPartReportDto() {}

    public DiscardPartReportDto(VDiscardedPartReport discardedPartReport) {
        this.id = discardedPartReport.getId();
        this.lossId = discardedPartReport.getLossId();
        this.description = discardedPartReport.getDescription();
        this.lossState = discardedPartReport.getLossState() == null ? "" :
                discardedPartReport.getLossState().toString();
        this.lastStateChanges = discardedPartReport.getLastStateChanges();
        this.discardVoucherNo = discardedPartReport.getDiscardVoucherNo();
        this.disegno = discardedPartReport.getDisegno();
        this.qty = discardedPartReport.getQty();
        this.discardCost = discardedPartReport.getDiscardCost();
        this.lossType = discardedPartReport.getLossType() == null ? "" :
                discardedPartReport.getLossType().toString();
        this.subType = discardedPartReport.getProductionSubLossType() == null ? "" :
                discardedPartReport.getProductionSubLossType().toString();
        this.lossDate = discardedPartReport.getLossDate();
        this.lossInsDate = discardedPartReport.getLossInsDate();
    }

    public Long getId() {
        return id;
    }

    public Long getLossId() {
        return lossId;
    }

    public String getDescription() {
        return description;
    }

    public String getLossState() {
        return lossState;
    }

    public Date getLastStateChanges() {
        return lastStateChanges;
    }

    public String getDiscardVoucherNo() {
        return discardVoucherNo;
    }

    public String getDisegno() {
        return disegno;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public BigDecimal getDiscardCost() {
        return discardCost;
    }

    public String getLossType() {
        return lossType;
    }

    public String getSubType() {
        return subType;
    }

    public Date getLossDate() {
        return lossDate;
    }

    public Date getLossInsDate() {
        return lossInsDate;
    }
}
