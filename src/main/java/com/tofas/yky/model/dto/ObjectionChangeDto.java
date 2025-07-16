package com.tofas.yky.model.dto;
/* T40127 @ 19.10.2015. */

public class ObjectionChangeDto {

    private Long objectionInstanceId;

    private Long objectionId;

    private String description;

    public Long getObjectionInstanceId() {
        return objectionInstanceId;
    }

    public void setObjectionInstanceId(Long objectionInstanceId) {
        this.objectionInstanceId = objectionInstanceId;
    }

    public Long getObjectionId() {
        return objectionId;
    }

    public void setObjectionId(Long objectionId) {
        this.objectionId = objectionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
