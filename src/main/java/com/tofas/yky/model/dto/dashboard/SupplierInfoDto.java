package com.tofas.yky.model.dto.dashboard;
/* T40127 @ 26.11.2015. */

import com.tofas.yky.enums.dashboard.DashboardSupplierType;

public class SupplierInfoDto {

    private String sapCode;

    private String name;

    private boolean extraSerie;

    private boolean fiatPolo;

    private boolean isValid;

    public SupplierInfoDto(String sapCode, String name, boolean extraSerie, boolean fiatPolo) {
        this.sapCode = sapCode;
        this.name = name;
        this.extraSerie = extraSerie;
        this.fiatPolo = fiatPolo;
        this.isValid = true;
    }

    public SupplierInfoDto() {
        this.sapCode = "-";
        this.name = "-";
        this.extraSerie = false;
        this.fiatPolo = false;
        this.isValid = false;
    }

    public DashboardSupplierType getSupplierType() {
        if(isExtraSerie()) {
            return DashboardSupplierType.EXT_SERIE;
        } else if(isFiatPolo()) {
            return DashboardSupplierType.FIAT_POLO;
        } else {
            return DashboardSupplierType.LOCAL;
        }
    }

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public boolean isLocal() {
        return !extraSerie && !fiatPolo;
    }

    public String getSapCode() {
        return sapCode;
    }

    public void setSapCode(String sapCode) {
        this.sapCode = sapCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExtraSerie() {
        return extraSerie;
    }

    public void setExtraSerie(boolean extraSerie) {
        this.extraSerie = extraSerie;
    }

    public boolean isFiatPolo() {
        return fiatPolo;
    }

    public void setFiatPolo(boolean fiatPolo) {
        this.fiatPolo = fiatPolo;
    }
}
