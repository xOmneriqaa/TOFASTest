package com.tofas.yky.model.decoratorbases.decorators;
/* T40127 @ 16.12.2015. */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tofas.yky.model.decoratorbases.IPart;
import com.tofas.yky.model.decoratorbases.impl.VPart;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class VPartDecorator implements IPart {

    private String disegno;

    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DISEGNO", referencedColumnName = "DISEGNO", insertable = false, updatable = false)
    private VPart partObject;

    public VPartDecorator() { }

    public VPartDecorator(String disegno, VPart partObject) {
        this.disegno = disegno;
        this.partObject = partObject;
    }

    public String getDisegno() {
        return disegno;
    }

    public void setDisegno(String disegno) {
        this.disegno = disegno;
    }

    @Override
    public String getDescTr() {
        return partObject == null ? "" : partObject.getDescTr();
    }


    @Override
    public String getDescEn() {
        return partObject == null ? "" : partObject.getDescEn();
    }


    @Override
    public String getDescIt() {
        return partObject == null ? "" : partObject.getDescIt();
    }


    public VPart getPartObject() {
        return partObject;
    }

    public void setPartObject(VPart partObject) {
        this.partObject = partObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VPartDecorator that = (VPartDecorator) o;

        return !(disegno != null ? !disegno.equals(that.disegno) : that.disegno != null);

    }

    @Override
    public int hashCode() {
        return disegno != null ? disegno.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "VPartDecorator{" +
                "disegno='" + disegno + '\'' +
                ", partObject=" + partObject +
                '}';
    }
}
