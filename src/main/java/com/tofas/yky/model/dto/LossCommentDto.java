package com.tofas.yky.model.dto;

public class LossCommentDto {

    private String message;

    private Long lossId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getLossId() {
        return lossId;
    }

    public void setLossId(Long lossId) {
        this.lossId = lossId;
    }

    @Override
    public String toString() {
        return "LossCommentDto{" +
                "message='" + message + '\'' +
                ", lossId=" + lossId +
                '}';
    }
}
