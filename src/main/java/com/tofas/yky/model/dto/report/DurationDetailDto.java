package com.tofas.yky.model.dto.report;
/* T40127 @ 11.11.2015. */

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DurationDetailDto {

    private String workTypeName;

    private BigDecimal duration;

    private BigDecimal cost;

    public static List<DurationDetailDto> generateDurationDetailDto(String durationDescription) {
        List<DurationDetailDto> durationDetails = new ArrayList<>();

        if(durationDescription != null && durationDescription.trim().length() > 0) {
            String[] durationDescs = durationDescription.trim().split(";");
            for(String durationDesc : durationDescs) {
                String[] durationDescDetail = durationDesc.split(":");

                if(durationDescDetail.length == 3) {
                    try {
                        durationDetails.add(new DurationDetailDto(durationDescDetail[0],
                                BigDecimal.valueOf(NumberFormat.getInstance(Locale.US).parse(durationDescDetail[1]).doubleValue()),
                                BigDecimal.valueOf(NumberFormat.getInstance(Locale.US).parse(durationDescDetail[2]).doubleValue())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        return durationDetails;
    }

    public DurationDetailDto(String workTypeName, BigDecimal duration, BigDecimal cost) {
        this.workTypeName = workTypeName;
        this.duration = duration;
        this.cost = cost;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
