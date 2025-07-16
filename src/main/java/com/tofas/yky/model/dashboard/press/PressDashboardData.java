package com.tofas.yky.model.dashboard.press;
/* t40127 @ 23.06.2016. */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_PRESS_DASHBOARD_DATA")
public class PressDashboardData {

    @Id
    private String id;

    @Column(name = "SUPPLIER_CODE")
    private String supplierCode;

    @Column(name = "SUPPLIER_NAME")
    private String supplierName;

    private Integer year;

    @Column(name = "TOTAL_COST")
    private BigDecimal totalCost;

    @Column(name = "SUPPLIER_PAYMENT")
    private BigDecimal supplierPayment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getSupplierPayment() {
        return supplierPayment;
    }

    public void setSupplierPayment(BigDecimal supplierPayment) {
        this.supplierPayment = supplierPayment;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "PressDashboardData{" +
                "id='" + id + '\'' +
                ", supplierCode='" + supplierCode + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", year=" + year +
                ", totalCost=" + totalCost +
                ", supplierPayment=" + supplierPayment +
                '}';
    }
}
