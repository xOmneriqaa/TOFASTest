package com.tofas.yky.model.dto.report;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tofas.core.common.serializer.TfJsonDateDeSerializer;

import java.util.Date;

/**
 * Created by mndeveci
 */
public class EfficiencyReportSearchParamsDto {

    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date lossDateStart;

    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date lossDateEnd;

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
}
