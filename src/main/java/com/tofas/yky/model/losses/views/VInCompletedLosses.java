package com.tofas.yky.model.losses.views;
/* t40127 @ 28.03.2016. */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateSerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(schema = "TFS_YKY", name = "V_INCOMPLETED_LOSSES")
public class VInCompletedLosses {

    @Id
    private Long id;

    @Column(name = "SQP_NO")
    private String sqpNo;

    private String disegno;

    @Column(name = "TUT_CODE")
    private String tutCode;

    @Column(name = "EFFECTED_TUTS")
    private String effectedTuts;

    private String description;

    @JsonSerialize(using = TfJsonDateSerializer.class)
    @Column(name = "LOSS_DATE")
    private Date lossDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSqpNo() {
        return sqpNo;
    }

    public void setSqpNo(String sqpNo) {
        this.sqpNo = sqpNo;
    }

    public String getDisegno() {
        return disegno;
    }

    public void setDisegno(String disegno) {
        this.disegno = disegno;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLossDate() {
        return lossDate;
    }

    public void setLossDate(Date lossDate) {
        this.lossDate = lossDate;
    }

    public String getTutCode() {
        return tutCode;
    }

    public void setTutCode(String tutCode) {
        this.tutCode = tutCode;
    }

    public String getEffectedTuts() {
        return effectedTuts;
    }

    public void setEffectedTuts(String effectedTuts) {
        this.effectedTuts = effectedTuts;
    }

    @Override
    public String toString() {
        return "VUnCompletedLosses{" +
                "id=" + id +
                ", sqpNo='" + sqpNo + '\'' +
                ", disegno='" + disegno + '\'' +
                ", description='" + description + '\'' +
                ", lossDate=" + lossDate +
                '}';
    }
}
