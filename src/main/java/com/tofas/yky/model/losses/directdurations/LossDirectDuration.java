package com.tofas.yky.model.losses.directdurations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.model.losses.WorkType;

import javax.persistence.*;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_LOSS_DIRECT_DURATIONS")
public class LossDirectDuration extends AbstractLossDirectDuration {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "LOSS_ID")
    private Loss loss;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "WORK_TYPE_ID")
    private WorkType workType;

    public Loss getLoss() {
        return loss;
    }

    public void setLoss(Loss loss) {
        this.loss = loss;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }


}
