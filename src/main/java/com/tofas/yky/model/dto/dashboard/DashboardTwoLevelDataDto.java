package com.tofas.yky.model.dto.dashboard;

import java.math.BigDecimal;

/**
 * Created by t40127 on 29.12.2016.
 */
public class DashboardTwoLevelDataDto {

    private String level1;
    private String level2;
    private BigDecimal total;

    public DashboardTwoLevelDataDto(String level1, String level2, BigDecimal total) {
        this.level1 = level1;
        this.level2 = level2;
        this.total = total;
    }

    public String getLevel1() {
        return level1;
    }

    public String getLevel2() {
        return level2;
    }

    public BigDecimal getTotal() {
        return total;
    }

}
