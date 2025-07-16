package com.tofas.yky.model.dto.press.config;
/* t40127 @ 30.05.2016. */

import java.math.BigDecimal;

public class PressParameters {

    private BigDecimal customsCostParam;

    private BigDecimal logisticsCostParam;

    private BigDecimal scrapSalesParamHrd088;

    private BigDecimal scrapSalesParamHrd033;

    private BigDecimal blueCollarBaseWageAsEur;

    public PressParameters() { }

    public BigDecimal getCustomsCostParam() {
        return customsCostParam;
    }

    public void setCustomsCostParam(BigDecimal customsCostParam) {
        this.customsCostParam = customsCostParam;
    }

    public BigDecimal getLogisticsCostParam() {
        return logisticsCostParam;
    }

    public void setLogisticsCostParam(BigDecimal logisticsCostParam) {
        this.logisticsCostParam = logisticsCostParam;
    }

    public BigDecimal getScrapSalesParamHrd088() {
        return scrapSalesParamHrd088;
    }

    public void setScrapSalesParamHrd088(BigDecimal scrapSalesParamHrd088) {
        this.scrapSalesParamHrd088 = scrapSalesParamHrd088;
    }

    public BigDecimal getScrapSalesParamHrd033() {
        return scrapSalesParamHrd033;
    }

    public void setScrapSalesParamHrd033(BigDecimal scrapSalesParamHrd033) {
        this.scrapSalesParamHrd033 = scrapSalesParamHrd033;
    }

    public BigDecimal getBlueCollarBaseWageAsEur() {
        return blueCollarBaseWageAsEur;
    }

    public void setBlueCollarBaseWageAsEur(BigDecimal blueCollarBaseWageAsEur) {
        this.blueCollarBaseWageAsEur = blueCollarBaseWageAsEur;
    }
}
