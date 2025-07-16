package com.tofas.yky.model.losses.production.duration;

import com.tofas.core.common.model.base.TfBaseEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_STEPS_DURATIONS")
public class StepDuration implements Serializable, TfBaseEntity {

    @EmbeddedId
    private StepDurationId stepDurationId;


    @Column(name = "DURATION")
    private BigDecimal duration;

    public StepDurationId getStepDurationId() {
        return stepDurationId;
    }

    public void setStepDurationId(StepDurationId stepDurationId) {
        this.stepDurationId = stepDurationId;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StepDuration that = (StepDuration) o;

        return !(stepDurationId != null ? !stepDurationId.equals(that.stepDurationId) : that.stepDurationId != null);

    }

    @Override
    public int hashCode() {
        return stepDurationId != null ? stepDurationId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "StepDuration{" +
                "stepDurationId=" + stepDurationId +
                ", duration=" + duration +
                '}';
    }
}
