package com.tofas.yky.model.decoratorbases.decorators;
/* T40127 @ 19.12.2015. */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tofas.yky.model.decoratorbases.ISupplier;
import com.tofas.yky.model.decoratorbases.impl.VSupplier;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Embeddable
public class VSupplierDecorator implements ISupplier {

    @Column(name = "SUPPLIER_CODE")
    private String supplierCode;

    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SUPPLIER_CODE", referencedColumnName = "SUPPLIER_SAP_CODE", insertable = false, updatable = false)
    private VSupplier supplier;

    public VSupplierDecorator() { }

    public VSupplierDecorator(String supplierCode, VSupplier supplier) {
        this.supplierCode = supplierCode;
        this.supplier = supplier;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public VSupplier getSupplier() {
        return supplier;
    }

    public void setSupplier(VSupplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public boolean isDirectMaterial() {
        return supplier != null && supplier.isDirectMaterial();
    }

    @Override
    public boolean isAuxiliaryMaterial() {
        return supplier != null && supplier.isAuxiliaryMaterial();
    }

    @Override
    public boolean isSparePart() {
        return supplier != null && supplier.isSparePart();
    }

    @Override
    public boolean isSheet() {
        return supplier != null && supplier.isSheet();
    }

    @Override
    public boolean isExtraSerie() {
        return supplier != null && supplier.isExtraSerie();
    }

    @Override
    public Long getCode() {
        return supplier == null ? -1 : supplier.getCode();
    }

    @Override
    public String getSapCode() {
        return supplierCode;
    }

    @Override
    public String getName() {
        return supplier == null ? "" : supplier.getName();
    }

    @Override
    public String getPoloFlag() {
        return supplier == null ? "" : supplier.getPoloFlag();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if(o instanceof ISupplier) {
            ISupplier that = (ISupplier) o;

            return that.getSapCode().equals(that.getSapCode());
        } else {
            if (o == null || getClass() != o.getClass()) return false;

            VSupplierDecorator that = (VSupplierDecorator) o;

            if (supplierCode != null ? !supplierCode.equals(that.supplierCode) : that.supplierCode != null) return false;
            return !(supplier != null ? !supplier.equals(that.supplier) : that.supplier != null);
        }
    }

    @Override
    public int hashCode() {
        int result = supplierCode != null ? supplierCode.hashCode() : 0;
        result = 31 * result + (supplier != null ? supplier.hashCode() : 0);
        return result;
    }
}
