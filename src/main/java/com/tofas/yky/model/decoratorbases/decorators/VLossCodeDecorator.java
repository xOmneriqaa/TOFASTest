package com.tofas.yky.model.decoratorbases.decorators;
/* T40127 @ 19.12.2015. */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tofas.yky.model.decoratorbases.ILossCode;
import com.tofas.yky.model.decoratorbases.impl.VLossCode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Embeddable
public class VLossCodeDecorator implements ILossCode {

    @Column(name = "LOSS_CODE")
    private Long lossCode;

    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LOSS_CODE", referencedColumnName = "LOSS_CODE", insertable = false, updatable = false)
    private VLossCode lossCodeObject;

    public VLossCodeDecorator() { }

    public VLossCodeDecorator(Long lossCode, VLossCode lossCodeObject) {
        this.lossCode = lossCode;
        this.lossCodeObject = lossCodeObject;
    }

    @Override
    public Long getLossCode() {
        return lossCode;
    }

    @Override
    public String getDescription() {
        return lossCodeObject == null ? "" : lossCodeObject.getDescription();
    }

    public void setLossCode(Long lossCode) {
        this.lossCode = lossCode;
    }

    public VLossCode getLossCodeObject() {
        return lossCodeObject;
    }

    public void setLossCodeObject(VLossCode lossCodeObject) {
        this.lossCodeObject = lossCodeObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VLossCodeDecorator that = (VLossCodeDecorator) o;

        return !(lossCode != null ? !lossCode.equals(that.lossCode) : that.lossCode != null);

    }

    @Override
    public int hashCode() {
        return lossCode != null ? lossCode.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "VLossCodeDecorator{" +
                "lossCode=" + lossCode +
                ", lossCodeObject=" + lossCodeObject +
                '}';
    }
}
