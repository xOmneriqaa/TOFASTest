package com.tofas.yky.model.dto.press.pos;
/* t40127 @ 26.04.2016. */

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateTimeDeSerializer;
import com.tofas.core.common.serializer.TfJsonDateTimeSerializer;
import com.tofas.yky.model.decoratorbases.decorators.VPartDecorator;
import com.tofas.yky.model.decoratorbases.decorators.VSupplierDecorator;
import com.tofas.yky.model.dto.VPartDto;
import com.tofas.yky.model.dto.VSupplierDto;
import com.tofas.yky.model.losses.press.pos.VPosRollDetail;

import java.math.BigDecimal;
import java.util.Date;

public class PressPosRollDetailDto {

    private String rollNo;

    private String customRollNo;

    private String shipName;

    private BigDecimal rollWeight;

    private String invoiceNo;

    @JsonDeserialize(using = TfJsonDateTimeDeSerializer.class)
    @JsonSerialize(using = TfJsonDateTimeSerializer.class)
    private Date invoiceDate;

    @JsonDeserialize(using = TfJsonDateTimeDeSerializer.class)
    @JsonSerialize(using = TfJsonDateTimeSerializer.class)
    private Date factoryEntranceDate;

    private VPartDto part;

    private VSupplierDto supplier;

    private PressPosDisegnoDetailDto disegnoDetail;

    public PressPosRollDetailDto() { }

    public PressPosRollDetailDto(VPosRollDetail rollDetail, BigDecimal basePrice) {
        this.rollNo = rollDetail.getRollNo();
        this.customRollNo = this.rollNo;
        this.shipName = rollDetail.getShipName();
        this.rollWeight = rollDetail.getRollWeight();
        this.invoiceNo = rollDetail.getInvoiceNo();
        this.invoiceDate = rollDetail.getInvoiceDate();
        this.factoryEntranceDate = rollDetail.getFactoryEntranceDate();

        VPartDecorator vPart = rollDetail.getPart();
        part = new VPartDto(vPart.getDisegno(), vPart.getDescTr());

        VSupplierDecorator vSupplier = rollDetail.getSupplier();
        supplier = new VSupplierDto(vSupplier .getSapCode(), vSupplier.getName());

        disegnoDetail = new PressPosDisegnoDetailDto(rollDetail.getDisegnoDetail(), basePrice);
    }

    public PressPosDisegnoDetailDto getDisegnoDetail() {
        return disegnoDetail;
    }

    public void setDisegnoDetail(PressPosDisegnoDetailDto disegnoDetail) {
        this.disegnoDetail = disegnoDetail;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public BigDecimal getRollWeight() {
        return rollWeight;
    }

    public void setRollWeight(BigDecimal rollWeight) {
        this.rollWeight = rollWeight;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getFactoryEntranceDate() {
        return factoryEntranceDate;
    }

    public void setFactoryEntranceDate(Date factoryEntranceDate) {
        this.factoryEntranceDate = factoryEntranceDate;
    }

    public VPartDto getPart() {
        return part;
    }

    public void setPart(VPartDto part) {
        this.part = part;
    }

    public VSupplierDto getSupplier() {
        return supplier;
    }

    public void setSupplier(VSupplierDto supplier) {
        this.supplier = supplier;
    }

    public String getCustomRollNo() {
        return customRollNo;
    }

    public void setCustomRollNo(String customRollNo) {
        this.customRollNo = customRollNo;
    }
}
