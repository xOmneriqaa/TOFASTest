package com.tofas.yky.events.scheduled;

import com.tofas.yky.service.VPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// TODO cache temizleme scheduler job
@Component
public class ClearCacheScheduledJob extends AbstractLoggableScheduledJob {

    VPartService vPartService;

    @Autowired
    public ClearCacheScheduledJob(VPartService vPartService) {
        super("Cahce temizleme");
        this.vPartService=vPartService;
    }


    @Scheduled(cron = "0 0 3 * * ?")
    public void run(){

        logStartTime();
        vPartService.cleanPartsCahce();

        vPartService.listAllPartsStr();

        logEndTime();
    }
}