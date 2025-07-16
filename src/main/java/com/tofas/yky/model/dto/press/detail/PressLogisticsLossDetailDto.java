package com.tofas.yky.model.dto.press.detail;
/* t40127 @ 26.04.2016. */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateDeSerializer;
import com.tofas.core.common.serializer.TfJsonDateSerializer;
import com.tofas.yky.model.dto.VPartDto;
import com.tofas.yky.model.dto.press.common.IPressLogisticsLossDetail;
import com.tofas.yky.model.dto.press.pos.PressPosDisegnoDetailDto;
import com.tofas.yky.utility.press.PressCalculatorUtility;

import java.math.BigDecimal;
import java.util.Date;

public class PressLogisticsLossDetailDto extends AbstractPressLossDetailDto implements IPressLogisticsLossDetail {

    private AtrscField atrscField;

    private String firmApprover;

    private String transportedFrom;

    private String transportedTo;

    private BigDecimal altDisegnoBasePriceDiff;

    @JsonSerialize(using = TfJsonDateSerializer.class)
    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date transportDate;

    private String customAlternatePartDisegno;

    private VPartDto alternatePart;

    private BigDecimal altDisegnoBasePrice;

    private BigDecimal altDisegnoSliceWidth;

    private PressPosDisegnoDetailDto altDisegnoDetail;

    private String altDiscardReason;

    @JsonSerialize(using = TfJsonDateSerializer.class)
    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date rollTofasTransDate;

    private BigDecimal slicePerTonne;

    private BigDecimal firmTransportationCost;

    private BigDecimal warehouseCost;

    private String csmInvoiceNo;

    private BigDecimal csmInvoiceAmount;

    public PressLogisticsLossDetailDto() {
        this(BigDecimal.ZERO);
    }

    public PressLogisticsLossDetailDto(BigDecimal blueCollarWage) {
        super(blueCollarWage);
        this.altDisegnoBasePriceDiff = BigDecimal.ZERO;
    }

    public AtrscField getAtrscField() {
        return atrscField;
    }

    public void setAtrscField(AtrscField atrscField) {
        this.atrscField = atrscField;
    }

    public String getFirmApprover() {
        return firmApprover;
    }

    public void setFirmApprover(String firmApprover) {
        this.firmApprover = firmApprover;
    }

    public String getTransportedFrom() {
        return transportedFrom;
    }

    public void setTransportedFrom(String transportedFrom) {
        this.transportedFrom = transportedFrom;
    }

    public String getTransportedTo() {
        return transportedTo;
    }

    public void setTransportedTo(String transportedTo) {
        this.transportedTo = transportedTo;
    }

    public Date getTransportDate() {
        return transportDate;
    }

    public void setTransportDate(Date transportDate) {
        this.transportDate = transportDate;
    }

    public VPartDto getAlternatePart() {
        return alternatePart;
    }

    public void setAlternatePart(VPartDto alternatePart) {
        this.alternatePart = alternatePart;
    }

    public BigDecimal getAltDisegnoBasePrice() {
        return altDisegnoBasePrice;
    }

    public void setAltDisegnoBasePrice(BigDecimal altDisegnoBasePrice) {
        this.altDisegnoBasePrice = altDisegnoBasePrice;
    }

    public BigDecimal getAltDisegnoSliceWidth() {
        return altDisegnoSliceWidth;
    }

    public void setAltDisegnoSliceWidth(BigDecimal altDisegnoSliceWidth) {
        this.altDisegnoSliceWidth = altDisegnoSliceWidth;
    }

    public PressPosDisegnoDetailDto getAltDisegnoDetail() {
        return altDisegnoDetail;
    }

    public void setAltDisegnoDetail(PressPosDisegnoDetailDto altDisegnoDetail) {
        this.altDisegnoDetail = altDisegnoDetail;
        this.customAlternatePartDisegno = altDisegnoDetail.getDisegno();
        this.altDisegnoBasePrice = altDisegnoDetail.getBasePrice();
    }

    public String getAltDiscardReason() {
        return altDiscardReason;
    }

    public void setAltDiscardReason(String altDiscardReason) {
        this.altDiscardReason = altDiscardReason;
    }

    public Date getRollTofasTransDate() {
        return rollTofasTransDate;
    }

    public void setRollTofasTransDate(Date rollTofasTransDate) {
        this.rollTofasTransDate = rollTofasTransDate;
    }

    public BigDecimal getSlicePerTonne() {
        return slicePerTonne;
    }

    public void setSlicePerTonne(BigDecimal slicePerTonne) {
        this.slicePerTonne = slicePerTonne;
    }

    public BigDecimal getFirmTransportationCost() {
        return firmTransportationCost;
    }

    @Override
    public BigDecimal getAltDisegnoBasePriceDiff() {
        return altDisegnoBasePriceDiff == null ? BigDecimal.ZERO : altDisegnoBasePriceDiff;
    }

    public void setFirmTransportationCost(BigDecimal firmTransportationCost) {
        this.firmTransportationCost = firmTransportationCost;
    }

    public BigDecimal getWarehouseCost() {
        return warehouseCost;
    }

    public void setWarehouseCost(BigDecimal warehouseCost) {
        this.warehouseCost = warehouseCost;
    }

    public String getCustomAlternatePartDisegno() {
        return customAlternatePartDisegno;
    }

    public void setCustomAlternatePartDisegno(String customAlternatePartDisegno) {
        this.customAlternatePartDisegno = customAlternatePartDisegno;
    }

    public String getCsmInvoiceNo() {
        return csmInvoiceNo;
    }

    public void setCsmInvoiceNo(String csmInvoiceNo) {
        this.csmInvoiceNo = csmInvoiceNo;
    }

    public BigDecimal getCsmInvoiceAmount() {
        return csmInvoiceAmount;
    }

    public void setCsmInvoiceAmount(BigDecimal csmInvoiceAmount) {
        this.csmInvoiceAmount = csmInvoiceAmount;
    }

    @JsonIgnore
    public boolean isAlternateDisegnoChanged() {
        return getAlternatePart() == null || !getAlternatePart().getDisegno().equals(customAlternatePartDisegno);
    }



    /* --------------- calculations ----------------- */


    @Override
    public BigDecimal get_oKPartWeight() {
        return PressCalculatorUtility.getOkPartWeight(this);
    }

    @Override
    public BigDecimal get_scrapWeight() {
        return PressCalculatorUtility.getScrapWeight(this);
    }


    @Override
    public BigDecimal get_calculatedInvoiceAmount() {
        return PressCalculatorUtility.getCalculatedInvoiceAmount(this);
    }


    @Override
    public BigDecimal get_usedPartPriceDifference() {
        return PressCalculatorUtility.getUsedPartPriceDifference(this);
    }


    @Override
    public BigDecimal get_sliceCost() {
        return get_calculatedInvoiceAmount();
    }


    @Override
    public BigDecimal get_scrapCostDiscountPrice() {
        return PressCalculatorUtility.getScrapCostDiscountPrice(this);
    }

    @Override
    public BigDecimal get_scrapPartCost() {
        return PressCalculatorUtility.getScrapPartCost(this);
    }


    @Override
    public BigDecimal get_totalCost() {
        return PressCalculatorUtility.getTotalCost(this);
    }


    @Override
    public BigDecimal get_rollWeight() {
        return this.rollDetail.getRollWeight();
    }

    @Override
    public BigDecimal get_partSliceWidth() {
        return this.altDisegnoSliceWidth;
      //  return this.getAltDisegnoDetail().getWidth();
    }

    @Override
    public BigDecimal get_width() {
        return rollDetail.getDisegnoDetail().getWidth();
    }


    @Override
    public BigDecimal get_alternativeDisegnoBasePrice() {
        return altDisegnoDetail.getBasePrice();
    }

}
