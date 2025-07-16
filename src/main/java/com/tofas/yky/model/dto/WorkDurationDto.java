package com.tofas.yky.model.dto;

public class WorkDurationDto {

    private Long workType;

    private Long qty;

    private Long workDuration;

    public Long getWorkType() {
        return workType;
    }

    public void setWorkType(Long workType) {
        this.workType = workType;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Long getWorkDuration() {
        return workDuration;
    }

    public void setWorkDuration(Long workDuration) {
        this.workDuration = workDuration;
    }

    @Override
    public String toString() {
        return "WorkDurationDto{" +
                "workType=" + workType +
                ", qty=" + qty +
                ", workDuration=" + workDuration +
                '}';
    }
}
