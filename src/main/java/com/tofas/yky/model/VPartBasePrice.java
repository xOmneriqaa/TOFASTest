package com.tofas.yky.model;
/* T40127 @ 15.10.2015. */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateSerializer;
import com.tofas.yky.enums.BaseUnit;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(schema = "TFS_YKY", name = "V_PART_BASE_PRICE")
public class VPartBasePrice {

    @Id
    private String id;

    @Column(name = "SAP_FIRMA_KODU")
    private String firmCode;

    @Column(name = "RESIM_NO")
    private String disegno;

    @Column(name = "KARYUZ")
    private Integer profitPercent;

    @JsonSerialize(using = TfJsonDateSerializer.class)
    @Column(name = "YUKLEME_TARIHI")
    private Date importedDate;

    @JsonSerialize(using = TfJsonDateSerializer.class)
    @Column(name = "CARI_BF_TARIH")
    private Date priceDate;

    @Column(name = "CARI_BF")
    private BigDecimal currentBasePrice;

    @Column(name = "PARA_BIRIMI")
    private String currencyType;

    @Column(name = "OLCU_BIRIM")
    private String measureType;

    @Column(name = "BASE_UNIT")
    private BigDecimal baseUnitPrice;

    @Column(name = "DOVIZ_KURU")
    private BigDecimal currencyRate;

    @Column(name = "DOVIZ_KURU_BF_TARIHI")
    private BigDecimal currencyRateAtBasePriceDate;

    @Column(name = "EUR_KURU_BF_TARIHI")
    private BigDecimal euroRateAtBasePriceDate;

    @Transient
    private BaseUnit baseUnit;

    @PostLoad
    public void setBaseUnit() {
        this.baseUnit = BaseUnit.getUnit(this.measureType);
    }

    public BigDecimal getBaseUnitPriceInEur() {
        return baseUnitPrice.multiply(euroRateAtBasePriceDate);
    }

    public BigDecimal getBaseUnitPriceInTl() {
        return baseUnitPrice.multiply(currencyRate);
    }

    /* ------------------------------------------- */

    public String getBaseUnitText() {
        return baseUnit.getLabel();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirmCode() {
        return firmCode;
    }

    public void setFirmCode(String firmCode) {
        this.firmCode = firmCode;
    }

    public String getDisegno() {
        return disegno;
    }

    public void setDisegno(String disegno) {
        this.disegno = disegno;
    }

    public Integer getProfitPercent() {
        return profitPercent;
    }

    public void setProfitPercent(Integer profitPercent) {
        this.profitPercent = profitPercent;
    }

    public Date getImportedDate() {
        return importedDate;
    }

    public void setImportedDate(Date importedDate) {
        this.importedDate = importedDate;
    }

    public Date getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(Date priceDate) {
        this.priceDate = priceDate;
    }

    public BigDecimal getCurrentBasePrice() {
        return currentBasePrice;
    }

    public void setCurrentBasePrice(BigDecimal currentBasePrice) {
        this.currentBasePrice = currentBasePrice;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getMeasureType() {
        return measureType;
    }

    public void setMeasureType(String measureType) {
        this.measureType = measureType;
    }

    public BigDecimal getBaseUnitPrice() {
        return baseUnitPrice;
    }

    public void setBaseUnitPrice(BigDecimal baseUnitPrice) {
        this.baseUnitPrice = baseUnitPrice;
    }

    public BaseUnit getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(BaseUnit baseUnit) {
        this.baseUnit = baseUnit;
    }

    public BigDecimal getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(BigDecimal currencyRate) {
        this.currencyRate = currencyRate;
    }

    public BigDecimal getCurrencyRateAtBasePriceDate() {
        return currencyRateAtBasePriceDate;
    }

    public void setCurrencyRateAtBasePriceDate(BigDecimal currencyRateAtBasePriceDate) {
        this.currencyRateAtBasePriceDate = currencyRateAtBasePriceDate;
    }

    public BigDecimal getEuroRateAtBasePriceDate() {
        return euroRateAtBasePriceDate;
    }

    public void setEuroRateAtBasePriceDate(BigDecimal euroRateAtBasePriceDate) {
        this.euroRateAtBasePriceDate = euroRateAtBasePriceDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VPartBasePrice that = (VPartBasePrice) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "VPartBasePrice{" +
                "id='" + id + '\'' +
                ", firmCode=" + firmCode +
                ", disegno='" + disegno + '\'' +
                ", profitPercent=" + profitPercent +
                ", importedDate=" + importedDate +
                ", priceDate=" + priceDate +
                ", currentBasePrice=" + currentBasePrice +
                ", currencyType='" + currencyType + '\'' +
                ", measureType='" + measureType + '\'' +
                ", baseUnit=" + baseUnit +
                ", currencyRate=" + currencyRate +
                '}';
    }
}
