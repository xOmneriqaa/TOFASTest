package com.tofas.yky.model.dto.press.common;
/* t40127 @ 30.04.2016. */

import java.math.BigDecimal;

public interface IPressQualityLossDetail extends IPressAbstractLossDetail {

    BigDecimal get_cutPartWeight();

    BigDecimal get_pressedPartWeight();

    BigDecimal getCutScrapQty();

    BigDecimal getPressedScrapQty();

    BigDecimal getCutPartBaseWeight();

    BigDecimal getPressedPartBaseWeight();

    BigDecimal get_partCustomsBasePrice();

    BigDecimal getCustomsCostParam();

    BigDecimal get_totalScrap();

    BigDecimal getRollScrapWeight();

    BigDecimal get_scrapHrd03();

    BigDecimal getScrapSoldOut();

    BigDecimal get_scrapPartCost();

    BigDecimal get_scrapCostDiscount();

    BigDecimal getScrapSalePrice();

    BigDecimal getLogisticsCostParam();

    BigDecimal get_logisticsCost();

}
