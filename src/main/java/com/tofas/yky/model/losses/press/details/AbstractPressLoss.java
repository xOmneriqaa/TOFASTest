package com.tofas.yky.model.losses.press.details;
/* t40127 @ 25.04.2016. */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.model.base.TfEntity;
import com.tofas.core.common.serializer.TfJsonDateDeSerializer;
import com.tofas.core.common.serializer.TfJsonDateSerializer;
import com.tofas.yky.model.dto.press.common.IPressAbstractLossDetail;
import com.tofas.yky.model.dto.press.detail.AbstractPressLossDetailDto;
import com.tofas.yky.model.losses.press.PressLoss;
import com.tofas.yky.model.losses.press.pos.VPosRollDetail;
import com.tofas.yky.utility.press.PressCalculatorUtility;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PressLogisticsLoss.class, name = "logistics"),
        @JsonSubTypes.Type(value = PressQualityLoss.class, name="quality")
})
public abstract class AbstractPressLoss extends TfEntity implements IPressAbstractLossDetail {

    @Id
    @SequenceGenerator(name="SEQ_YKY_PRESS_LOSS", sequenceName="SEQ_YKY_PRESS_LOSS", allocationSize = 1)
    @GeneratedValue(generator="SEQ_YKY_PRESS_LOSS", strategy = GenerationType.SEQUENCE)
    protected Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "LOSS_ID")
    private PressLoss loss;



    /* ----------- POS -------------- */



    @ManyToOne
    @JoinColumn(name = "ROLL_NO")
    private VPosRollDetail rollDetail;


    @Column(name = "DISEGNO_BASE_PRICE")
    private BigDecimal disegnoBasePrice;

    @Column(name = "DISEGNO_BASE_PRICE_DIFF")
    private BigDecimal disegnoBasePriceDiff;

    /* ----------- POS -------------- */



    @Column(name = "LOGISTICS_LABOUR_HRS")
    protected Long logisticsLabourHours;

    @Column(name = "PRODUCTION_LABOUR_HRS")
    protected Long productionLabourHours;


    @Column(name = "SCRAP_HRD_PARAM")
    protected BigDecimal scrapHrdParam;


    @Column(name = "SAP_ORDER_NO")
    protected String sapOrderNo;


    @Column(name = "CREDIT_NOTE")
    protected String creditNote;


    @JsonSerialize(using = TfJsonDateSerializer.class)
    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    @Column(name = "CREDIT_NOTE_DATE")
    protected Date creditNoteDate;


    @Column(name = "CREDIT_NOTE_AMOUNT")
    protected BigDecimal creditNoteAmount;


    @JsonSerialize(using = TfJsonDateSerializer.class)
    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    @Column(name = "PAYMENT_MAIL_DATE")
    protected Date paymentMailDate;


    @Column(name = "PAYMENT_INFORMER")
    protected String paymentInformer;

    @Column(name = "NOTES")
    protected String notes;

    @Column(name = "BLUE_COLLAR_BASE_WAGE")
    protected BigDecimal blueCollarBaseWage;

    public AbstractPressLoss() {
        blueCollarBaseWage = BigDecimal.ONE;
        this.scrapHrdParam = BigDecimal.ONE;
    }

    public BigDecimal getBlueCollarBaseWage() {
        return blueCollarBaseWage;
    }

    public void setBlueCollarBaseWage(BigDecimal blueCollarBaseWage) {
        this.blueCollarBaseWage = blueCollarBaseWage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PressLoss getLoss() {
        return loss;
    }

    public void setLoss(PressLoss loss) {
        this.loss = loss;
    }

    public VPosRollDetail getRollDetail() {
        return rollDetail;
    }

    public void setRollDetail(VPosRollDetail rollDetail) {
        this.rollDetail = rollDetail;
    }

    public void setLogisticsLabourHours(Long logisticsLabourHours) {
        this.logisticsLabourHours = logisticsLabourHours;
    }

    public void setProductionLabourHours(Long productionLabourHours) {
        this.productionLabourHours = productionLabourHours;
    }

    public void setScrapHrdParam(BigDecimal scrapHrdParam) {
        this.scrapHrdParam = scrapHrdParam;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractPressLoss that = (AbstractPressLoss) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String getType() {
        return getClass().equals(PressLogisticsLoss.class) ? "logistics" : "quality";
    }

    public void setFromDto(AbstractPressLossDetailDto abstractPressLossDetailDto, VPosRollDetail vRollDetail) {
        setCreditNote(abstractPressLossDetailDto.getCreditNote());
        setCreditNoteAmount(abstractPressLossDetailDto.getCreditNoteAmount());
        setCreditNoteDate(abstractPressLossDetailDto.getCreditNoteDate());
        setDisegnoBasePrice(abstractPressLossDetailDto.getDisegnoBasePrice());
        setDisegnoBasePriceDiff(abstractPressLossDetailDto.getDisegnoBasePriceDiff());
        setLogisticsLabourHours(abstractPressLossDetailDto.getLogisticsLabourHours());
        setNotes(abstractPressLossDetailDto.getNotes());
        setPaymentInformer(abstractPressLossDetailDto.getPaymentInformer());
        setPaymentMailDate(abstractPressLossDetailDto.getPaymentMailDate());
        setProductionLabourHours(abstractPressLossDetailDto.getProductionLabourHours());
        setRollDetail(vRollDetail);
        setSapOrderNo(abstractPressLossDetailDto.getSapOrderNo());
        setScrapHrdParam(abstractPressLossDetailDto.getScrapHrdParam());
    }

    public void updateFields(AbstractPressLoss lossDetail) {
        setCreditNote(lossDetail.getCreditNote());
        setCreditNoteAmount(lossDetail.getCreditNoteAmount());
        setCreditNoteDate(lossDetail.getCreditNoteDate());
        setDisegnoBasePriceDiff(lossDetail.getDisegnoBasePriceDiff());
        setLogisticsLabourHours(lossDetail.getLogisticsLabourHours());
        setNotes(lossDetail.getNotes());
        setPaymentInformer(lossDetail.getPaymentInformer());
        setPaymentMailDate(lossDetail.getPaymentMailDate());
        setProductionLabourHours(lossDetail.getProductionLabourHours());
        setSapOrderNo(lossDetail.getSapOrderNo());
        setScrapHrdParam(lossDetail.getScrapHrdParam());
    }


    /* ------------------ calculations ------------------ */

    @Override
    public BigDecimal getScrapHrdParam() {
        return scrapHrdParam;
    }

    @Override
    public BigDecimal get_blueCollarBaseWage() {
        return this.blueCollarBaseWage;
    }

    @Override
    public BigDecimal get_partBasePrice() {
        return disegnoBasePrice;
    }

    @Override
    public BigDecimal get_partBasePriceDiff() {
        return disegnoBasePriceDiff;
    }

    @Override
    public Long getLogisticsLabourHours() {
        return logisticsLabourHours;
    }

    @Override
    public Long getProductionLabourHours() {
        return productionLabourHours;
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
