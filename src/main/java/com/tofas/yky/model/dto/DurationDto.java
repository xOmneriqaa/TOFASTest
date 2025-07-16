package com.tofas.yky.model.dto;

import com.tofas.yky.model.Model;
import com.tofas.yky.model.losses.production.duration.Duration;
import com.tofas.yky.model.losses.production.duration.StepDuration;

import java.util.ArrayList;
import java.util.List;

public class DurationDto {

    private Long id;

    private String disegno;

    private List<String> models;

    private List<StepDurationDto> stepDurations;

    public DurationDto() {
        this.models = new ArrayList<>();
        this.stepDurations = new ArrayList<>();
    }

    public DurationDto(Duration duration) {
        this();

        this.id = duration.getId();
        this.disegno = duration.getPart().getDisegno();

        for(Model model : duration.getModels()){
            this.models.add(model.getCode());
        }


        for(StepDuration stepDuration : duration.getStepDurations()) {
            this.stepDurations.add(new StepDurationDto(stepDuration));
        }
    }

    public String getDisegno() {
        return disegno;
    }

    public void setDisegno(String disegno) {
        this.disegno = disegno;
    }


    public List<String> getModels() {
        return models;
    }

    public void setModels(List<String> models) {
        this.models = models;
    }

        public List<StepDurationDto> getStepDurations() {
        return stepDurations;
    }

    public void setStepDurations(List<StepDurationDto> stepDurations) {
        this.stepDurations = stepDurations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
