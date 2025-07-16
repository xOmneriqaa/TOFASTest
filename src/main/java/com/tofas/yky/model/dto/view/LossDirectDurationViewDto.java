package com.tofas.yky.model.dto.view;
/* T40127 @ 18.10.2015. */

import com.tofas.yky.model.losses.directdurations.LossDirectDuration;

import java.math.BigDecimal;

public class LossDirectDurationViewDto {

    private Long qty;

    private String workType;

    private BigDecimal workTypeWage;

    private Long duration;

    public LossDirectDurationViewDto(LossDirectDuration directDuration) {
        this.qty = directDuration.getQty();
        this.workType = directDuration.getWorkTypeCached();
        this.workTypeWage = directDuration.getWorkTypeWageCached();
        this.duration = directDuration.getDuration();
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public BigDecimal getWorkTypeWage() {
        return workTypeWage;
    }

    public void setWorkTypeWage(BigDecimal workTypeWage) {
        this.workTypeWage = workTypeWage;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "LossDirectDurationForSupplierDto{" +
                "duration=" + duration +
                ", workTypeWage=" + workTypeWage +
                ", workType='" + workType + '\'' +
                ", qty=" + qty +
                '}';
    }
}
