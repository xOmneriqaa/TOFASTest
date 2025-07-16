package com.tofas.yky.model.losses.production;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.model.base.TfEntity;
import com.tofas.core.common.serializer.TfJsonDateTimeSerializer;
import com.tofas.yky.model.losses.Loss;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_LOSS_COMPLETION")
public class LossCompletion extends TfEntity {

    @Id
    @SequenceGenerator(name="SEQ_YKY_LOSS_COMPLETION", sequenceName="SEQ_YKY_LOSS_COMPLETION", allocationSize = 1)
    @GeneratedValue(generator="SEQ_YKY_LOSS_COMPLETION", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LOSS_ID")
    @JsonIgnore
    private Loss loss;

    @Column(name = "TUT_CODE")
    private String tutCode;

    @Column(name = "SHIFT_CODE")
    private String shiftCode;

    @JsonSerialize(using = TfJsonDateTimeSerializer.class)
    @Column(name = "COMPLETION_DATE")
    private Date completionDate;

    @Column(name = "COMPLETION_QTY")
    private Long completionQty;

    @Column(name = "COMPLETION_DURATION")
    private Long completionDuration;

    @Column(name = "COMPLETION_STEP_ID")
    private Long completionStepId;

    @Column(name = "COMPLETION_STEP_IS_STD")
    private Long completionStepIsStd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Loss getLoss() {
        return loss;
    }

    public void setLoss(Loss loss) {
        this.loss = loss;
    }

    public String getTutCode() {
        return tutCode;
    }

    public void setTutCode(String tutCode) {
        this.tutCode = tutCode;
    }

    public String getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(String shiftCode) {
        this.shiftCode = shiftCode;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Long getCompletionQty() {
        return completionQty;
    }

    public void setCompletionQty(Long completionQty) {
        this.completionQty = completionQty;
    }

    public Long getCompletionDuration() {
        return completionDuration;
    }

    public void setCompletionDuration(Long completionDuration) {
        this.completionDuration = completionDuration;
    }

    public Long getCompletionStepId() {
        return completionStepId;
    }

    public void setCompletionStepId(Long completionStepId) {
        this.completionStepId = completionStepId;
    }

    public Long getCompletionStepIsStd() {
        return completionStepIsStd;
    }

    public void setCompletionStepIsStd(Long completionStepIsStd) {
        this.completionStepIsStd = completionStepIsStd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        LossCompletion that = (LossCompletion) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LossCompletion{" +
                "id=" + id +
                ", tutCode='" + tutCode + '\'' +
                ", shiftCode='" + shiftCode + '\'' +
                ", completionDate=" + completionDate +
                ", completionQty=" + completionQty +
                '}';
    }
}
