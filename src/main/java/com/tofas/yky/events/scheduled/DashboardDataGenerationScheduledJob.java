package com.tofas.yky.events.scheduled;
/* T40127 @ 26.11.2015. */

import com.tofas.yky.service.report.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DashboardDataGenerationScheduledJob extends AbstractLoggableScheduledJob {

    private final DashboardService dashboardService;

    @Autowired
    public DashboardDataGenerationScheduledJob(DashboardService dashboardService) {
        super("Dashboard Veri Guncelleme");
        this.dashboardService = dashboardService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void run(){
        if(!canRun()) return;

        logStartTime();

        dashboardService.generateCachedData();

        logEndTime();
    }

}
