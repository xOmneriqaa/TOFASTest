package com.tofas.yky.model.dto.press.detail;
/* t40127 @ 26.04.2016. */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateTimeDeSerializer;
import com.tofas.core.common.serializer.TfJsonDateTimeSerializer;
import com.tofas.yky.model.dto.press.common.IPressAbstractLossDetail;
import com.tofas.yky.model.dto.press.pos.PressPosRollDetailDto;
import com.tofas.yky.utility.press.PressCalculatorUtility;

import java.math.BigDecimal;
import java.util.Date;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PressLogisticsLossDetailDto.class, name = "logistics"),
        @JsonSubTypes.Type(value = PressQualityLossDetailDto.class, name="quality")
})
public abstract class AbstractPressLossDetailDto implements IPressAbstractLossDetail {

    protected PressPosRollDetailDto rollDetail;

    protected BigDecimal disegnoBasePrice;
    
    protected BigDecimal disegnoBasePriceDiff;
    
    protected Long logisticsLabourHours;
    
    protected Long productionLabourHours;
    
    protected String sapOrderNo;
    
    protected String creditNote;
    
    @JsonSerialize(using = TfJsonDateTimeSerializer.class)
    @JsonDeserialize(using = TfJsonDateTimeDeSerializer.class)
    protected Date creditNoteDate;
    
    protected BigDecimal creditNoteAmount;

    @JsonSerialize(using = TfJsonDateTimeSerializer.class)
    @JsonDeserialize(using = TfJsonDateTimeDeSerializer.class)
    protected Date paymentMailDate;
    
    protected String paymentInformer;
    
    protected String notes;

    protected BigDecimal blueCollarBaseWage;

    private BigDecimal scrapHrdParam;

    public AbstractPressLossDetailDto(BigDecimal blueCollarBaseWage) {
        this.blueCollarBaseWage = blueCollarBaseWage;
        this.creditNoteAmount = BigDecimal.ZERO;
        this.scrapHrdParam = BigDecimal.ONE;
        this.disegnoBasePriceDiff = BigDecimal.ZERO;
    }

    public PressPosRollDetailDto getRollDetail() {
        return rollDetail;
    }

    public void setRollDetail(PressPosRollDetailDto rollDetail) {
        if(rollDetail != null && rollDetail.getDisegnoDetail() != null) {
            disegnoBasePrice = rollDetail.getDisegnoDetail().getBasePrice();
        }

        this.rollDetail = rollDetail;
    }

    public BigDecimal getDisegnoBasePrice() {
        return disegnoBasePrice;
    }

    public void setDisegnoBasePrice(BigDecimal disegnoBasePrice) {
        this.disegnoBasePrice = disegnoBasePrice;
    }

    public BigDecimal getDisegnoBasePriceDiff() {
        return disegnoBasePriceDiff;
    }

    public void setDisegnoBasePriceDiff(BigDecimal disegnoBasePriceDiff) {
        this.disegnoBasePriceDiff = disegnoBasePriceDiff;
    }

    public Long getLogisticsLabourHours() {
        return logisticsLabourHours;
    }

    public void setLogisticsLabourHours(Long logisticsLabourHours) {
        this.logisticsLabourHours = logisticsLabourHours;
    }

    public Long getProductionLabourHours() {
        return productionLabourHours;
    }

    public void setProductionLabourHours(Long productionLabourHours) {
        this.productionLabourHours = productionLabourHours;
    }

    public String getSapOrderNo() {
        return sapOrderNo;
    }

    public void setSapOrderNo(String sapOrderNo) {
        this.sapOrderNo = sapOrderNo;
    }

    public String getCreditNote() {
        return creditNote;
    }

    public void setCreditNote(String creditNote) {
        this.creditNote = creditNote;
    }

    public Date getCreditNoteDate() {
        return creditNoteDate;
    }

    public void setCreditNoteDate(Date creditNoteDate) {
        this.creditNoteDate = creditNoteDate;
    }

    public BigDecimal getCreditNoteAmount() {
        return creditNoteAmount;
    }

    public void setCreditNoteAmount(BigDecimal creditNoteAmount) {
        this.creditNoteAmount = creditNoteAmount;
    }

    public Date getPaymentMailDate() {
        return paymentMailDate;
    }

    public void setPaymentMailDate(Date paymentMailDate) {
        this.paymentMailDate = paymentMailDate;
    }

    public String getPaymentInformer() {
        return paymentInformer;
    }

    public void setPaymentInformer(String paymentInformer) {
        this.paymentInformer = paymentInformer;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setBlueCollarBaseWage(BigDecimal blueCollarBaseWage) {
        this.blueCollarBaseWage = blueCollarBaseWage;
    }

    public BigDecimal getBlueCollarBaseWage() {
        return blueCollarBaseWage;
    }

    public BigDecimal getScrapHrdParam() {
        return scrapHrdParam;
    }

    public void setScrapHrdParam(BigDecimal scrapHrdParam) {
        this.scrapHrdParam = scrapHrdParam;
    }

    public String getType() {
        if(getClass().equals(PressLogisticsLossDetailDto.class)) {
            return "logistics";
        } else {
            return "quality";
        }
    }

    @JsonIgnore
    public boolean isRollNoChanged() {
        return getRollDetail().getRollNo() == null || getRollDetail().getRollNo().equals(getRollDetail().getCustomRollNo());
    }

    /* ------------------ calculations ------------------ */


    @Override
    public BigDecimal get_blueCollarBaseWage() {
        return blueCollarBaseWage;
    }

    @Override
    public BigDecimal get_partBasePrice() {
        return disegnoBasePrice == null ? BigDecimal.ZERO : disegnoBasePrice;
    }

    @Override
    public BigDecimal get_partBasePriceDiff() {
        return disegnoBasePriceDiff == null ? BigDecimal.ZERO : disegnoBasePriceDiff;
    }

    @Override
    public BigDecimal get_logisticsLabourCost() {
        return PressCalculatorUtility.getLogisticsLabourCost(this);
    }

    @Override
    public BigDecimal get_productionLabourCost() {
        return PressCalculatorUtility.getProductionLabourCost(this);
    }

    @Override
    public BigDecimal get_paidBalance() {
        return PressCalculatorUtility.getPaidBalance(this);
    }

    @Override
    public BigDecimal get_totalCost() {
        return PressCalculatorUtility.getTotalCost(this);
    }
}
