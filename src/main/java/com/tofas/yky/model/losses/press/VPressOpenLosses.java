package com.tofas.yky.model.losses.press;
/* t40127 @ 25.05.2016. */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateSerializer;
import com.tofas.core.common.serializer.TfJsonDateTimeSerializer;
import com.tofas.yky.enums.LossType;
import com.tofas.yky.enums.PressFirmType;
import com.tofas.yky.enums.PressLossType;
import com.tofas.yky.model.decoratorbases.decorators.VSupplierDecorator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "TFS_YKY", name = "V_PRESS_OPEN_LOSSES")
public class VPressOpenLosses {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "LOSS_TYPE")
    private LossType lossType;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRESS_LOSS_TYPE")
    private PressLossType pressLossType;

    @JsonSerialize(using = TfJsonDateSerializer.class)
    @Column(name = "LOSS_DATE")
    private Date lossDate;

    @Column(name = "DESCRIPTION")
    private String description;

    @JsonSerialize(using = TfJsonDateTimeSerializer.class)
    @Column(name = "INSDATE")
    private Date insertedDate;

    @Column(name = "INSBY")
    private String insertedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "FRM_INS_SHP")
    private PressFirmType firmType;

    @Column(name = "FIRM_DESCRIPTION")
    private String firmDescription;

    @Embedded
    private VSupplierDecorator supplier;


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

    public PressLossType getPressLossType() {
        return pressLossType;
    }

    public void setPressLossType(PressLossType pressLossType) {
        this.pressLossType = pressLossType;
    }

    public Date getLossDate() {
        return lossDate;
    }

    public void setLossDate(Date lossDate) {
        this.lossDate = lossDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getInsertedDate() {
        return insertedDate;
    }

    public void setInsertedDate(Date insertedDate) {
        this.insertedDate = insertedDate;
    }

    public String getInsertedBy() {
        return insertedBy;
    }

    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }

    public PressFirmType getFirmType() {
        return firmType;
    }

    public void setFirmType(PressFirmType firmType) {
        this.firmType = firmType;
    }

    public String getFirmDescription() {
        return firmDescription;
    }

    public void setFirmDescription(String firmDescription) {
        this.firmDescription = firmDescription;
    }

    public VSupplierDecorator getSupplier() {
        return supplier;
    }

    public void setSupplier(VSupplierDecorator supplier) {
        this.supplier = supplier;
    }
}
