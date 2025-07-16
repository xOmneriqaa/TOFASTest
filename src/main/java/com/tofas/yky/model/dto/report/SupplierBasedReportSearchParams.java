package com.tofas.yky.model.dto.report;
/* T40127 @ 12.11.2015. */

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tofas.core.common.serializer.TfJsonDateDeSerializer;
import com.tofas.yky.enums.SupplierType;

import java.util.Date;
import java.util.List;

public class SupplierBasedReportSearchParams {

    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date lossDateStart;

    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date lossDateEnd;

    List<SupplierType> supplierTypes;

    public Date getLossDateStart() {
        return lossDateStart;
    }

    public void setLossDateStart(Date lossDateStart) {
        this.lossDateStart = lossDateStart;
    }

    public Date getLossDateEnd() {
        return lossDateEnd;
    }

    public void setLossDateEnd(Date lossDateEnd) {
        this.lossDateEnd = lossDateEnd;
    }

    public List<SupplierType> getSupplierTypes() {
        return supplierTypes;
    }

    public void setSupplierTypes(List<SupplierType> supplierTypes) {
        this.supplierTypes = supplierTypes;
    }
}
