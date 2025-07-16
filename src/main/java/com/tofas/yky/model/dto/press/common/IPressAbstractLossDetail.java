package com.tofas.yky.model.dto.press.common;
/* t40127 @ 30.04.2016. */

import java.math.BigDecimal;

public interface IPressAbstractLossDetail {

    BigDecimal get_partBasePrice();

    BigDecimal get_partBasePriceDiff();

    Long getLogisticsLabourHours();

    Long getProductionLabourHours();

    BigDecimal get_blueCollarBaseWage();

    BigDecimal get_logisticsLabourCost();

    BigDecimal get_productionLabourCost();

    BigDecimal get_totalCost();

    BigDecimal getCreditNoteAmount();

    BigDecimal get_paidBalance();

    BigDecimal getScrapHrdParam();

}
