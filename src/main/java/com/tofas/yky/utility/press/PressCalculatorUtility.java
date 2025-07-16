package com.tofas.yky.utility.press;
/* t40127 @ 20.05.2016. */

import com.tofas.yky.model.dto.press.common.IPressAbstractLossDetail;
import com.tofas.yky.model.dto.press.common.IPressLogisticsLossDetail;
import com.tofas.yky.model.dto.press.common.IPressQualityLossDetail;

import java.math.BigDecimal;

public abstract class PressCalculatorUtility {

    /* ------------------ QUALITY ------------------------- */

    public static BigDecimal getCutPartWeight(IPressQualityLossDetail lossDetail) {
        return lossDetail.getCutPartBaseWeight()
                .multiply(lossDetail.getCutScrapQty());
    }

    public static BigDecimal getPressedPartWeight(IPressQualityLossDetail lossDetail) {
        return lossDetail.getPressedPartBaseWeight()
                .multiply(lossDetail.getPressedScrapQty());
    }

    public static BigDecimal getPartCustomsBasePrice(IPressQualityLossDetail lossDetail) {
        return lossDetail.get_partBasePrice().add(lossDetail.get_partBasePriceDiff())
                .multiply(lossDetail.getCustomsCostParam());
    }

    public static BigDecimal getTotalScrap(IPressQualityLossDetail lossDetail) {
        return getCutPartWeight(lossDetail).add(getPressedPartWeight(lossDetail))
                .add(lossDetail.getRollScrapWeight());
    }

    public static BigDecimal getWastageHrd(IPressQualityLossDetail lossDetail) {
        return getTotalScrap(lossDetail).subtract(lossDetail.getScrapSoldOut());
    }

    public static BigDecimal getScrapPartCost(IPressQualityLossDetail lossDetail) {
        return getTotalScrap(lossDetail).multiply(getPartCustomsBasePrice(lossDetail))
                .divide(BigDecimal.valueOf(1000), BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal getScrapCostDiscount(IPressQualityLossDetail lossDetail) {
        return getWastageHrd(lossDetail).multiply(lossDetail.getScrapHrdParam())
                .add(lossDetail.getScrapSoldOut().multiply(lossDetail.getScrapSalePrice()));
    }

    public static BigDecimal getLogisticsCost(IPressQualityLossDetail lossDetail) {
        return lossDetail.getLogisticsCostParam().multiply(getTotalScrap(lossDetail))
                .divide(BigDecimal.valueOf(1000), BigDecimal.ROUND_HALF_UP);
    }




    public static BigDecimal getTotalCost(IPressQualityLossDetail lossDetail) {
        return getLogisticsCost(lossDetail).add(getProductionLabourCost(lossDetail))
                .add(getLogisticsLabourCost(lossDetail)).add(getScrapPartCost(lossDetail))
                .subtract(getScrapCostDiscount(lossDetail));
    }

    /* -------------------------- LOGISTICS ----------------------- */

    public static BigDecimal getOkPartWeight(IPressLogisticsLossDetail lossDetail) {
        return lossDetail.get_rollWeight().multiply(lossDetail.get_partSliceWidth())
                .divide(lossDetail.get_width(), BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal getScrapWeight(IPressLogisticsLossDetail lossDetail) {
        return lossDetail.get_rollWeight().subtract(getOkPartWeight(lossDetail));
    }

    public static BigDecimal getCalculatedInvoiceAmount(IPressLogisticsLossDetail lossDetail) {
        return lossDetail.getSlicePerTonne().multiply(lossDetail.get_rollWeight())
                .divide(BigDecimal.valueOf(1000), BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal getUsedPartPriceDifference(IPressLogisticsLossDetail lossDetail) {
        return lossDetail.get_partBasePrice().add(lossDetail.get_partBasePriceDiff())
                .subtract(lossDetail.get_alternativeDisegnoBasePrice().add(lossDetail.getAltDisegnoBasePriceDiff()))
                .multiply(getOkPartWeight(lossDetail));
    }

    public static BigDecimal getScrapCostDiscountPrice(IPressLogisticsLossDetail lossDetail) {
        return getScrapWeight(lossDetail).multiply(lossDetail.getScrapHrdParam());
    }

    public static BigDecimal getScrapPartCost(IPressLogisticsLossDetail lossDetail) {
        return getScrapWeight(lossDetail).multiply(lossDetail.get_partBasePrice())
                .divide(BigDecimal.valueOf(1000), BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal getTotalCost(IPressLogisticsLossDetail lossDetail) {

         if(lossDetail.getFirmTransportationCost()==null){
             return  getUsedPartPriceDifference(lossDetail)
                    .add(getCalculatedInvoiceAmount(lossDetail))
                    .add(getProductionLabourCost(lossDetail))
                    .add(getLogisticsLabourCost(lossDetail))
                    .add(getScrapPartCost(lossDetail))
                    .subtract(getScrapCostDiscountPrice(lossDetail));
        }
        else {
             return getUsedPartPriceDifference(lossDetail)
                     .add(getCalculatedInvoiceAmount(lossDetail))
                     .add(getProductionLabourCost(lossDetail))
                     .add(getLogisticsLabourCost(lossDetail))
                     .add(getScrapPartCost(lossDetail))
                     .subtract(getScrapCostDiscountPrice(lossDetail));
         }


    }

























    /* -------------------------- COMMON ----------------------- */

    public static BigDecimal getLogisticsLabourCost(IPressAbstractLossDetail lossDetail) {
        return BigDecimal.valueOf(lossDetail.getLogisticsLabourHours())
                .multiply(lossDetail.get_blueCollarBaseWage());
    }

    public static BigDecimal getProductionLabourCost(IPressAbstractLossDetail lossDetail) {
        return BigDecimal.valueOf(lossDetail.getProductionLabourHours())
                .multiply(lossDetail.get_blueCollarBaseWage());
    }

    public static BigDecimal getCreditAmount(IPressAbstractLossDetail lossDetail) {
        return lossDetail.getCreditNoteAmount();
    }

    public static BigDecimal getPaidBalance(IPressAbstractLossDetail lossDetail) {
        return getTotalCost(lossDetail)
                .subtract(getCreditAmount(lossDetail));
    }

    public static BigDecimal getTotalCost(IPressAbstractLossDetail lossDetail) {
        return IPressQualityLossDetail.class.isAssignableFrom(lossDetail.getClass())
                ? getTotalCost((IPressQualityLossDetail) lossDetail) :
                getTotalCost((IPressLogisticsLossDetail) lossDetail);
    }



}
