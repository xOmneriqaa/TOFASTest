package com.tofas.yky.model.decoratorbases;
/* T40127 @ 19.12.2015. */

import com.tofas.yky.enums.dashboard.DashboardSupplierType;

public interface ISupplier {
    boolean isDirectMaterial();

    boolean isAuxiliaryMaterial();

    boolean isSparePart();

    boolean isSheet();

    boolean isExtraSerie();

    Long getCode();

    String getSapCode();

    String getName();

    String getPoloFlag();

    default DashboardSupplierType getSupplierType() {
        if(isExtraSerie()) {
            return DashboardSupplierType.EXT_SERIE;
        } else if(getPoloFlag() != null && getPoloFlag().trim().length() > 0) {
            return DashboardSupplierType.FIAT_POLO;
        } else {
            return DashboardSupplierType.LOCAL;
        }
    }
}
