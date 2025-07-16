package com.tofas.yky.model.dto.press;
/* t40127 @ 26.04.2016. */

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateDeSerializer;
import com.tofas.core.common.serializer.TfJsonDateSerializer;
import com.tofas.yky.enums.PressFirmType;
import com.tofas.yky.enums.PressLossType;
import com.tofas.yky.model.dto.VSupplierDto;
import com.tofas.yky.model.dto.press.detail.AbstractPressLossDetailDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PressLossDto {

    private PressLossType pressLossType;

    private PressFirmType firmType;

    private VSupplierDto supplierDto;

    private String firmDescription;

    private List<AbstractPressLossDetailDto> details;

    private String description;

    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    @JsonSerialize(using = TfJsonDateSerializer.class)
    private Date lossDate;

    public PressLossDto() {
        details = new ArrayList<>();
    }

    public PressLossType getPressLossType() {
        return pressLossType;
    }

    public void setPressLossType(PressLossType pressLossType) {
        this.pressLossType = pressLossType;
    }

    public PressFirmType getFirmType() {
        return firmType;
    }

    public void setFirmType(PressFirmType firmType) {
        this.firmType = firmType;
    }

    public VSupplierDto getSupplierDto() {
        return supplierDto;
    }

    public void setSupplierDto(VSupplierDto supplierDto) {
        this.supplierDto = supplierDto;
    }

    public String getFirmDescription() {
        return firmDescription;
    }

    public void setFirmDescription(String firmDescription) {
        this.firmDescription = firmDescription;
    }

    public List<AbstractPressLossDetailDto> getDetails() {
        return details;
    }

    public void setDetails(List<AbstractPressLossDetailDto> details) {
        this.details = details;
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
}
