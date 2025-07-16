package com.tofas.yky.model.dto.invoice;
/* T40127 @ 24.11.2015. */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tofas.yky.model.dto.VSupplierDto;

public class SupplierBasedInvoiceModelDto {

    private List<Long> lostIds;

    private VSupplierDto supplier;

    private BigDecimal totalQualityLabTestCost;

    private BigDecimal totalDiscardedPartCost;

    private BigDecimal totalWorkTypeCost;
    
    private BigDecimal totalOtherCost;

    public SupplierBasedInvoiceModelDto() {
        totalDiscardedPartCost = BigDecimal.ZERO;
        totalQualityLabTestCost = BigDecimal.ZERO;
        totalWorkTypeCost = BigDecimal.ZERO;
        totalOtherCost = BigDecimal.ZERO;
        lostIds = new ArrayList<>();
    }

    public BigDecimal getTotalCost() {
        BigDecimal totalCost = BigDecimal.ZERO;

        totalCost = totalCost.add(totalQualityLabTestCost);
        totalCost = totalCost.add(totalDiscardedPartCost);
        totalCost = totalCost.add(totalWorkTypeCost);
        totalCost=totalCost.add(totalOtherCost);
        return totalCost;
    }

    public String getLossIds() {
        StringBuilder buffer = new StringBuilder();

        for (Long lostId : lostIds) {
            buffer.append(lostId).append("-");
        }

        buffer.setLength(buffer.length() - 1);

        return buffer.toString();
    }

    public void addLoss(Long id) {
        this.lostIds.add(id);
    }

    public void addQualityLabTestCost(BigDecimal cost) {
        this.totalQualityLabTestCost = this.totalQualityLabTestCost.add(cost);
    }

    public void addDiscardedPartCost(BigDecimal cost) {
        this.totalDiscardedPartCost = this.totalDiscardedPartCost.add(cost);
    }

    public void addWorkTypeCost(BigDecimal cost) {
        this.totalWorkTypeCost = this.totalWorkTypeCost.add(cost);
    }
    
    public void addOtherCost(BigDecimal cost){
    	this.totalOtherCost=this.totalOtherCost.add(cost);
    }

    public List<Long> getLostIds() {
        return lostIds;
    }

    public void setLostIds(List<Long> lostIds) {
        this.lostIds = lostIds;
    }

    public VSupplierDto getSupplier() {
        return supplier;
    }

    public void setSupplier(VSupplierDto supplier) {
        this.supplier = supplier;
    }

    public BigDecimal getTotalQualityLabTestCost() {
        return totalQualityLabTestCost;
    }

    public void setTotalQualityLabTestCost(BigDecimal totalQualityLabTestCost) {
        this.totalQualityLabTestCost = totalQualityLabTestCost;
    }

    public BigDecimal getTotalDiscardedPartCost() {
        return totalDiscardedPartCost;
    }

    public void setTotalDiscardedPartCost(BigDecimal totalDiscardedPartCost) {
        this.totalDiscardedPartCost = totalDiscardedPartCost;
    }

    public BigDecimal getTotalWorkTypeCost() {
        return totalWorkTypeCost;
    }

    public void setTotalWorkTypeCost(BigDecimal totalWorkTypeCost) {
        this.totalWorkTypeCost = totalWorkTypeCost;
    }

	public BigDecimal getTotalOtherCost() {
		return totalOtherCost;
	}

	public void setTotalOtherCost(BigDecimal totalOtherCost) {
		this.totalOtherCost = totalOtherCost;
	}
}
