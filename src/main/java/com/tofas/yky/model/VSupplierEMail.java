package com.tofas.yky.model;
/* T40127 @ 01.11.2015. */

import com.tofas.yky.enums.LossType;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(schema = "TFS_YKY", name = "V_SUPPLIER_EMAILS")
public class VSupplierEMail {

    @Id
    private Long id;

    @Column(name = "SUPPLIER_SAP_CODE")
    private String supplierSapCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "RES_TYPE")
    private LossType responsibleType;

    @Column(name = "RES_NAME")
    private String name;

    @Column(name = "RES_EMAIL")
    private String email;

    public LossType getResponsibleType() {
        return responsibleType;
    }

    public void setResponsibleType(LossType responsibleType) {
        this.responsibleType = responsibleType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSupplierSapCode() {
        return supplierSapCode;
    }

    public void setSupplierSapCode(String supplierSapCode) {
        this.supplierSapCode = supplierSapCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VSupplierEMail that = (VSupplierEMail) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "VSupplierEMail{" +
                "id=" + id +
                ", supplierSapCode='" + supplierSapCode + '\'' +
                ", responsibleType=" + responsibleType +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
