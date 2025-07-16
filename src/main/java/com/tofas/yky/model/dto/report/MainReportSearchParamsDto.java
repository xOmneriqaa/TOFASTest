package com.tofas.yky.model.dto.report;
/* T40127 @ 11.11.2015. */

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tofas.core.common.serializer.TfJsonDateDeSerializer;
import com.tofas.yky.enums.LossType;

import java.util.Date;
import java.util.List;

public class MainReportSearchParamsDto {

    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date stateChangeDateStart;

    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date stateChangeDateEnd;

    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date lossDateStart;

    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date lossDateEnd;

    //test
    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date approvalDateStart;

    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date approvalDateEnd;


    public Date getApprovalDateStart() {
        return approvalDateStart;
    }

    public void setApprovalDateStart(Date approvalDateStart) {
        this.approvalDateStart = approvalDateStart;
    }

    public Date getApprovalDateEnd() {
        return approvalDateEnd;
    }

    public void setApprovalDateEnd(Date approvalDateEnd) {
        this.approvalDateEnd = approvalDateEnd;
    }

    //test

    private String lossState;

    private String supplierCode;

    private String lossIds;

    private String sqpNo;

    private String lossType;

    private String disegno;

    private String model;

    private String tut;

    private Long lossSourceId;

    private Long approverRoleId;

    public Long getLossSourceId() {
        return lossSourceId;
    }

    public void setLossSourceId(Long lossSourceId) {
        this.lossSourceId = lossSourceId;
    }

    public String getTut() {
        return tut;
    }

    public void setTuts(String tut) {
        this.tut = tut;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDisegno() {
        return disegno;
    }

    public void setDisegno(String disegno) {
        this.disegno = disegno;
    }

    public String getLossType() {
        return lossType;
    }

    public void setLossType(String lossType) {
        this.lossType = lossType;
    }

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

    public Date getStateChangeDateStart() {
        return stateChangeDateStart;
    }

    public void setStateChangeDateStart(Date stateChangeDateStart) {
        this.stateChangeDateStart = stateChangeDateStart;
    }

    public Date getStateChangeDateEnd() {
        return stateChangeDateEnd;
    }

    public void setStateChangeDateEnd(Date stateChangeDateEnd) {
        this.stateChangeDateEnd = stateChangeDateEnd;
    }

    public String getLossState() {
        return lossState;
    }

    public void setLossState(String lossState) {
        this.lossState = lossState;
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

    public String getSqpNo() {
        return sqpNo;
    }

    public void setSqpNo(String sqpNo) {
        this.sqpNo = sqpNo;
    }

    public Long getApproverRoleId() {
        return approverRoleId;
    }

    public void setApproverRoleId(Long approverRoleId) {
        this.approverRoleId = approverRoleId;
    }

    @Override
	public String toString() {
		return "MainReportSearchParamsDto [stateChangeDateStart=" + stateChangeDateStart + ", stateChangeDateEnd="
				+ stateChangeDateEnd + ", lossDateStart=" + lossDateStart + ", lossDateEnd=" + lossDateEnd
				+ ", lossState=" + lossState + ", supplierCode=" + supplierCode + ", lossIds=" + lossIds + ", sqpNo="
				+ sqpNo + ", lossType=" + lossType + ", disegno=" + disegno + ", model=" + model + ", tut=" + tut
				+ ", lossSourceId=" + lossSourceId + "]";
	}
    
    
}
