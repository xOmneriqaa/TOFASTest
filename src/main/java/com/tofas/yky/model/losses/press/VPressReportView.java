package com.tofas.yky.model.losses.press;
/* T40127 @ 10.06.2016. */

import com.tofas.yky.enums.LossState;
import com.tofas.yky.enums.PressLossType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "TFS_YKY", name = "V_PRESS_REPORT_VIEW")
public class VPressReportView {

    @Id
    private String id;

    @Column(name = "LOSS_ID")
    private Long lossId;

    @Column(name = "LOSS_DATE")
    private Date lossDate;

    @Column(name = "SUPPLIER_CODE")
    private String supplierCode;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "PRESS_LOSS_TYPE")
    private PressLossType pressLossType;

    @Column(name = "ROLL_NO")
    private String rollNo;

    @Column(name = "DISEGNO")
    private String disegno;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "LOSS_STATE")
    private LossState lossState;

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

    public Date getLossDate() {
        return lossDate;
    }

    public void setLossDate(Date lossDate) {
        this.lossDate = lossDate;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public PressLossType getPressLossType() {
        return pressLossType;
    }

    public void setPressLossType(PressLossType pressLossType) {
        this.pressLossType = pressLossType;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getDisegno() {
        return disegno;
    }

    public void setDisegno(String disegno) {
        this.disegno = disegno;
    }

    public LossState getLossState() {
        return lossState;
    }

    public void setLossState(LossState lossState) {
        this.lossState = lossState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VPressReportView that = (VPressReportView) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "VPressReportView{" +
                "id='" + id + '\'' +
                ", lossId=" + lossId +
                ", lossDate=" + lossDate +
                ", supplierCode='" + supplierCode + '\'' +
                ", pressLossType=" + pressLossType +
                ", rollNo='" + rollNo + '\'' +
                ", disegno='" + disegno + '\'' +
                ", lossState=" + lossState +
                '}';
    }
}
