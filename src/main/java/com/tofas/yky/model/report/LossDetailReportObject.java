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
@Table(schema = "TFS_YKY", name = "V_LOSS_DETAIL_REPORT")
public class LossDetailReportObject {

	@Id
	@Column(name = "VIEW_ID")
	private Integer viewId;

	@Column(name = "LOSS_ID")
	private Long id;

	@Column(name = "LOSS_TYPE")
	private String lossType;

	@Column(name = "LOSS_SUB_TYPE")
	private String lossSubType;

	// @JsonSerialize(using = TfJsonDateSerializer.class)
	// @JsonDeserialize(using=TfJsonDateDeSerializer.class)
	// @Column(name = "LOSS_DATE")
	// private Date lossDate;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "SUPPLIER_CODE")
	private String supplierCode;

	@Column(name = "SUPPLIER_NAME")
	private String supplierName;
	
	@Column(name="SQP_NO")
	private Long sqpNo;
	
	@Column(name = "STEP_NAME_CACHED")
	private String stepNameCached;

	@Column(name = "BLUE_COLLAR_WAGE_CACHED")
	private Double blueCollarWageCached;

	@Column(name = "QTY")
	private Integer qty;

	@Column(name = "STEP_DURATION_CACHED")
	private Double stepDurationCached;

	public LossDetailReportObject() {

	}

	public LossDetailReportObject(Integer viewId, Long id, String lossType, String lossSubType, String description,
			String supplierCode, String supplierName, String stepNameCached, Double blueCollarWageCached, Integer qty,
			Double stepDurationCached) {
		super();
		this.viewId = viewId;
		this.id = id;
		this.lossType = lossType;
		this.lossSubType = lossSubType;
		this.description = description;
		this.supplierCode = supplierCode;
		this.supplierName = supplierName;
		this.stepNameCached = stepNameCached;
		this.blueCollarWageCached = blueCollarWageCached;
		this.qty = qty;
		this.stepDurationCached = stepDurationCached;
	}

	
	
	public Integer getViewId() {
		return viewId;
	}

	public void setViewId(Integer viewId) {
		this.viewId = viewId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLossType() {
		return lossType;
	}

	public void setLossType(String lossType) {
		this.lossType = lossType;
	}

	public String getLossSubType() {
		return lossSubType;
	}

	public void setLossSubType(String lossSubType) {
		this.lossSubType = lossSubType;
	}

	// public Date getLossDate() {
	// return lossDate;
	// }
	//
	//
	// public void setLossDate(Date lossDate) {
	// this.lossDate = lossDate;
	// }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	
	public Long getSqpNo() {
		return sqpNo;
	}

	public void setSqpNo(Long sqpNo) {
		this.sqpNo = sqpNo;
	}

	public String getStepNameCached() {
		return stepNameCached;
	}

	public void setStepNameCached(String stepNameCached) {
		this.stepNameCached = stepNameCached;
	}

	public Double getBlueCollarWageCached() {
		return blueCollarWageCached;
	}

	public void setBlueCollarWageCached(Double blueCollarWageCached) {
		this.blueCollarWageCached = blueCollarWageCached;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Double getStepDurationCached() {
		return stepDurationCached;
	}

	public void setStepDurationCached(Double stepDurationCached) {
		this.stepDurationCached = stepDurationCached;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blueCollarWageCached == null) ? 0 : blueCollarWageCached.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		// result = prime * result + ((lossDate == null) ? 0 :
		// lossDate.hashCode());
		result = prime * result + ((lossSubType == null) ? 0 : lossSubType.hashCode());
		result = prime * result + ((lossType == null) ? 0 : lossType.hashCode());
		result = prime * result + ((qty == null) ? 0 : qty.hashCode());
		result = prime * result + ((stepDurationCached == null) ? 0 : stepDurationCached.hashCode());
		result = prime * result + ((stepNameCached == null) ? 0 : stepNameCached.hashCode());
		result = prime * result + ((supplierCode == null) ? 0 : supplierCode.hashCode());
		result = prime * result + ((supplierName == null) ? 0 : supplierName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LossDetailReportObject other = (LossDetailReportObject) obj;
		if (blueCollarWageCached == null) {
			if (other.blueCollarWageCached != null)
				return false;
		} else if (!blueCollarWageCached.equals(other.blueCollarWageCached))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		// if (lossDate == null) {
		// if (other.lossDate != null)
		// return false;
		// } else if (!lossDate.equals(other.lossDate))
		// return false;
		if (lossSubType == null) {
			if (other.lossSubType != null)
				return false;
		} else if (!lossSubType.equals(other.lossSubType))
			return false;
		if (lossType == null) {
			if (other.lossType != null)
				return false;
		} else if (!lossType.equals(other.lossType))
			return false;
		if (qty == null) {
			if (other.qty != null)
				return false;
		} else if (!qty.equals(other.qty))
			return false;
		if (stepDurationCached == null) {
			if (other.stepDurationCached != null)
				return false;
		} else if (!stepDurationCached.equals(other.stepDurationCached))
			return false;
		if (stepNameCached == null) {
			if (other.stepNameCached != null)
				return false;
		} else if (!stepNameCached.equals(other.stepNameCached))
			return false;
		if (supplierCode == null) {
			if (other.supplierCode != null)
				return false;
		} else if (!supplierCode.equals(other.supplierCode))
			return false;
		if (supplierName == null) {
			if (other.supplierName != null)
				return false;
		} else if (!supplierName.equals(other.supplierName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LossDetailReportObject [id=" + id + ", lossType=" + lossType + ", lossSubType=" + lossSubType
				+ ", description=" + description + ", supplierCode=" + supplierCode + ", supplierName=" + supplierName
				+ ", stepNameCached=" + stepNameCached + ", blueCollarWageCached=" + blueCollarWageCached + ", qty="
				+ qty + ", stepDurationCached=" + stepDurationCached + "]";
	}

}
