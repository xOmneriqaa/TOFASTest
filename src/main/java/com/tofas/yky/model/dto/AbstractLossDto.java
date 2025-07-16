package com.tofas.yky.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tofas.core.common.serializer.TfJsonDateDeSerializer;

import java.util.Date;

public class AbstractLossDto {

    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    protected Date lossDate;

    protected String description;

    public Date getLossDate() {
        return lossDate;
    }

    public void setLossDate(Date lossDate) {
        this.lossDate = lossDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
