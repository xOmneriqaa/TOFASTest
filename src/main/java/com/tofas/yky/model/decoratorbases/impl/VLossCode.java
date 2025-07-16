package com.tofas.yky.model.decoratorbases.impl;

import com.tofas.yky.model.decoratorbases.ILossCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "TFS_YKY", name = "V_GRY_LOSS_CODES")
public class VLossCode implements ILossCode {

    @Id
    @Column(name = "LOSS_CODE")
    private Long lossCode;

    @Column
    private String description;

    @Override
    public Long getLossCode() {
        return lossCode;
    }

    public void setLossCode(Long lossCode) {
        this.lossCode = lossCode;
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

        VLossCode vLossCode = (VLossCode) o;

        if (lossCode != null ? !lossCode.equals(vLossCode.lossCode) : vLossCode.lossCode != null) return false;
        return !(description != null ? !description.equals(vLossCode.description) : vLossCode.description != null);

    }

    @Override
    public int hashCode() {
        int result = lossCode != null ? lossCode.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VLossCode{" +
                "lossCode=" + lossCode +
                ", description='" + description + '\'' +
                '}';
    }
}
