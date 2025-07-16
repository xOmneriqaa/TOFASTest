package com.tofas.yky.model.dto.report;
/* T40127 @ 12.11.2015. */

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tofas.yky.model.report.MainReportObject;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SupplierBasedReportObjectDto extends AbstractReportObjectDto {

    private String supplierCode;

    private String supplierName;

    @JsonIgnore
    private List<Long> lossIds;

    public SupplierBasedReportObjectDto(MainReportObject mainReportObject, List<String> activeWorkTypes) {
        super(activeWorkTypes);
        this.supplierCode = mainReportObject.getSupplierCode();
        this.supplierName = mainReportObject.getSupplierName();
        this.lossIds = new ArrayList<>();
    }

    public void add(MainReportObject mainReportObject) {

        for (DurationDetailDto durationDetail : mainReportObject.getDurationDetails()) {

            if(!this.durationDetails.containsKey(durationDetail.getWorkTypeName())) {
                this.durationDetails.put(durationDetail.getWorkTypeName(), BigDecimal.ZERO);
                this.costDetails.put(durationDetail.getWorkTypeName(), BigDecimal.ZERO);
            }

            this.durationDetails.put(durationDetail.getWorkTypeName(),
                    durationDetails.get(durationDetail.getWorkTypeName()).add(durationDetail.getDuration()));
            this.costDetails.put(durationDetail.getWorkTypeName(),
                    costDetails.get(durationDetail.getWorkTypeName()).add(durationDetail.getCost()));
        }

        this.lossIds.add(mainReportObject.getId());

        this.discardedPartCost = this.discardedPartCost.add(mainReportObject.getDiscardedPartCost());
        this.qLabTestCost = this.qLabTestCost.add(mainReportObject.getqLabTestCost());
    }


    @JsonGetter("lossIds")
    public String getLossIds() {
        return StringUtils.arrayToDelimitedString(lossIds.toArray(), ", ");
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

}
