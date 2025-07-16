package com.tofas.yky.model.report;
/* T40127 @ 11.11.2015. */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateSerializer;
import com.tofas.yky.model.dto.report.DurationDetailDto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(schema = "TFS_YKY", name = "V_LOSS_MAIN_REPORT")
public class MainReportObject {

    @Id
    @Column(name = "LOSS_ID")
    private Long id;

    @Column(name = "SUPPLIER_CODE")
    private String supplierCode;

    @Column(name = "SUPPLIER_NAME")
    private String supplierName;

    @Column(name = "LOSS_TYPE")
    private String lossType;

    @Column(name = "LOSS_SUB_TYPE")
    private String lossSubType;

    @Column
    private String description;

    @JsonSerialize(using = TfJsonDateSerializer.class)
    @Column(name = "LOSS_DATE")
    private Date lossDate;

    @Column(name = "LOSS_STATE")
    private String lossState;

    @Column(name = "LOSS_STATE_DATE")
    private Date lossStateDate;

    @Column(name = "LOSS_STATE_CHANGER")
    private String lossStateChanger;

    @JsonIgnore
    @Column(name = "DURATION_DESCRIPTION")
    private String durationDescription;

    @Column(name = "DISCARDED_PART_COST")
    private BigDecimal discardedPartCost;

    @Column(name = "QLAB_TEST_COST")
    private BigDecimal qLabTestCost;

    @Column(name = "OTHER_COSTS")
    private BigDecimal otherCosts;

    private String disegno;

    @Column(name = "DISEGNO_DESC")
    private String disegnoDesc;

    @Column(name = "DISEGNO_TABLE_CODE")
    private String disegnoTableCode;

    @Column(name = "SQP_NO")
    private Long sqpNo;

    @Column(name = "INSBY")
    private String insertedBy;

    @Column(name = "USR_DEPT")
    private String usrDept;

    @Column(name = "MODEL_CODE")
    private String modelCode;

    @Column(name = "TUT_CODE")
    private String tutCode;

    @Column(name = "LOSS_SOURCE_ID")
    private Long lossSourceId;

    @Column(name = "LOSS_SOURCE_NAME")
    private String lossSourceName;

    @Column(name = "INSDATE")
    private Date insDate;

    @Column(name = "APPROVAL_DATE")
    private Date approvalDate;
    
    @Column(name="APPROVAL_INS_BY")
    private String approvalInsBy;
    
    @Column(name = "OBJECTION_LOSS_DATE")
    private Date objectionLossDate;

    @Column(name = "APPROVER_ROLE_ID")
    private Long approverRoleId;
    
    /*@Enumerated(EnumType.STRING)
    @Column(name="OBJECTION_STATUS")
    private ObjectionStatus objectionStatus;*/

    @Transient
    private List<DurationDetailDto> durationDetails;

    public List<DurationDetailDto> getDurationDetails() {
        return durationDetails;
    }

    @PostLoad
    public void init() {
        durationDetails = DurationDetailDto.generateDurationDetailDto(durationDescription);
    }

    public BigDecimal getTotalCost() {
        BigDecimal totalCost = new BigDecimal(0d);

        for(DurationDetailDto durationDetail : durationDetails) {
            totalCost = totalCost.add(durationDetail.getCost());
        }

        if(discardedPartCost != null) {
            totalCost = totalCost.add(this.discardedPartCost);
        }

        if(qLabTestCost != null) {
            totalCost = totalCost.add(this.qLabTestCost);
        }
        
        if(otherCosts !=null){
        	totalCost=totalCost.add(this.otherCosts);
        }

        return totalCost;
    }

    public BigDecimal getTotalTime() {
        BigDecimal totalTime = new BigDecimal(0d);

        for(DurationDetailDto durationDetail : durationDetails) {
            totalTime = totalTime.add(durationDetail.getDuration());
        }

        return totalTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLossType() {
        return lossType;
    }

    public void setLossType(String lossType) {
        this.lossType = lossType;
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

    public String getLossState() {
        return lossState;
    }

    public void setLossState(String lossState) {
        this.lossState = lossState;
    }

    public Date getLossStateDate() {
        return lossStateDate;
    }

    public void setLossStateDate(Date lossStateDate) {
        this.lossStateDate = lossStateDate;
    }

    public String getDurationDescription() {
        return durationDescription;
    }

    public void setDurationDescription(String durationDescription) {
        this.durationDescription = durationDescription;
    }

    public BigDecimal getDiscardedPartCost() {
        return discardedPartCost;
    }

    public void setDiscardedPartCost(BigDecimal discardedPartCost) {
        this.discardedPartCost = discardedPartCost;
    }

    public BigDecimal getqLabTestCost() {
        return qLabTestCost;
    }

    public void setqLabTestCost(BigDecimal qLabTestCost) {
        this.qLabTestCost = qLabTestCost;
    }

    public String getDisegno() {
        return disegno;
    }

    public void setDisegno(String disegno) {
        this.disegno = disegno;
    }

    public String getDisegnoDesc() {
        return disegnoDesc;
    }

    public void setDisegnoDesc(String disegnoDesc) {
        this.disegnoDesc = disegnoDesc;
    }

    public Long getSqpNo() {
        return sqpNo;
    }

    public void setSqpNo(Long sqpNo) {
        this.sqpNo = sqpNo;
    }

    public String getUsrDept() {
        return usrDept;
    }

    public void setUsrDept(String usrDept) {
        this.usrDept = usrDept;
    }

    public String getInsertedBy() {
        return insertedBy;
    }

    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getTutCode() {
        return tutCode;
    }

    public void setTutCode(String tutCode) {
        this.tutCode = tutCode;
    }

    public void setDurationDetails(List<DurationDetailDto> durationDetails) {
        this.durationDetails = durationDetails;
    }

    public Long getLossSourceId() {
        return lossSourceId;
    }

    public void setLossSourceId(Long lossSourceId) {
        this.lossSourceId = lossSourceId;
    }

    public String getLossSourceName() {
        return lossSourceName;
    }

    public void setLossSourceName(String lossSourceName) {
        this.lossSourceName = lossSourceName;
    }

    public String getLossStateChanger() {
        return lossStateChanger;
    }

    public void setLossStateChanger(String lossStateChanger) {
        this.lossStateChanger = lossStateChanger;
    }

    public String getLossSubType() {
        return lossSubType;
    }

    public void setLossSubType(String lossSubType) {
        this.lossSubType = lossSubType;
    }

    public String getDisegnoTableCode() {
        return disegnoTableCode;
    }

    public void setDisegnoTableCode(String disegnoTableCode) {
        this.disegnoTableCode = disegnoTableCode;
    }

    public Date getInsDate() {
        return insDate;
    }

    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getApprovalInsBy() {
		return approvalInsBy;
	}

	public void setApprovalInsDate(String approvalInsBy) {
		this.approvalInsBy = approvalInsBy;
	}

	public BigDecimal getOtherCosts() {
        return otherCosts;
    }

    public void setOtherCosts(BigDecimal otherCosts) {
        this.otherCosts = otherCosts;
    }

	public Date getObjectionLossDate() {
		return objectionLossDate;
	}

	public void setObjectionLossDate(Date objectionLossDate) {
		this.objectionLossDate = objectionLossDate;
	}

    public Long getApproverRoleId() {
        return approverRoleId;
    }

    public void setApproverRoleId(Long approverRoleId) {
        this.approverRoleId = approverRoleId;
    }

    /*public ObjectionStatus getObjectionStatus() {
		return objectionStatus;
	}

	public void setObjectionStatus(ObjectionStatus objectionStatus) {
		this.objectionStatus = objectionStatus;
	}*/
}
