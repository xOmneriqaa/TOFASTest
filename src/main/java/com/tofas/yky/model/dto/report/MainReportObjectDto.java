package com.tofas.yky.model.dto.report;
/* T40127 @ 14.11.2015. */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateSerializer;
import com.tofas.core.common.serializer.TfJsonDateTimeSerializer;
import com.tofas.yky.model.report.MainReportObject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MainReportObjectDto extends AbstractReportObjectDto {

	private Long id;

	private String supplierCode;

	private String supplierName;

	private String lossType;

	private String lossSubType;

	private String description;

	@JsonSerialize(using = TfJsonDateSerializer.class)
	private Date lossDate;

	private String lossState;

	@JsonSerialize(using = TfJsonDateSerializer.class)
	private Date lossStateDate;

	private String lossStateChanger;

	private String disegno;

	private String disegnoDesc;

	private String disegnoTableCode;

	private Long sqpNo;

	private String insertedBy;

	private String usrDept;

	private String model;

	private String tut;

	private String lossSource;

	//private BigDecimal otherCosts;

	@JsonSerialize(using = TfJsonDateTimeSerializer.class)
	private Date insDate;

	@JsonSerialize(using = TfJsonDateTimeSerializer.class)
	private Date approvalDate;
	
	private String approvalInsBy;

	private String qualityLabApprover;
	
	@JsonSerialize(using = TfJsonDateTimeSerializer.class)
	private Date objectionLossDate;

	//private ObjectionStatus objectionStatus;

	public MainReportObjectDto(MainReportObject mainReportObject, List<String> workTypes) {
		super(workTypes);
		this.id = mainReportObject.getId();
		this.supplierCode = mainReportObject.getSupplierCode();
		this.supplierName = mainReportObject.getSupplierName();
		this.lossType = mainReportObject.getLossType();
		this.lossSubType = mainReportObject.getLossSubType();
		this.description = mainReportObject.getDescription();
		this.lossDate = mainReportObject.getLossDate();
		this.lossState = mainReportObject.getLossState();
		this.lossStateDate = mainReportObject.getLossStateDate();
		this.lossStateChanger = mainReportObject.getLossStateChanger();
		this.discardedPartCost = mainReportObject.getDiscardedPartCost();
		this.qLabTestCost = mainReportObject.getqLabTestCost();
		this.otherCosts = mainReportObject.getOtherCosts();
		this.disegno = mainReportObject.getDisegno();
		this.disegnoDesc = mainReportObject.getDisegnoDesc();
		this.disegnoTableCode = mainReportObject.getDisegnoTableCode();
		this.sqpNo = mainReportObject.getSqpNo();
		this.usrDept = mainReportObject.getUsrDept();
		this.insertedBy = mainReportObject.getInsertedBy();
		this.model = mainReportObject.getModelCode();
		this.tut = mainReportObject.getTutCode();
		this.lossSource = mainReportObject.getLossSourceName();
		this.insDate = mainReportObject.getInsDate();
		this.approvalDate = mainReportObject.getApprovalDate();
		this.approvalInsBy=mainReportObject.getApprovalInsBy();
		this.objectionLossDate = mainReportObject.getObjectionLossDate();
		this.qualityLabApprover = this.setQualityLabApproverById(mainReportObject.getApproverRoleId());

		for (DurationDetailDto durationDetailDto : mainReportObject.getDurationDetails()) {
			if (!this.durationDetails.containsKey(durationDetailDto.getWorkTypeName())) {
				durationDetails.put(durationDetailDto.getWorkTypeName(), BigDecimal.ZERO);
				costDetails.put(durationDetailDto.getWorkTypeName(), BigDecimal.ZERO);
			}

			this.durationDetails.put(durationDetailDto.getWorkTypeName(),
					this.durationDetails.get(durationDetailDto.getWorkTypeName()).add(durationDetailDto.getDuration()));

			this.costDetails.put(durationDetailDto.getWorkTypeName(),
					this.costDetails.get(durationDetailDto.getWorkTypeName()).add(durationDetailDto.getCost()));
		}

	}

	public String getModel() {
		return model;
	}

	public String getTut() {
		return tut;
	}

	public String getLossSource() {
		return lossSource;
	}

	public Long getId() {
		return id;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public String getLossType() {
		return lossType;
	}

	public String getDescription() {
		return description;
	}

	public Date getLossDate() {
		return lossDate;
	}

	public String getLossState() {
		return lossState;
	}

	public Date getLossStateDate() {
		return lossStateDate;
	}

	public String getDisegno() {
		return disegno;
	}

	public String getDisegnoDesc() {
		return disegnoDesc;
	}

	public Long getSqpNo() {
		return sqpNo;
	}

	public String getUsrDept() {
		return usrDept;
	}

	public String getInsertedBy() {
		return insertedBy;
	}

	public String getLossStateChanger() {
		return lossStateChanger;
	}

	public String getLossSubType() {
		return lossSubType;
	}

	public String getDisegnoTableCode() {
		return disegnoTableCode;
	}

	public Date getInsDate() {
		return insDate;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public String getApprovalInsBy() {
		return approvalInsBy;
	}

	public Date getObjectionLossDate() {
		return objectionLossDate;
	}

	/*public ObjectionStatus getObjectionStatus() {
		return objectionStatus;
	}

	public BigDecimal getOtherCosts() {
		return otherCosts;
	}*/

	private String setQualityLabApproverById(Long approvelRoleId)
	{
		if(approvelRoleId != null)
		{
			if(approvelRoleId == 1)
				return "Montaj Masası";
			else if(approvelRoleId == 2)
				return "Numune Masası";
			else if(approvelRoleId == 3)
				return "Satış Sonrası Masası";
			else if(approvelRoleId == 4)
				return "Süspansiyon Masası";
			else if(approvelRoleId == 5)
				return "Gövde Masası";
		}

		return "";
	}

	public String getQualityLabApprover() {
		return qualityLabApprover;
	}

	public void setQualityLabApprover(String qualityLabApprover) {
		this.qualityLabApprover = qualityLabApprover;
	}
}
