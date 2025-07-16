package com.tofas.yky.model.dashboard;
/* T40127 @ 26.11.2015. */

import com.tofas.yky.enums.dashboard.DashboardParamType;
import com.tofas.yky.enums.dashboard.DashboardPeriodType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_DASHBOARD_DATA_CACHED")
public class DashboardDataCached implements Serializable {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "PERIOD_TYPE")
    private DashboardPeriodType periodType;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "PARAM_TYPE")
    private DashboardParamType paramType;

    @Id
    @Column(name = "PARAM_NAME")
    private String paramName;

    @Id
    @Column(name = "PARAM_SUB_NAME")
    private String paramSubName;

    @Column(name = "PARAM_VALUE")
    private BigDecimal paramValue;

    public DashboardPeriodType getPeriodType() {
        return periodType;
    }

    public void setPeriodType(DashboardPeriodType periodType) {
        this.periodType = periodType;
    }

    public DashboardParamType getParamType() {
        return paramType;
    }

    public void setParamType(DashboardParamType paramType) {
        this.paramType = paramType;
    }

    public BigDecimal getParamValue() {
        return paramValue;
    }

    public void setParamValue(BigDecimal paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamSubName() {
        return paramSubName;
    }

    public void setParamSubName(String paramSubName) {
        this.paramSubName = paramSubName;
    }

    @Override
    public String toString() {
        return "DashboardDataCached{" +
                "periodType=" + periodType +
                ", paramType=" + paramType +
                ", paramName='" + paramName + '\'' +
                ", paramSubName='" + paramSubName + '\'' +
                ", paramValue=" + paramValue +
                '}';
    }
}
