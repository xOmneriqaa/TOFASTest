package com.tofas.yky.model.dto.listing;
/* T40127 @ 25.10.2015. */

import javax.persistence.Entity;

@Entity
public class LogisticsLossListingDto extends AbstractLossListingDto {

    protected String supplierCode;

    protected String supplierName;

    protected Integer qty;

    protected Long sqpNo;

    protected String disegno;

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Long getSqpNo() {
        return sqpNo;
    }

    public void setSqpNo(Long sqpNo) {
        this.sqpNo = sqpNo;
    }

    public String getDisegno() {
        return disegno;
    }

    public void setDisegno(String disegno) {
        this.disegno = disegno;
    }
}
