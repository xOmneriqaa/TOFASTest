package com.tofas.yky.model.losses.views;
/* T40127 @ 19.11.2015. */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateSerializer;
import com.tofas.yky.enums.LossState;
import com.tofas.yky.enums.LossType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "TFS_YKY", name = "V_SUPPLIER_BASED_LOSSES")
public class VSupplierBasedLoss {

    @Id
    private Long id;

    private String description;

    @JsonSerialize(using = TfJsonDateSerializer.class)
    @Column(name = "LOSS_DATE")
    private Date lossDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "LOSS_TYPE")
    private LossType lossType;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "LOSS_STATE")
    private LossState lossState;

    @Column(name = "SUPPLIER_CODE")
    private String supplierCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLossDate() {
        return lossDate;
    }

    public void setLossDate(Date lossDate) {
        this.lossDate = lossDate;
    }

    public LossType getLossType() {
        return lossType;
    }

    public void setLossType(LossType lossType) {
        this.lossType = lossType;
    }

    public LossState getLossState() {
        return lossState;
    }

    public void setLossState(LossState lossState) {
        this.lossState = lossState;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }
}
