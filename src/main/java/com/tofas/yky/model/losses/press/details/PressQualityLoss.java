package com.tofas.yky.model.losses.press.details;
/* t40127 @ 25.04.2016. */

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateDeSerializer;
import com.tofas.core.common.serializer.TfJsonDateSerializer;
import com.tofas.yky.model.dto.press.common.IPressQualityLossDetail;
import com.tofas.yky.model.dto.press.detail.PressQualityLossDetailDto;
import com.tofas.yky.model.losses.press.pos.VPosRollDetail;
import com.tofas.yky.utility.press.PressCalculatorUtility;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_PRESS_Q_LOSS")
public class PressQualityLoss extends AbstractPressLoss implements IPressQualityLossDetail {

    @Column(name = "QUALITY_TRACE_ID")
    private String qualityTraceId;

    @JsonSerialize(using = TfJsonDateSerializer.class)
    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    @Column(name = "CUT_DATE")
    private Date cutDate;

    @Column(name = "MOLD_NO")
    private String moldNo;

    @Column(name = "ROLL_DISCARD_WEIGHT")
    private BigDecimal rollScrapWeight;

    @Column(name = "CUT_DISCARDED_QTY")
    private BigDecimal cutScrapQty;

    @Column(name = "PRESSED_DISCARDED_QTY")
    private BigDecimal pressedScrapQty;

    @Column(name = "REPAIR_QTY")
    private BigDecimal repairQty;

    @JsonSerialize(using = TfJsonDateSerializer.class)
    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    @Column(name = "REPORT_DATE")
    private Date reportDate;

    @Column(name = "REPORT_KEEPER")
    private String reportKeeper;

    @Column(name = "CUT_PART_BASE_WEIGHT")
    private BigDecimal cutPartBaseWeight;

    @Column(name = "PRESSED_PART_BASE_WEIGHT")
    private BigDecimal pressedPartBaseWeight;

    @Column(name = "SCRAP_SOLD_OUT")
    private BigDecimal scrapSoldOut;

    @Column(name = "SCRAP_SALE_PRICE")
    private BigDecimal scrapSalePrice;

    @Column(name = "LOGISTICS_TRANSPORT_PLACE")
    private String logisticsTransportPlace;

    @Column(name = "CUSTOMS_COST_PARAM")
    private BigDecimal customsCostParam;

    @Column(name = "LOGISTICS_COST_PARAM")
    private BigDecimal logisticsCostParam;

    public PressQualityLoss() {
        customsCostParam = BigDecimal.ONE;
        logisticsCostParam = BigDecimal.ONE;
    }


    public String getQualityTraceId() {
        return qualityTraceId;
    }

    public void setQualityTraceId(String qualityTraceId) {
        this.qualityTraceId = qualityTraceId;
    }

    public Date getCutDate() {
        return cutDate;
    }

    public void setCutDate(Date cutDate) {
        this.cutDate = cutDate;
    }

    public String getMoldNo() {
        return moldNo;
    }

    public void setMoldNo(String moldNo) {
        this.moldNo = moldNo;
    }

    public BigDecimal getRollScrapWeight() {
        return rollScrapWeight;
    }

    public void setRollScrapWeight(BigDecimal rollScrapWeight) {
        this.rollScrapWeight = rollScrapWeight;
    }

    public BigDecimal getCutScrapQty() {
        return cutScrapQty;
    }

    public void setCutScrapQty(BigDecimal cutScrapQty) {
        this.cutScrapQty = cutScrapQty;
    }

    public BigDecimal getPressedScrapQty() {
        return pressedScrapQty;
    }

    public void setPressedScrapQty(BigDecimal pressedScrapQty) {
        this.pressedScrapQty = pressedScrapQty;
    }

    public BigDecimal getRepairQty() {
        return repairQty;
    }

    public void setRepairQty(BigDecimal repairQty) {
        this.repairQty = repairQty;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportKeeper() {
        return reportKeeper;
    }

    public void setReportKeeper(String reportKeeper) {
        this.reportKeeper = reportKeeper;
    }

    public BigDecimal getCutPartBaseWeight() {
        return cutPartBaseWeight;
    }

    public void setCutPartBaseWeight(BigDecimal cutPartBaseWeight) {
        this.cutPartBaseWeight = cutPartBaseWeight;
    }

    public BigDecimal getPressedPartBaseWeight() {
        return pressedPartBaseWeight;
    }

    public void setPressedPartBaseWeight(BigDecimal pressedPartBaseWeight) {
        this.pressedPartBaseWeight = pressedPartBaseWeight;
    }

    public BigDecimal getScrapSoldOut() {
        return scrapSoldOut;
    }

    public void setScrapSoldOut(BigDecimal scrapSoldOut) {
        this.scrapSoldOut = scrapSoldOut;
    }

    public BigDecimal getScrapSalePrice() {
        return scrapSalePrice;
    }



    public void setScrapSalePrice(BigDecimal scrapSalePrice) {
        this.scrapSalePrice = scrapSalePrice;
    }

    public String getLogisticsTransportPlace() {
        return logisticsTransportPlace;
    }

    public void setLogisticsTransportPlace(String logisticsTransportPlace) {
        this.logisticsTransportPlace = logisticsTransportPlace;
    }

    public void setCustomsCostParam(BigDecimal customsCostParam) {
        this.customsCostParam = customsCostParam;
    }

    public void setLogisticsCostParam(BigDecimal logisticsCostParam) {
        this.logisticsCostParam = logisticsCostParam;
    }

    @Override
    public String toString() {
        return "PressQualityLoss{" +
                "qualityTraceId='" + qualityTraceId + '\'' +
                ", cutDate=" + cutDate +
                ", moldNo='" + moldNo + '\'' +
                ", rollScrapWeight=" + rollScrapWeight +
                ", cutScrapQty=" + cutScrapQty +
                ", pressedScrapQty=" + pressedScrapQty +
                ", repairQty=" + repairQty +
                ", reportDate=" + reportDate +
                ", reportKeeper='" + reportKeeper + '\'' +
                ", cutPartBaseWeight=" + cutPartBaseWeight +
                ", pressedPartBaseWeight=" + pressedPartBaseWeight +
                ", scrapSoldOut=" + scrapSoldOut +
                ", scrapSalePrice=" + scrapSalePrice +
                ", logisticsTransportPlace='" + logisticsTransportPlace + '\'' +
                "} " + super.toString();
    }

    public void setFromDto(PressQualityLossDetailDto pressQualityLossDetailDto, VPosRollDetail vRollDetail) {
        setCutDate(pressQualityLossDetailDto.getCutDate());
        setCutScrapQty(pressQualityLossDetailDto.getCutScrapQty());
        setCutPartBaseWeight(pressQualityLossDetailDto.getCutPartBaseWeight());
        setLogisticsTransportPlace(pressQualityLossDetailDto.getLogisticsTransportPlace());
        setMoldNo(pressQualityLossDetailDto.getMoldNo());
        setPressedScrapQty(pressQualityLossDetailDto.getPressedScrapQty());
        setPressedPartBaseWeight(pressQualityLossDetailDto.getPressedPartBaseWeight());
        setQualityTraceId(pressQualityLossDetailDto.getQualityTraceId());
        setRepairQty(pressQualityLossDetailDto.getRepairQty());
        setReportDate(pressQualityLossDetailDto.getReportDate());
        setReportKeeper(pressQualityLossDetailDto.getReportKeeper());
        setRollScrapWeight(pressQualityLossDetailDto.getRollScrapWeight());
        setScrapSalePrice(pressQualityLossDetailDto.getScrapSalePrice());
        setScrapSoldOut(pressQualityLossDetailDto.getScrapSoldOut());
        setCustomsCostParam(pressQualityLossDetailDto.getCustomsCostParam());
        setLogisticsCostParam(pressQualityLossDetailDto.getLogisticsCostParam());

        super.setFromDto(pressQualityLossDetailDto, vRollDetail);
    }


    public void updateFields(PressQualityLoss lossDetail) {
        setCutDate(lossDetail.getCutDate());
        setCutScrapQty(lossDetail.getCutScrapQty());
        setCutPartBaseWeight(lossDetail.getCutPartBaseWeight());
        setLogisticsTransportPlace(lossDetail.getLogisticsTransportPlace());
        setMoldNo(lossDetail.getMoldNo());
        setPressedScrapQty(lossDetail.getPressedScrapQty());
        setPressedPartBaseWeight(lossDetail.getPressedPartBaseWeight());
        setQualityTraceId(lossDetail.getQualityTraceId());
        setRepairQty(lossDetail.getRepairQty());
        setReportDate(lossDetail.getReportDate());
        setReportKeeper(lossDetail.getReportKeeper());
        setRollScrapWeight(lossDetail.getRollScrapWeight());
        setScrapSalePrice(lossDetail.getScrapSalePrice());
        setScrapSoldOut(lossDetail.getScrapSoldOut());

        super.updateFields(lossDetail);
    }







     /* ---------------- calculations ------------------ */

    @Override
    public BigDecimal get_cutPartWeight() {
        return PressCalculatorUtility.getCutPartWeight(this);
    }

    @Override
    public BigDecimal get_pressedPartWeight() {
        return PressCalculatorUtility.getPressedPartWeight(this);
    }

    @Override
    public BigDecimal get_partCustomsBasePrice() {
        return PressCalculatorUtility.getPartCustomsBasePrice(this);
    }

    @Override
    public BigDecimal getCustomsCostParam() {
        return customsCostParam;
    }

    @Override
    public BigDecimal get_totalScrap() {
        return PressCalculatorUtility.getTotalScrap(this);
    }

    @Override
    public BigDecimal get_scrapHrd03() {
        return PressCalculatorUtility.getWastageHrd(this);
    }

    @Override
    public BigDecimal get_scrapPartCost() {
        return PressCalculatorUtility.getScrapPartCost(this);
    }

    @Override
    public BigDecimal get_scrapCostDiscount() {
        return PressCalculatorUtility.getScrapCostDiscount(this);
    }


    @Override
    public BigDecimal getLogisticsCostParam() {
        return logisticsCostParam;
    }

    @Override
    public BigDecimal get_logisticsCost() {
        return PressCalculatorUtility.getLogisticsCost(this);
    }
}
