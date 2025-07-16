package com.tofas.yky.model.losses.views;
/* T40127 @ 23.11.2015. */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateSerializer;
import com.tofas.core.common.serializer.TfJsonDateTimeSerializer;
import com.tofas.yky.enums.LossState;
import com.tofas.yky.enums.LossType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@MappedSuperclass
public abstract class VInvoicableLoss {

    @Id
    private Long id;

    @Column(name = "LOSS_TYPE")
    @Enumerated(value = EnumType.STRING)
    private LossType lossType;

    @JsonSerialize(using = TfJsonDateSerializer.class)
    @Column(name = "LOSS_DATE")
    private Date lossDate;

    @Column(name = "LOSS_DESCRIPTION")
    private String lossDescription;

    @Column(name = "INSDATE")
    private Timestamp insertedDate;

    @Column(name = "LOSS_STATE")
    @Enumerated(value = EnumType.STRING)
    private LossState lossState;

    @JsonSerialize(using = TfJsonDateTimeSerializer.class)
    @Column(name = "LOSS_STATE_DATE")
    private Timestamp lossStateDate;

    @Column(name = "SUPPLIER_CODE")
    private String supplierCode;

    @Column(name = "SUPPLIER_NAME")
    private String supplierName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LossType getLossType() {
        return lossType;
    }

    public void setLossType(LossType lossType) {
        this.lossType = lossType;
    }

    public Date getLossDate() {
        return lossDate;
    }

    public void setLossDate(Date lossDate) {
        this.lossDate = lossDate;
    }

    public Timestamp getInsertedDate() {
        return insertedDate;
    }

    public void setInsertedDate(Timestamp insertedDate) {
        this.insertedDate = insertedDate;
    }

    public LossState getLossState() {
        return lossState;
    }

    public void setLossState(LossState lossState) {
        this.lossState = lossState;
    }

    public Timestamp getLossStateDate() {
        return lossStateDate;
    }

    public void setLossStateDate(Timestamp lossStateDate) {
        this.lossStateDate = lossStateDate;
    }

    public String getLossDescription() {
        return lossDescription;
    }

    public void setLossDescription(String lossDescription) {
        this.lossDescription = lossDescription;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

	@Override
	public String toString() {
		return "VInvoicableLoss [id=" + id + ", lossType=" + lossType + ", lossDate=" + lossDate + ", lossDescription="
				+ lossDescription + ", insertedDate=" + insertedDate + ", lossState=" + lossState + ", lossStateDate="
				+ lossStateDate + ", supplierCode=" + supplierCode + ", supplierName=" + supplierName + "]";
	}
    
    
}
