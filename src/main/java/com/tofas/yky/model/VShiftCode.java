package com.tofas.yky.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "TFS_YKY", name = "V_GRY_SHIFT_CODES")
public class VShiftCode  {

    @Id
    @Column
    private String id;

    @Column(name = "ORG_MMM")
    private String orgMmm;

    @Column(name = "SHIFT_CODE")
    private String shiftCode;


    @Column(name = "SHIFT_NAME")
    private String shiftName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgMmm() {
        return orgMmm;
    }

    public void setOrgMmm(String orgMmm) {
        this.orgMmm = orgMmm;
    }

    public String getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(String shiftCode) {
        this.shiftCode = shiftCode;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VShiftCode that = (VShiftCode) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "VShiftCode{" +
                "id='" + id + '\'' +
                ", orgMmm='" + orgMmm + '\'' +
                ", shiftCode='" + shiftCode + '\'' +
                ", shiftName='" + shiftName + '\'' +
                '}';
    }
}
