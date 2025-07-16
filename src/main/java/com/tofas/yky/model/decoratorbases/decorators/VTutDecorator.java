package com.tofas.yky.model.decoratorbases.decorators;
/* T40127 @ 16.12.2015. */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tofas.yky.model.decoratorbases.ITut;
import com.tofas.yky.model.decoratorbases.impl.VTut;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Embeddable
public class VTutDecorator implements ITut {

    @Column(name = "TUT_CODE")
    private String tut;

    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TUT_CODE", referencedColumnName = "ORG_MMM", insertable = false, updatable = false)
    private VTut tutObject;

    public VTutDecorator() { }

    public VTutDecorator(String tut, VTut tutObject) {
        this.tut = tut;
        this.tutObject = tutObject;
    }

    @Override
    public String getOrgMmm() {
        return tutObject == null ? "" : tutObject.getOrgMmm();
    }

    @Override
    public String getDescription() {
        return tutObject == null ? "" : tutObject.getDescription();
    }

    public String getTut() {
        return tut;
    }

    public void setTut(String tut) {
        this.tut = tut;
    }

    public VTut getTutObject() {
        return tutObject;
    }

    public void setTutObject(VTut tutObject) {
        this.tutObject = tutObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VTutDecorator that = (VTutDecorator) o;

        return !(tut != null ? !tut.equals(that.tut) : that.tut != null);

    }

    @Override
    public int hashCode() {
        return tut != null ? tut.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "VTutDecorator{" +
                "tut='" + tut + '\'' +
                ", tutObject=" + tutObject +
                '}';
    }


}
