package com.tofas.yky.model.decoratorbases.impl;

import com.tofas.yky.model.decoratorbases.ITut;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "TFS_YKY", name = "V_TUTS")
public class VTut implements ITut {

    @Id
    @Column(name = "ORG_MMM")
    private String orgMmm;

    @Column(name = "DESCR")
    private String description;

    @Override
    public String getOrgMmm() {
        return orgMmm;
    }

    public void setOrgMmm(String orgMmm) {
        this.orgMmm = orgMmm;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VTut vTut = (VTut) o;

        return !(orgMmm != null ? !orgMmm.equals(vTut.orgMmm) : vTut.orgMmm != null);

    }

    @Override
    public int hashCode() {
        return orgMmm != null ? orgMmm.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "VTut{" +
                "orgMmm='" + orgMmm + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
