package com.tofas.yky.model.report;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateDeSerializer;
import com.tofas.core.common.serializer.TfJsonDateSerializer;

@Entity
@Table(schema = "TFS_YKY", name = "V_SQP_REPORT")
public class SqpReportObject {

	@Id
	@Column(name = "VIEW_ID")
	private Integer viewId;
	
	@Column(name = "SQP")
	private Long sqp;

	@Column(name = "LINE")
	private Integer line;

	@Column(name = "APPROVAL_INS_BY")
	private String approvalInsBy;
	
	@Column(name = "SUPPLIER_NAME")
	private String supplierName;
	
	@Column(name = "COST_DETAIL")
	private String costDetail;

	@Column(name = "COST_ITEM")
	private String costItem;

	@Column(name = "COST_UNIT")
	private String costUnit;

	@Column(name = "COST_SUB_ITEM")
	private String costSubItem;

	@Column(name = "AMOUNT")
	private Double amount;
	
	@Column(name="UNIT")
	private String unit;
	
	@Column(name = "UNIT_COST_IN_EURO")
	private Double unitCostInEuro;

	@Column(name = "TOTAL_COST")
	private Double totalCost;
	
	@JsonSerialize(using = TfJsonDateSerializer.class)
    @Column(name = "LOSS_DATE")
    private Date lossDate;
	
	@JsonSerialize(using = TfJsonDateSerializer.class)
    @Column(name = "APPROVAL_DATE")
    private Date approvalDate;
	
	private String disegno;
	
	@Column(name = "MODEL_CODE")
    private String modelCode;
	
	@Column(name = "EURO_RATE")
	private Double euroRate;
	
	public Integer getViewId() {
		return viewId;
	}

	public void setViewId(Integer viewId) {
		this.viewId = viewId;
	}

	public Long getSqp() {
		return sqp;
	}

	public void setSqp(Long sqp) {
		this.sqp = sqp;
	}

	public Integer getLine() {
		return line;
	}

	public void setLine(Integer line) {
		this.line = line;
	}

	public String getCostDetail() {
		return costDetail;
	}

	public void setCostDetail(String costDetail) {
		this.costDetail = costDetail;
	}

	public String getCostItem() {
		return costItem;
	}

	public void setCostItem(String costItem) {
		this.costItem = costItem;
	}

	public String getCostUnit() {
		return costUnit;
	}

	public void setCostUnit(String costUnit) {
		this.costUnit = costUnit;
	}

	public String getCostSubItem() {
		return costSubItem;
	}

	public void setCostSubItem(String costSubItem) {
		this.costSubItem = costSubItem;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getUnitCostInEuro() {
		return unitCostInEuro;
	}

	public void setUnitCostInEuro(Double unitCostInEuro) {
		this.unitCostInEuro = unitCostInEuro;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public Date getLossDate() {
		return lossDate;
	}

	public void setLossDate(Date lossDate) {
		this.lossDate = lossDate;
	}

	public String getDisegno() {
		return disegno;
	}

	public void setDisegno(String disegno) {
		this.disegno = disegno;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public Double getEuroRate() {
		return euroRate;
	}

	public void setEuroRate(Double euroRate) {
		this.euroRate = euroRate;
	}

	public String getApprovalInsBy() {
		return approvalInsBy;
	}

	public void setApprovalInsBy(String approvalInsBy) {
		this.approvalInsBy = approvalInsBy;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	
	
	
	
	
}
