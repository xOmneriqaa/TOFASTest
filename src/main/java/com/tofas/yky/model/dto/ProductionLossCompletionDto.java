package com.tofas.yky.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tofas.core.common.serializer.TfJsonDateTimeDeSerializer;

import java.util.Date;

public class ProductionLossCompletionDto {

    private Long lossId;

    private Long qty;

    private String completedTut;

    private String completedShift;

    @JsonDeserialize(using = TfJsonDateTimeDeSerializer.class)
    private Date completionDate;

    private Long completionDuration;

    private Long completionStepId;

    private Long completionStepIsStd;

    public Long getLossId() {
        return lossId;
    }

    public void setLossId(Long lossId) {
        this.lossId = lossId;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getCompletedTut() {
        return completedTut;
    }

    public void setCompletedTut(String completedTut) {
        this.completedTut = completedTut;
    }

    public String getCompletedShift() {
        return completedShift;
    }

    public void setCompletedShift(String completedShift) {
        this.completedShift = completedShift;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Long getCompletionDuration() {
        return completionDuration;
    }

    public void setCompletionDuration(Long completionDuration) {
        this.completionDuration = completionDuration;
    }

    public Long getCompletionStepId() {
        return completionStepId;
    }

    public void setCompletionStepId(Long completionStepId) {
        this.completionStepId = completionStepId;
    }

    public Long getCompletionStepIsStd() {
        return completionStepIsStd;
    }

    public void setCompletionStepIsStd(Long completionStepIsStd) {
        this.completionStepIsStd = completionStepIsStd;
    }

    @Override
    public String toString() {
        return "ProductionLossCompletionDto{" +
                "lossId=" + lossId +
                ", qty=" + qty +
                ", completedTut='" + completedTut + '\'' +
                ", completedShift='" + completedShift + '\'' +
                ", completionDate=" + completionDate +
                '}';
    }
}
