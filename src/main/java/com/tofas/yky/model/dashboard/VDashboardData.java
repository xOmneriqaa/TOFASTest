package com.tofas.yky.model.dashboard;
/* T40127 @ 26.11.2015. */

import com.tofas.yky.enums.dashboard.DashboardCostType;
import com.tofas.yky.model.dto.dashboard.SupplierInfoDto;
import com.tofas.yky.model.dto.report.DurationDetailDto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(schema = "TFS_YKY", name = "V_DASHBOARD_DATA")
public class VDashboardData {

    @Id
    @Column(name = "LOSS_ID")
    private Long lossId;

    @Column(name = "LOSS_DATE")
    private Date lossDate;

    @Column(name = "LOSS_TYPE")
    private String lossTypeStr;

    @Column(name = "SUB_TYPE")
    private String productionLossSubTypeStr;

    @Column(name = "OBJ_COUNT")
    private Long objectionCount;

    @Column(name = "SUP_INFO")
    private String supplierInfo;

    @Column(name = "LOSS_STATE")
    private String lossStateStr;

    @Column(name = "WORK_TYPE_COST")
    private BigDecimal workTypeCost;

    @Column(name = "DISCARDED_PART_COST")
    private BigDecimal discardedPartCost;

    @Column(name = "QLAB_TEST_COST")
    private BigDecimal qLabTestCost;

    @Column(name = "OTHER_COSTS")
    private BigDecimal otherCosts;

    @Column(name = "TOTAL_COST")
    private BigDecimal totalCost;

    @Column(name = "DURATION_INFO")
    private String durationInfo;

    @Transient
    private List<DurationDetailDto> durationDetails;

    @Transient
    private SupplierInfoDto supplierInfoObject;

    @PostLoad
    public void postLoad() {
        String[] supplierInfoArray = supplierInfo.split(";");
        if(supplierInfoArray != null && supplierInfoArray.length >= 3) {
            String sapCode = supplierInfoArray[0];
            String name = supplierInfoArray[1];
            boolean isExtraSerie = supplierInfoArray[2] != null && supplierInfoArray[2].equals("Y");
            boolean isFiatPolo = supplierInfoArray.length == 4 && supplierInfoArray[3] != null && supplierInfoArray[3].trim().length() > 0;

            supplierInfoObject = new SupplierInfoDto(
                    sapCode,
                    name,
                    isExtraSerie,
                    isFiatPolo
            );
        } else {
            supplierInfoObject = new SupplierInfoDto();
        }

        durationDetails = DurationDetailDto.generateDurationDetailDto(durationInfo);
    }

    public String getTotalLossType() {
        if(productionLossSubTypeStr != null) {
            return lossTypeStr + "." + productionLossSubTypeStr;
        } else {
            return lossTypeStr;
        }
    }

    public SupplierInfoDto getSupplierInfoObject() {
        return supplierInfoObject;
    }

    public void setSupplierInfoObject(SupplierInfoDto supplierInfoObject) {
        this.supplierInfoObject = supplierInfoObject;
    }

    public Long getLossId() {
        return lossId;
    }

    public void setLossId(Long lossId) {
        this.lossId = lossId;
    }

    public Date getLossDate() {
        return lossDate;
    }

    public void setLossDate(Date lossDate) {
        this.lossDate = lossDate;
    }

    public String getLossTypeStr() {
        return lossTypeStr;
    }

    public void setLossTypeStr(String lossTypeStr) {
        this.lossTypeStr = lossTypeStr;
    }

    public String getLossStateStr() {
        return lossStateStr;
    }

    public void setLossStateStr(String lossStateStr) {
        this.lossStateStr = lossStateStr;
    }

    public String getProductionLossSubTypeStr() {
        return productionLossSubTypeStr;
    }

    public void setProductionLossSubTypeStr(String productionLossSubTypeStr) {
        this.productionLossSubTypeStr = productionLossSubTypeStr;
    }

    public Long getObjectionCount() {
        return objectionCount;
    }

    public void setObjectionCount(Long objectionCount) {
        this.objectionCount = objectionCount;
    }

    public String getSupplierInfo() {
        return supplierInfo;
    }

    public void setSupplierInfo(String supplierInfo) {
        this.supplierInfo = supplierInfo;
    }

    public BigDecimal getWorkTypeCost() {
        return workTypeCost == null ? BigDecimal.ZERO : workTypeCost;
    }

    public void setWorkTypeCost(BigDecimal workTypeCost) {
        this.workTypeCost = workTypeCost;
    }

    public BigDecimal getDiscardedPartCost() {
        return discardedPartCost == null ? BigDecimal.ZERO : discardedPartCost;
    }

    public void setDiscardedPartCost(BigDecimal discardedPartCost) {
        this.discardedPartCost = discardedPartCost;
    }

    public BigDecimal getqLabTestCost() {
        return qLabTestCost == null ? BigDecimal.ZERO : qLabTestCost;
    }

    public void setqLabTestCost(BigDecimal qLabTestCost) {
        this.qLabTestCost = qLabTestCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public String getDurationInfo() {
        return durationInfo;
    }

    public void setDurationInfo(String durationInfo) {
        this.durationInfo = durationInfo;
    }

    public List<DurationDetailDto> getDurationDetails() {
        return durationDetails;
    }

    public void setDurationDetails(List<DurationDetailDto> durationDetails) {
        this.durationDetails = durationDetails;
    }

    public BigDecimal getOtherCosts() {
        return otherCosts;
    }

    public void setOtherCosts(BigDecimal otherCosts) {
        this.otherCosts = otherCosts;
    }

    public String getTotalLossState() {
        if(this.objectionCount > 0) {
            return "OBJECTED";
        } else {
            return this.lossStateStr;
        }
    }

    public BigDecimal getCostOf(DashboardCostType dashboardCostType) {

        switch (dashboardCostType) {
            case DISCARDED_PART:
                return this.getDiscardedPartCost();
            case QLAB_TEST:
                return this.getqLabTestCost();
            case WORK_TYPE:
                return this.getWorkTypeCost();
            case OTHER_COST:
                return this.getOtherCosts();
        }

        return BigDecimal.ZERO;
    }


    public BigDecimal getDurationOf(String type) {
        return durationDetails.stream()
                .filter(durationDetailDto ->  durationDetailDto.getWorkTypeName().equals(type))
                .map(DurationDetailDto::getDuration)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalDuration() {
        return durationDetails.stream().map(DurationDetailDto::getDuration).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
