package com.tofas.yky.events.listener;
/* t40127 @ 12.05.2016. */

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.tofas.yky.enums.LossState;
import com.tofas.yky.events.InvoiceLossEvent;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.repositories.CommonLossRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceLossEventListener {

    @Autowired
    private CommonLossRepository commonLossRepository;

    @Autowired
    public InvoiceLossEventListener(EventBus eventBus) {
        eventBus.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void invoiceLossEventHandler(InvoiceLossEvent invoiceLossEvent) {
        Loss loss = commonLossRepository.getLoss(invoiceLossEvent.getLossId());

        if(loss!= null && loss.getStateChanges().last().getLossState().equals(LossState.INVOICE_APPROVED)) {
            loss.invoiceLoss();
            commonLossRepository.save(loss);
        }
    }
}
