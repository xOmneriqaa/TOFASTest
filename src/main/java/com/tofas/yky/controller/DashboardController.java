package com.tofas.yky.controller;
/* T40127 @ 26.11.2015. */

import com.tofas.yky.model.dashboard.DashboardDataCached;
import com.tofas.yky.service.report.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/dashboard")
@RestController
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @RequestMapping("/get-data/{period}/{param}")
    public List<DashboardDataCached> getData(@PathVariable("period") String dashboardPeriodType,
                                             @PathVariable("param") String dashboardParamType) {
        return dashboardService.getData(dashboardPeriodType, dashboardParamType);
    }

}
