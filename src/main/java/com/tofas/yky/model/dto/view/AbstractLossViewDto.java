package com.tofas.yky.model.dto.view;
/* T40127 @ 18.10.2015. */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateSerializer;
import com.tofas.yky.enums.LossState;
import com.tofas.yky.enums.LossType;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.model.losses.LossAttachment;
import com.tofas.yky.model.losses.LossComment;
import com.tofas.yky.model.losses.LossObjection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AbstractLossViewDto {

    private Long id;

    private LossType lossType;

    @JsonSerialize(using = TfJsonDateSerializer.class)
    private Date lossDate;

    private String description;

    private LossState currentLossState;

    private LossObjectioViewDto currentObjection;

    private List<LossAttachmentViewDto> attachments;

    private List<LossCommentViewDto> comments;

    private List<LossObjectioViewDto> objections;

    public AbstractLossViewDto(Loss loss) {
        this.id = loss.getId();
        this.lossType = loss.getLossType();
        this.lossDate = loss.getLossDate();
        this.description = loss.getDescription();
        this.currentLossState = loss.getCurrentState();
        this.currentObjection = loss.getCurrentObjection() != null ? new LossObjectioViewDto(loss.getCurrentObjection()) : null;

        attachments = new ArrayList<>();
        comments = new ArrayList<>();
        objections = new ArrayList<>();

        for(LossAttachment attach : loss.getAttachments()) {
            attachments.add(new LossAttachmentViewDto(attach));
        }

        for(LossComment comment : loss.getComments()) {
            comments.add(new LossCommentViewDto(comment));
        }

        for(LossObjection objection : loss.getObjections()) {
            objections.add(new LossObjectioViewDto(objection));
        }
    }

    public LossObjectioViewDto getCurrentObjection() {
        return currentObjection;
    }

    public void setCurrentObjection(LossObjectioViewDto currentObjection) {
        this.currentObjection = currentObjection;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LossType getLossType() {
        return lossType;
    }

    public void setLossType(LossType lossType) {
        this.lossType = lossType;
    }

    public Date getLossDate() {
        return lossDate;
    }

    public void setLossDate(Date lossDate) {
        this.lossDate = lossDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LossState getCurrentLossState() {
        return currentLossState;
    }

    public void setCurrentLossState(LossState currentLossState) {
        this.currentLossState = currentLossState;
    }

    public List<LossAttachmentViewDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<LossAttachmentViewDto> attachments) {
        this.attachments = attachments;
    }

    public List<LossCommentViewDto> getComments() {
        return comments;
    }

    public void setComments(List<LossCommentViewDto> comments) {
        this.comments = comments;
    }

    public List<LossObjectioViewDto> getObjections() {
        return objections;
    }

    public void setObjections(List<LossObjectioViewDto> objections) {
        this.objections = objections;
    }

    @Override
    public String toString() {
        return "AbstractLossForSupplierDto{" +
                "id=" + id +
                ", lossType=" + lossType +
                ", lossDate=" + lossDate +
                ", description='" + description + '\'' +
                ", currentLossState=" + currentLossState +
                ", attachments=" + attachments +
                ", comments=" + comments +
                ", objections=" + objections +
                '}';
    }
}
