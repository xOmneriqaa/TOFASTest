package com.tofas.yky.service.utility;
/* t40127 @ 12.05.2016. */

import com.google.common.eventbus.EventBus;
import com.tofas.yky.events.InvoiceLossEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class OneTimeLossInvoicer {

    @Autowired
    private EventBus eventBus;

    @PostConstruct
    public void init() {
            Long[] lostIds = {};

        for (Long lostId : lostIds) {
            eventBus.post(new InvoiceLossEvent(lostId));
        }
    }


}
