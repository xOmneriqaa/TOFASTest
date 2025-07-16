package com.tofas.yky.model.losses.views;
/* T40127 @ 23.11.2015. */

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

@Entity
@Table(schema = "TFS_YKY", name = "V_INVOICE_READY_LOSSES")
public class VInvoiceReadyLoss extends VInvoicableLoss {

    @Transient
    private BigDecimal totalCost;

    @Transient
    private BigDecimal totalDiscardedPartCost;

    @Transient
    private BigDecimal totalTestCost;
    
    @Transient
    private BigDecimal totalOtherCost;

    @Transient
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTotalDiscardedPartCost() {
        return totalDiscardedPartCost;
    }

    public void setTotalDiscardedPartCost(BigDecimal totalDiscardedPartCost) {
        this.totalDiscardedPartCost = totalDiscardedPartCost;
    }

    public BigDecimal getTotalTestCost() {
        return totalTestCost;
    }

    public void setTotalTestCost(BigDecimal totalTestCost) {
        this.totalTestCost = totalTestCost;
    }

    public BigDecimal getTotalOtherCost() {
		return totalOtherCost;
	}

	public void setTotalOtherCost(BigDecimal totalOtherCost) {
		this.totalOtherCost = totalOtherCost;
	}

	public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
