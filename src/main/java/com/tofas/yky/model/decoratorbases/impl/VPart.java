package com.tofas.yky.model.decoratorbases.impl;

import com.tofas.yky.model.decoratorbases.IPart;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(schema = "TFS_YKY", name = "V_PARTS")
public class VPart implements Serializable, IPart {

    @Id
    @Column
    private String disegno;

    @Column(name = "DESC_TR")
    private String descTr;

    @Column(name = "DESC_ENG")
    private String descEn;

    @Column(name = "DESC_IT")
    private String descIt;

    @Override
    public String getDisegno() {
        return disegno;
    }

    public void setDisegno(String disegno) {
        this.disegno = disegno;
    }

    @Override
    public String getDescTr() {
        return descTr;
    }

    public void setDescTr(String descTr) {
        this.descTr = descTr;
    }

    @Override
    public String getDescEn() {
        return descEn;
    }

    public void setDescEn(String descEn) {
        this.descEn = descEn;
    }

    @Override
    public String getDescIt() {
        return descIt;
    }

    public void setDescIt(String descIt) {
        this.descIt = descIt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VPart vPart = (VPart) o;

        return !(disegno != null ? !disegno.equals(vPart.disegno) : vPart.disegno != null);

    }

    @Override
    public int hashCode() {
        return disegno != null ? disegno.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "VPart{" +
                "disegno='" + disegno + '\'' +
                ", descTr='" + descTr + '\'' +
                ", descEn='" + descEn + '\'' +
                ", descIt='" + descIt + '\'' +
                '}';
    }
}
