package com.tofas.yky.model.dto.view;
/* T40127 @ 18.10.2015. */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateTimeSerializer;
import com.tofas.yky.model.losses.LossComment;

import java.util.Date;

public class LossCommentViewDto {

    private String comment;

    private String insertedBy;

    @JsonSerialize(using = TfJsonDateTimeSerializer.class)
    private Date insertedDate;

    public LossCommentViewDto(LossComment comment) {
        this.comment = comment.getComment();
        this.insertedBy = comment.getInsertedBy();
        this.insertedDate = comment.getInsertedDate();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getInsertedBy() {
        return insertedBy;
    }

    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }

    public Date getInsertedDate() {
        return insertedDate;
    }

    public void setInsertedDate(Date insertedDate) {
        this.insertedDate = insertedDate;
    }

    @Override
    public String toString() {
        return "LossCommentForSupplierDto{" +
                "comment='" + comment + '\'' +
                ", insertedBy='" + insertedBy + '\'' +
                ", insertedDate=" + insertedDate +
                '}';
    }
}
