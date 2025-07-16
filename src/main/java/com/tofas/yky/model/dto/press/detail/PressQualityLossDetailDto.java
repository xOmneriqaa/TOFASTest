package com.tofas.yky.model.dto.press.detail;
/* t40127 @ 26.04.2016. */

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateDeSerializer;
import com.tofas.core.common.serializer.TfJsonDateSerializer;
import com.tofas.yky.model.dto.press.common.IPressQualityLossDetail;
import com.tofas.yky.utility.press.PressCalculatorUtility;

import java.math.BigDecimal;
import java.util.Date;

public class PressQualityLossDetailDto extends AbstractPressLossDetailDto implements IPressQualityLossDetail {

    private String qualityTraceId;

    @JsonSerialize(using = TfJsonDateSerializer.class)
    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date cutDate;

    private String moldNo;

    private BigDecimal rollScrapWeight;

    private BigDecimal cutScrapQty;

    private BigDecimal pressedScrapQty;

    private BigDecimal repairQty;

    @JsonSerialize(using = TfJsonDateSerializer.class)
    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date reportDate;

    private String reportKeeper;

    private BigDecimal cutPartBaseWeight;

    private BigDecimal pressedPartBaseWeight;

    private BigDecimal scrapSoldOut;

    private BigDecimal scrapSalePrice;

    private String logisticsTransportPlace;

    private BigDecimal logisticsCostParam;

    private BigDecimal customsCostParam;



    public PressQualityLossDetailDto() {
        super(BigDecimal.ZERO);
    }

    public PressQualityLossDetailDto(BigDecimal blueCollarWage) {
        super(blueCollarWage);
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

    public void setLogisticsCostParam(BigDecimal logisticsCostParam) {
        this.logisticsCostParam = logisticsCostParam;
    }

    public void setCustomsCostParam(BigDecimal customsCostParam) {
        this.customsCostParam = customsCostParam;
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

    @Override
    public BigDecimal getCutPartBaseWeight() {
        return cutPartBaseWeight;
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


    @Override
    public BigDecimal getLogisticsCostParam() {
        return logisticsCostParam;
    }

    @Override
    public BigDecimal getCustomsCostParam() {
        return customsCostParam;
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
    public BigDecimal get_logisticsCost() {
        return PressCalculatorUtility.getLogisticsCost(this);
    }




}
