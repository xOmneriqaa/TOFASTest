package com.tofas.yky.model.losses.production.duration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tofas.yky.model.losses.Loss;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_LOSSES_STD_STEPS")
public class LossStdStepDuration extends AbstractLossStdStepDuration {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "LOSS_ID")
    private Loss loss;

    @ManyToOne
    @JoinColumn(name = "STEP_ID")
    private StandartStep stdStep;

    public Loss getLoss() {
        return loss;
    }

    public void setLoss(Loss loss) {
        this.loss = loss;
    }

    public StandartStep getStdStep() {
        return stdStep;
    }

    public void setStdStep(StandartStep stdStep) {
        this.stdStep = stdStep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LossStdStepDuration that = (LossStdStepDuration) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return !(stdStep != null ? !stdStep.equals(that.stdStep) : that.stdStep != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (stdStep != null ? stdStep.hashCode() : 0);
        return result;
    }

}
