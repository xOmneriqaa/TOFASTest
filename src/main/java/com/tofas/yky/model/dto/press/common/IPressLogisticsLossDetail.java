package com.tofas.yky.model.dto.press.common;
/* t40127 @ 30.04.2016. */

import java.math.BigDecimal;

public interface IPressLogisticsLossDetail extends IPressAbstractLossDetail {

    BigDecimal get_rollWeight();

    BigDecimal get_partSliceWidth();

    BigDecimal get_width();

    BigDecimal get_oKPartWeight();

    BigDecimal get_scrapWeight();

    BigDecimal getSlicePerTonne();

    BigDecimal get_calculatedInvoiceAmount();

    BigDecimal get_alternativeDisegnoBasePrice();

    BigDecimal get_usedPartPriceDifference();

    BigDecimal get_sliceCost();

    BigDecimal get_scrapCostDiscountPrice();

    BigDecimal get_scrapPartCost();

    BigDecimal getFirmTransportationCost();

    BigDecimal getAltDisegnoBasePriceDiff();



}
