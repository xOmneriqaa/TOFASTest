package com.tofas.yky.model.dto.press.report;
/* t40127 @ 09.06.2016. */

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateTimeDeSerializer;
import com.tofas.core.common.serializer.TfJsonDateTimeSerializer;
import com.tofas.yky.enums.LossState;

import java.util.Date;

public class PressMainReportSearchParameters {

    @JsonSerialize(using = TfJsonDateTimeSerializer.class)
    @JsonDeserialize(using = TfJsonDateTimeDeSerializer.class)
    private Date lossDateStart;

    @JsonSerialize(using = TfJsonDateTimeSerializer.class)
    @JsonDeserialize(using = TfJsonDateTimeDeSerializer.class)
    private Date lossDateEnd;

    private String supplierCode;

    private String lossIds;

    private String rollNo;

    private String lossState;

    private String disegno;

    public PressMainReportSearchParameters() { }

    public Date getLossDateStart() {
        return lossDateStart;
    }

    public void setLossDateStart(Date lossDateStart) {
        this.lossDateStart = lossDateStart;
    }

    public Date getLossDateEnd() {
        return lossDateEnd;
    }

    public void setLossDateEnd(Date lossDateEnd) {
        this.lossDateEnd = lossDateEnd;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getLossIds() {
        return lossIds;
    }

    public void setLossIds(String lossIds) {
        this.lossIds = lossIds;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getLossState() {
        return lossState;
    }

    public void setLossState(String lossState) {
        this.lossState = lossState;
    }

    public String getDisegno() {
        return disegno;
    }

    public void setDisegno(String disegno) {
        this.disegno = disegno;
    }
}
