package com.tofas.yky.model.losses.views;
/* t40127 @ 11.05.2016. */

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tofas.core.common.serializer.TfJsonDateTimeDeSerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(schema = "TFS_YKY", name = "V_UN_APPROVED_LOSSES")
public class VUnApprovedLosses {

    @Id
    private String id;

    @Column(name = "LOSS_ID")
    private Long lossId;

    private String description;

    @JsonDeserialize(using = TfJsonDateTimeDeSerializer.class)
    @Column(name = "LOSS_DATE")
    private Date lossDate;

    private String disegno;

    @Column(name = "DISEGNO_DESC")
    private String disegnoDesc;

    @Column(name = "LOSS_SOURCE")
    private String lossSource;

    @Column(name = "TABLE_CODE")
    private String tableCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getLossId() {
        return lossId;
    }

    public void setLossId(Long lossId) {
        this.lossId = lossId;
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

    public String getDisegno() {
        return disegno;
    }

    public void setDisegno(String disegno) {
        this.disegno = disegno;
    }

    public String getDisegnoDesc() {
        return disegnoDesc;
    }

    public void setDisegnoDesc(String disegnoDesc) {
        this.disegnoDesc = disegnoDesc;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getLossSource() {
        return lossSource;
    }

    public void setLossSource(String lossSource) {
        this.lossSource = lossSource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VUnApprovedLosses that = (VUnApprovedLosses) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "VUnApprovedLosses{" +
                "id='" + id + '\'' +
                ", lossId=" + lossId +
                ", description='" + description + '\'' +
                ", lossDate=" + lossDate +
                ", disegno='" + disegno + '\'' +
                ", disegnoDesc='" + disegnoDesc + '\'' +
                ", tableCode='" + tableCode + '\'' +
                '}';
    }
}
