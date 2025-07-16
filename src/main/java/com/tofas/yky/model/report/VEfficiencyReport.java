package com.tofas.yky.model.report;
/* t40127 @ 14.06.2016. */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateTimeSerializer;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(schema = "TFS_YKY", name = "V_YKY_GRY_REPORT")
public class VEfficiencyReport {

    @Id
    private String id;

    @Column(name = "KAYIP_ID")
    private Long lossId;

    @JsonSerialize(using = TfJsonDateTimeSerializer.class)
    @Column(name = "KAYIP_TARIHI")
    private Date lossDate;

    @Column(name = "KAYIP_ACIKLAMA")
    private String lossDescription;

    @Column(name = "STATU")
    private String state;

    @Column(name = "STATU_TANIM")
    private String stateDef;

    private String tut;

    @Column(name = "VARDIYA")
    private String shift;

    @Column(name = "SURE")
    private BigDecimal duration;

    @JsonSerialize(using = TfJsonDateTimeSerializer.class)
    @Column(name = "TAMAMLANMA_TARIH")
    private Date finishDate;

    @Column(name = "KAYIP_KOD")
    private String lossCode;

    @JsonSerialize(using = TfJsonDateTimeSerializer.class)
    @Column(name = "FATURALANMA_TARIHI")
    private Date invoiceDate;

    @JsonSerialize(using = TfJsonDateTimeSerializer.class)
    @Column(name = "STATU_TARIH")
    private Date stateDate;

    @JsonSerialize(using = TfJsonDateTimeSerializer.class)
    @Column(name = "ACILMA_TARIHI")
    private Date openDate;

    @JsonSerialize(using = TfJsonDateTimeSerializer.class)
    @Column(name = "ONAY_TARIHI")
    private Date approveDate;

    @Column(name = "KAYIP_KAYNAGI")
    private String lossSource;

    @Column(name = "KAYIP_TURU")
    private String lossType;

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

    public Date getLossDate() {
        return lossDate;
    }

    public void setLossDate(Date lossDate) {
        this.lossDate = lossDate;
    }

    public String getLossDescription() {
        return lossDescription;
    }

    public void setLossDescription(String lossDescription) {
        this.lossDescription = lossDescription;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateDef() {
        return stateDef;
    }

    public void setStateDef(String stateDef) {
        this.stateDef = stateDef;
    }

    public String getTut() {
        return tut;
    }

    public void setTut(String tut) {
        this.tut = tut;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getLossCode() {
        return lossCode;
    }

    public void setLossCode(String lossCode) {
        this.lossCode = lossCode;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getStateDate() {
        return stateDate;
    }

    public void setStateDate(Date stateDate) {
        this.stateDate = stateDate;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getLossSource() {
        return lossSource;
    }

    public void setLossSource(String lossSource) {
        this.lossSource = lossSource;
    }

   

    public String getLossType() {
		return lossType;
	}

	public void setLossType(String lossType) {
		this.lossType = lossType;
	}

	@Override
    public String toString() {
        return "VEfficiencyReport{" +
                "id='" + id + '\'' +
                ", lossId=" + lossId +
                ", lossDate=" + lossDate +
                ", lossDescription='" + lossDescription + '\'' +
                ", state='" + state + '\'' +
                ", stateDef='" + stateDef + '\'' +
                ", tut='" + tut + '\'' +
                ", shift='" + shift + '\'' +
                ", duration=" + duration +
                ", finishDate=" + finishDate +
                ", lossCode='" + lossCode + '\'' +
                ", invoiceDate=" + invoiceDate +
                ", stateDate=" + stateDate +
                ", openDate=" + openDate +
                ", approveDate=" + approveDate +
                '}';
    }
}
