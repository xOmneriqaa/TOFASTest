package com.tofas.yky.events.scheduled;
/* T40127 @ 25.11.2015. */

import com.google.common.eventbus.EventBus;
import com.tofas.yky.events.SendReminderMailToObjectionResponsiblesEvent;
import com.tofas.yky.model.VOpenObjection;
import com.tofas.yky.repositories.VOpenObjectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class OpenObjectionsReminderScheduledJob extends AbstractLoggableScheduledJob {

    private final VOpenObjectionRepository vOpenObjectionRepository;

    private final EventBus eventBus;

    @Autowired
    public OpenObjectionsReminderScheduledJob(VOpenObjectionRepository vOpenObjectionRepository, EventBus eventBus) {
        super("Acik Itiraz Kontrolu");
        this.vOpenObjectionRepository = vOpenObjectionRepository;
        this.eventBus = eventBus;
    }

    @Scheduled(cron = "0 30 7 * * *")
    public void run() {
        if(!canRun()) return;

        logStartTime();
        List<VOpenObjection> openObjections = vOpenObjectionRepository.findAll();

        for (VOpenObjection openObjection : openObjections) {
            eventBus.post(new SendReminderMailToObjectionResponsiblesEvent(openObjection.getLossId()));
        }
        logEndTime();
    }
}
