package com.tofas.yky.model.dto;
/* T40127 @ 18.10.2015. */

import com.tofas.yky.enums.LossType;

public class LossObjectionDto {

    private Long lossId;

    private Long objectionTypeId;

    private String objectionMessage;

    private LossType lossType;

    public Long getLossId() {
        return lossId;
    }

    public void setLossId(Long lossId) {
        this.lossId = lossId;
    }

    public Long getObjectionTypeId() {
        return objectionTypeId;
    }

    public void setObjectionTypeId(Long objectionTypeId) {
        this.objectionTypeId = objectionTypeId;
    }

    public String getObjectionMessage() {
        return objectionMessage;
    }

    public void setObjectionMessage(String objectionMessage) {
        this.objectionMessage = objectionMessage;
    }

    public LossType getLossType() {
        return lossType;
    }

    public void setLossType(LossType lossType) {
        this.lossType = lossType;
    }
}
