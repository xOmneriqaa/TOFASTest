package com.tofas.yky.model.dto;
/* T40127 @ 19.10.2015. */

public class ObjectionResultDto {

    private Long objectionInstanceId;

    private Long lossId;

    private String description;

    private boolean status;

    public Long getObjectionInstanceId() {
        return objectionInstanceId;
    }

    public void setObjectionInstanceId(Long objectionInstanceId) {
        this.objectionInstanceId = objectionInstanceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Long getLossId() {
        return lossId;
    }

    public void setLossId(Long lossId) {
        this.lossId = lossId;
    }
}
