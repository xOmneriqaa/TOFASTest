package com.tofas.yky.model.losses.production.duration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tofas.yky.model.losses.Loss;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_LOSSES_STEP_DURATIONS")
public class LossStepDuration extends AbstractLossStepDuration {


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "LOSS_ID")
    private Loss loss;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "DURATION_ID")
    private Duration duration;

    @ManyToOne
    @JoinColumn(name = "STEP_ID")
    private Step step;


    public Loss getLoss() {
        return loss;
    }

    public void setLoss(Loss loss) {
        this.loss = loss;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LossStepDuration that = (LossStepDuration) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        return !(step != null ? !step.equals(that.step) : that.step != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (step != null ? step.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LossStepDuration{" +
                "id=" + id +
                ", duration=" + duration +
                ", qty=" + qty +
                ", stepNameCached='" + stepNameCached + '\'' +
                ", stepDurationCached=" + stepDurationCached +
                '}';
    }


}
