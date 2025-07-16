package com.tofas.yky.model.losses;
/* T40127 @ 18.10.2015. */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tofas.core.common.model.base.TfEntity;
import com.tofas.yky.enums.ObjectionStatus;

import javax.persistence.*;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_LOSS_OBJECTIONS")
public class LossObjection extends TfEntity implements Comparable<LossObjection> {

    @Id
    @SequenceGenerator(name="SEQ_YKY_LOSS_OBJECTIONS", sequenceName="SEQ_YKY_LOSS_OBJECTIONS", allocationSize = 1)
    @GeneratedValue(generator="SEQ_YKY_LOSS_OBJECTIONS", strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "LOSS_ID")
    private Loss loss;

    @ManyToOne
    @JoinColumn(name = "OBJECTION_ID")
    private ObjectionType objectionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "OBJECTION_STATUS")
    private ObjectionStatus objectionStatus;

    @Column(name = "OBJECTION_DESC")
    private String objectionDescription;

    @Column(name = "OBJECTION_RESULT_DESC")
    private String objectionResultDescription;

    @Column(name = "STEP")
    private Integer step;

    public LossObjection() {
        step = step == null ? 0 : step;
        objectionStatus = ObjectionStatus.OPEN;
    }

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

    public ObjectionType getObjectionType() {
        return objectionType;
    }

    public void setObjectionType(ObjectionType objectionType) {
        this.objectionType = objectionType;
    }

    public ObjectionStatus getObjectionStatus() {
        return objectionStatus;
    }

    public void setObjectionStatus(ObjectionStatus objectionStatus) {
        this.objectionStatus = objectionStatus;
    }

    public String getObjectionDescription() {
        return objectionDescription;
    }

    public void setObjectionDescription(String objectionDescription) {
        this.objectionDescription = objectionDescription;
    }

    public String getObjectionResultDescription() {
        return objectionResultDescription;
    }

    public void setObjectionResultDescription(String objectionResultDescription) {
        this.objectionResultDescription = objectionResultDescription;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    @Override
    public int compareTo(LossObjection o) {
        return this.step.compareTo(o.step);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        LossObjection that = (LossObjection) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (objectionType != null ? !objectionType.equals(that.objectionType) : that.objectionType != null)
            return false;
        if (objectionStatus != that.objectionStatus) return false;
        if (objectionDescription != null ? !objectionDescription.equals(that.objectionDescription) : that.objectionDescription != null)
            return false;
        if (objectionResultDescription != null ? !objectionResultDescription.equals(that.objectionResultDescription) : that.objectionResultDescription != null)
            return false;
        return !(step != null ? !step.equals(that.step) : that.step != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (objectionType != null ? objectionType.hashCode() : 0);
        result = 31 * result + (objectionStatus != null ? objectionStatus.hashCode() : 0);
        result = 31 * result + (objectionDescription != null ? objectionDescription.hashCode() : 0);
        result = 31 * result + (objectionResultDescription != null ? objectionResultDescription.hashCode() : 0);
        result = 31 * result + (step != null ? step.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LossObjection{" +
                "id=" + id +
                ", objectionType=" + objectionType +
                ", objectionStatus=" + objectionStatus +
                ", objectionDescription='" + objectionDescription + '\'' +
                ", objectionResultDescription='" + objectionResultDescription + '\'' +
                ", step=" + step +
                '}';
    }

}
