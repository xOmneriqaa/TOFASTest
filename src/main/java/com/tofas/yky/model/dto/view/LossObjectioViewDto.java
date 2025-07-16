package com.tofas.yky.model.dto.view;
/* T40127 @ 18.10.2015. */

import com.tofas.yky.enums.ObjectionStatus;
import com.tofas.yky.model.losses.LossObjection;
import com.tofas.yky.model.losses.ObjectionType;

public class LossObjectioViewDto {

    private ObjectionType objectionType;

    private ObjectionStatus objectionStatus;

    private String objectionDescription;

    private String objectionResultDescription;

    private Integer step;

    public LossObjectioViewDto(LossObjection objection) {
        this.objectionType = objection.getObjectionType();
        this.objectionStatus = objection.getObjectionStatus();
        this.objectionDescription = objection.getObjectionDescription();
        this.objectionResultDescription = objection.getObjectionResultDescription();
        this.step = objection.getStep();
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
    public String toString() {
        return "LossObjectioForSupplierDto{" +
                "objectionType=" + objectionType +
                ", objectionStatus=" + objectionStatus +
                ", objectionDescription='" + objectionDescription + '\'' +
                ", objectionResultDescription='" + objectionResultDescription + '\'' +
                ", step=" + step +
                '}';
    }
}
