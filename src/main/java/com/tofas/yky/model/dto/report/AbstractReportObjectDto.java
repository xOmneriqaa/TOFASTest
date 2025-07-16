package com.tofas.yky.model.dto.report;
/* T40127 @ 14.11.2015. */

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractReportObjectDto {

    protected BigDecimal discardedPartCost;

    protected BigDecimal qLabTestCost;
    
    protected BigDecimal otherCosts;

    protected Map<String, BigDecimal> durationDetails;

    protected Map<String, BigDecimal> costDetails;

    public AbstractReportObjectDto(List<String> activeWorkTypes) {
        this.durationDetails = new HashMap<>();
        this.costDetails = new HashMap<>();

        for (String activeWorkType : activeWorkTypes) {
            this.durationDetails.put(activeWorkType, BigDecimal.ZERO);
            this.costDetails.put(activeWorkType, BigDecimal.ZERO);
        }

        discardedPartCost = new BigDecimal(0D);
        qLabTestCost = new BigDecimal(0D);
        otherCosts = new BigDecimal(0D);
    }

    public BigDecimal getDiscardedPartCost() {
        return discardedPartCost;
    }

    public BigDecimal getqLabTestCost() {
        return qLabTestCost;
    }
    
    public BigDecimal getOtherCosts() {
        return otherCosts;
    }

    public Map<String, BigDecimal> getDurationDetails() {
        return durationDetails;
    }

    public Map<String, BigDecimal> getCostDetails() {
        return costDetails;
    }



    public BigDecimal getTotalCostOfWorkTypes() {
        BigDecimal totalCost = new BigDecimal(0D);

        for (BigDecimal cost : costDetails.values()) {
            totalCost = totalCost.add(cost);
        }

        return totalCost;
    }

    public BigDecimal getTotalCost() {
        BigDecimal totalCost = getTotalCostOfWorkTypes();

        totalCost = totalCost.add(this.discardedPartCost);
        totalCost = totalCost.add(this.qLabTestCost);
        totalCost=totalCost.add(this.otherCosts); 
        return totalCost;
    }

    public BigDecimal getTotalDuration() {
        BigDecimal totalDuration = new BigDecimal(0D);

        for (BigDecimal duration : durationDetails.values()) {
            totalDuration = totalDuration.add(duration);
        }

        return totalDuration;
    }

}
