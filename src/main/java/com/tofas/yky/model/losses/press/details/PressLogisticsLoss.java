package com.tofas.yky.model.losses.press.details;
/* t40127 @ 25.04.2016. */

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateDeSerializer;
import com.tofas.core.common.serializer.TfJsonDateSerializer;
import com.tofas.yky.model.decoratorbases.decorators.VAlternativePartDecorator;
import com.tofas.yky.model.dto.press.common.IPressLogisticsLossDetail;
import com.tofas.yky.model.dto.press.detail.AtrscField;
import com.tofas.yky.model.dto.press.detail.PressLogisticsLossDetailDto;
import com.tofas.yky.model.losses.press.pos.VPosDisegnoDetail;
import com.tofas.yky.model.losses.press.pos.VPosRollDetail;
import com.tofas.yky.utility.press.PressCalculatorUtility;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_PRESS_L_LOSS")
public class PressLogisticsLoss extends AbstractPressLoss implements IPressLogisticsLossDetail {

    @Embedded
    private AtrscField atrscField;

    @Column(name = "FRM_APPROVER")
    private String firmApprover;

    @Column(name = "TRANSPORTED_FROM")
    private String transportedFrom;

    @Column(name = "TRANSPORTED_TO")
    private String transportedTo;

    @JsonSerialize(using = TfJsonDateSerializer.class)
    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    @Column(name = "TRANSPORT_DATE")
    private Date transportDate;

    @Embedded
    VAlternativePartDecorator alternativePart;

    @Column(name = "ALT_DISEGNO_BASE_PRICE")
    private BigDecimal altDisegnoBasePrice;

    @Column(name = "ALT_DISEGNO_BASE_PRICE_DIFF")
    private BigDecimal altDisegnoBasePriceDiff;

    @Column(name = "ALT_DISEGNO_SLICE_WIDTH")
    private BigDecimal altDisegnoSliceWidth;

    @ManyToOne
    @JoinColumn(name = "ALT_DISEGNO", insertable = false, updatable = false)
    private VPosDisegnoDetail altDisegnoDetail;

    @Column(name = "ALT_DISCARD_REASON")
    private String altDiscardReason;

    @JsonSerialize(using = TfJsonDateSerializer.class)
    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    @Column(name = "ROLL_TOFAS_TRANS_DATE")
    private Date rollTofasTransDate;

    @Column(name = "SLICE_COST_PER_TONNE")
    private BigDecimal sliceCostPerTonne;

    @Column(name = "FRM_TRANSPORTATION_COST")
    private BigDecimal firmTransportationCost;

    @Column(name = "WAREHOUSE_COST")
    private BigDecimal warehouseCost;

    @Column(name = "CSM_INVOICE_NO")
    private String csmInvoiceNo;

    @Column(name = "CSM_INVOICE_AMOUNT")
    private BigDecimal csmInvoiceAmount;


    public PressLogisticsLoss() {

    }

    public AtrscField getAtrscField() {
        return atrscField;
    }

    public void setAtrscField(AtrscField atrscField) {
        this.atrscField = atrscField;
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

    public VAlternativePartDecorator getAlternativePart() {
        return alternativePart;
    }

    public void setAlternativePart(VAlternativePartDecorator alternativePart) {
        this.alternativePart = alternativePart;
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

    @Override
    public BigDecimal getAltDisegnoBasePriceDiff() {
        return altDisegnoBasePriceDiff;
    }

    public void setAltDisegnoBasePriceDiff(BigDecimal altDisegnoBasePriceDiff) {
        this.altDisegnoBasePriceDiff = altDisegnoBasePriceDiff;
    }

    public VPosDisegnoDetail getAltDisegnoDetail() {
        return altDisegnoDetail;
    }

    public void setAltDisegnoDetail(VPosDisegnoDetail altDisegnoDetail) {
        this.altDisegnoDetail = altDisegnoDetail;
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

    public BigDecimal getSliceCostPerTonne() {
        return sliceCostPerTonne;
    }

    public void setSliceCostPerTonne(BigDecimal sliceCostPerTonne) {
        this.sliceCostPerTonne = sliceCostPerTonne;
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


    public void setFromDto(PressLogisticsLossDetailDto pressLogisticsLossDetailDto, VPosRollDetail vRollDetail,
                           VPosDisegnoDetail vAlternativeDisegnoDetail, VAlternativePartDecorator vAlternativePart) {

        setAltDiscardReason(pressLogisticsLossDetailDto.getAltDiscardReason());
        setAltDisegnoBasePrice(pressLogisticsLossDetailDto.getAltDisegnoBasePrice());
        setAltDisegnoBasePriceDiff(pressLogisticsLossDetailDto.getAltDisegnoBasePriceDiff());
        setAltDisegnoSliceWidth(pressLogisticsLossDetailDto.getAltDisegnoSliceWidth());
        setAltDisegnoDetail(vAlternativeDisegnoDetail);
        setAlternativePart(vAlternativePart);
        setAtrscField(new AtrscField(pressLogisticsLossDetailDto.getAtrscField()));
        setCsmInvoiceAmount(pressLogisticsLossDetailDto.getCsmInvoiceAmount());
        setCsmInvoiceNo(pressLogisticsLossDetailDto.getCsmInvoiceNo());
        setFirmApprover(pressLogisticsLossDetailDto.getFirmApprover());
        setFirmTransportationCost(pressLogisticsLossDetailDto.getFirmTransportationCost());
        setRollTofasTransDate(pressLogisticsLossDetailDto.getRollTofasTransDate());
        setSliceCostPerTonne(pressLogisticsLossDetailDto.getSlicePerTonne());
        setTransportDate(pressLogisticsLossDetailDto.getTransportDate());
        setTransportedFrom(pressLogisticsLossDetailDto.getTransportedFrom());
        setTransportedTo(pressLogisticsLossDetailDto.getTransportedTo());
        setWarehouseCost(pressLogisticsLossDetailDto.getWarehouseCost());


        super.setFromDto(pressLogisticsLossDetailDto, vRollDetail);
    }



    public void updateFields(PressLogisticsLoss lossDetail) {
        setAltDiscardReason(lossDetail.getAltDiscardReason());
        setAltDisegnoSliceWidth(lossDetail.getAltDisegnoSliceWidth());
        setAtrscField(lossDetail.getAtrscField());
        setCsmInvoiceAmount(lossDetail.getCsmInvoiceAmount());
        setCsmInvoiceNo(lossDetail.getCsmInvoiceNo());
        setFirmApprover(lossDetail.getFirmApprover());
        setFirmTransportationCost(lossDetail.getFirmTransportationCost());
        setRollTofasTransDate(lossDetail.getRollTofasTransDate());
        setSliceCostPerTonne(lossDetail.getSlicePerTonne());
        setTransportDate(lossDetail.getTransportDate());
        setTransportedFrom(lossDetail.getTransportedFrom());
        setTransportedTo(lossDetail.getTransportedTo());
        setWarehouseCost(lossDetail.getWarehouseCost());

        super.updateFields(lossDetail);
    }

    @Override
    public String toString() {
        return "PressLogisticsLoss{" +
                ", firmApprover='" + firmApprover + '\'' +
                ", transportedFrom='" + transportedFrom + '\'' +
                ", transportedTo='" + transportedTo + '\'' +
                ", transportDate=" + transportDate +
                ", alternativePart=" + alternativePart +
                ", altDisegnoBasePrice=" + altDisegnoBasePrice +
                ", altDisegnoSliceWidth=" + altDisegnoSliceWidth +
                ", altDisegnoDetail=" + altDisegnoDetail +
                ", altDiscardReason='" + altDiscardReason + '\'' +
                ", rollTofasTransDate=" + rollTofasTransDate +
                ", sliceCostPerTonne=" + sliceCostPerTonne +
                ", firmTransportationCost=" + firmTransportationCost +
                ", warehouseCost=" + warehouseCost +
                "} " + super.toString();
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
    public BigDecimal getSlicePerTonne() {
        return sliceCostPerTonne;
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
    public BigDecimal getFirmTransportationCost() {
        return firmTransportationCost;
    }


    @Override
    public BigDecimal get_totalCost() {
        return PressCalculatorUtility.getTotalCost(this);
    }


    @Override
    public BigDecimal get_rollWeight() {
        return this.getRollDetail().getRollWeight();
    }

    @Override
    public BigDecimal get_partSliceWidth() {
        return this.altDisegnoSliceWidth;
    }

    @Override
    public BigDecimal get_width() {
        return getRollDetail().getDisegnoDetail().getWidth();
    }


    @Override
    public BigDecimal get_alternativeDisegnoBasePrice() {
        return altDisegnoBasePrice;
    }


}
