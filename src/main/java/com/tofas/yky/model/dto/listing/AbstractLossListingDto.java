package com.tofas.yky.model.dto.listing;
/* T40127 @ 25.10.2015. */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateSerializer;
import com.tofas.yky.enums.LossState;
import com.tofas.yky.enums.LossType;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractLossListingDto {

    @Id
    protected Long id;

    protected String description;

    protected LossState lossState;

    protected LossType lossType;

    @JsonSerialize(using = TfJsonDateSerializer.class)
    protected Date lossDate;


    public Date getLossDate() {
        return lossDate;
    }

    public void setLossDate(Date lossDate) {
        this.lossDate = lossDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LossState getLossState() {
        return lossState;
    }

    public void setLossState(LossState lossState) {
        this.lossState = lossState;
    }
}
