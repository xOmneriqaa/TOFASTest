package com.tofas.yky.model.dto;
/* t40127 @ 26.04.2016. */

import com.tofas.yky.model.decoratorbases.impl.VSupplier;

public class VSupplierDto {

    private String sapCode;

    private String name;

    public VSupplierDto() { }

    public VSupplierDto(String sapCode, String name) {
        this.sapCode = sapCode;
        this.name = name;
    }

    public String getSapCode() {
        return sapCode;
    }

    public void setSapCode(String sapCode) {
        this.sapCode = sapCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
