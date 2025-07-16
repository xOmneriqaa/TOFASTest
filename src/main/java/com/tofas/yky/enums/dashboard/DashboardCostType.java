package com.tofas.yky.enums.dashboard;
/* T40127 @ 27.11.2015. */

import java.util.ArrayList;
import java.util.List;

public enum DashboardCostType {

    WORK_TYPE, DISCARDED_PART, QLAB_TEST, OTHER_COST;

    public static List<DashboardCostType> getAllCostType() {
        return new ArrayList<DashboardCostType>(){{
            add(WORK_TYPE);
            add(DISCARDED_PART);
            add(QLAB_TEST);
            add(OTHER_COST);
        }};
    }

}
