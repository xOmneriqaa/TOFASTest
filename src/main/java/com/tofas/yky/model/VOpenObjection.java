package com.tofas.yky.model;
/* T40127 @ 15.11.2015. */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateSerializer;
import com.tofas.yky.enums.LossType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "TFS_YKY", name = "V_OPEN_OBJECTIONS")
public class VOpenObjection {

    @Id
    @Column(name = "OBJ_ID")
    private Long objectionId;

    @Column(name = "LOSS_ID")
    private Long lossId;

    @Column(name = "LOSS_TYPE")
    @Enumerated(value = EnumType.STRING)
    private LossType lossType;


    @JsonSerialize(using = TfJsonDateSerializer.class)
    @Column(name = "LOSS_DATE")
    private Date lossDate;

    @Column(name = "LOSS_DESCRIPTION")
    private String lossDescription;

    @Column(name = "OBJECTION_DESCRIPTION")
    private String objectionDescription;

    @Column(name = "OBJECTION_NAME")
    private String objectionName;

    @Column(name = "INSBY")
    private String insertedBy;

    @JsonSerialize(using = TfJsonDateSerializer.class)
    @Column(name = "INSDATE")
    private Timestamp insertedDate;

    @JsonIgnore
    @Column(name = "OBJECTION_RESPONSIBLES")
    private String rawResponsibles;

    @Transient
    private Set<String> responsibles;

    @PostLoad
    public void init() {
        responsibles = new HashSet<>();

        if(rawResponsibles != null && rawResponsibles.trim().length() > 0) {
            String[] resps = rawResponsibles.split(",");
            Collections.addAll(this.responsibles, resps);
        }
    }


    public Long getObjectionId() {
        return objectionId;
    }

    public void setObjectionId(Long objectionId) {
        this.objectionId = objectionId;
    }

    public Long getLossId() {
        return lossId;
    }

    public void setLossId(Long lossId) {
        this.lossId = lossId;
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

    public String getLossDescription() {
        return lossDescription;
    }

    public void setLossDescription(String lossDescription) {
        this.lossDescription = lossDescription;
    }

    public String getObjectionDescription() {
        return objectionDescription;
    }

    public void setObjectionDescription(String objectionDescription) {
        this.objectionDescription = objectionDescription;
    }

    public String getObjectionName() {
        return objectionName;
    }

    public void setObjectionName(String objectionName) {
        this.objectionName = objectionName;
    }

    public String getInsertedBy() {
        return insertedBy;
    }

    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }

    public Timestamp getInsertedDate() {
        return insertedDate;
    }

    public void setInsertedDate(Timestamp insertedDate) {
        this.insertedDate = insertedDate;
    }

    public String getRawResponsibles() {
        return rawResponsibles;
    }

    public void setRawResponsibles(String rawResponsibles) {
        this.rawResponsibles = rawResponsibles;
    }

    public Set<String> getResponsibles() {
        return responsibles;
    }

    public void setResponsibles(Set<String> responsibles) {
        this.responsibles = responsibles;
    }
}
