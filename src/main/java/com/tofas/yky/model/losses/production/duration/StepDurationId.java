package com.tofas.yky.model.losses.production.duration;
/* T40127 @ 03.11.2015. */

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class StepDurationId implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "STEP_ID")
    private Step step;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DURATION_ID")
    private Duration durationDef;

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public Duration getDurationDef() {
        return durationDef;
    }

    public void setDurationDef(Duration durationDef) {
        this.durationDef = durationDef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StepDurationId that = (StepDurationId) o;

        if (step != null ? !step.equals(that.step) : that.step != null) return false;
        return !(durationDef != null ? !durationDef.equals(that.durationDef) : that.durationDef != null);

    }

    @Override
    public int hashCode() {
        int result = step != null ? step.hashCode() : 0;
        result = 31 * result + (durationDef != null ? durationDef.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StepDurationKey{" +
                "step=" + step +
                '}';
    }
}
