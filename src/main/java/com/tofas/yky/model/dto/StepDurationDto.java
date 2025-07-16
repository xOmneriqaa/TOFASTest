package com.tofas.yky.model.dto;

import com.tofas.yky.model.losses.production.duration.Step;
import com.tofas.yky.model.losses.production.duration.StepDuration;

public class StepDurationDto {

    private StepDto stepDto;

    private Long duration;

    public StepDurationDto() { }

    public StepDurationDto(StepDuration stepDuration) {
        this.stepDto = new StepDto(stepDuration.getStepDurationId().getStep());
        this.duration = stepDuration.getDuration().longValue();
    }

    public StepDurationDto(Step step) {
        this.stepDto = new StepDto(step);
        this.duration = 0L;
    }

    public StepDto getStepDto() {
        return stepDto;
    }

    public void setStepDto(StepDto stepDto) {
        this.stepDto = stepDto;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "StepDurationDto{" +
                "stepDto=" + stepDto +
                ", duration=" + duration +
                '}';
    }
}
