package com.tofas.yky.model.dto;

/**
 * Created by T40127 on 10.10.2015.
 */
public class StepForLossDto {

    private Long stepId;

    private String stepName;

    private Double stepDuration;

    private boolean durationExists;

    private boolean standartStep;

    private Long qty;

    private Long durationId;

    public Long getStepId() {
        return stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public Double getStepDuration() {
        return stepDuration;
    }

    public void setStepDuration(Double stepDuration) {
        this.stepDuration = stepDuration;
    }

    public boolean isDurationExists() {
        return durationExists;
    }

    public void setDurationExists(boolean durationExists) {
        this.durationExists = durationExists;
    }

    public boolean isStandartStep() {
        return standartStep;
    }

    public void setStandartStep(boolean standartStep) {
        this.standartStep = standartStep;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Long getDurationId() {
        return durationId;
    }

    public void setDurationId(Long durationId) {
        this.durationId = durationId;
    }

    @Override
    public String toString() {
        return "StepForLossDto{" +
                "stepId=" + stepId +
                ", stepName='" + stepName + '\'' +
                ", stepDuration=" + stepDuration +
                ", durationExists=" + durationExists +
                ", standartStep=" + standartStep +
                ", qty=" + qty +
                ", durationId=" + durationId +
                '}';
    }
}
