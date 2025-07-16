package com.tofas.yky.events.listener;
/* T40127 @ 23.11.2015. */

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.tofas.yky.events.LossIsInvoiceApprovedEvent;
import com.tofas.yky.model.losses.Loss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LossIsInvoiceApprovedEventListener extends AbstractMailSenderWithTemplateEventListener {

    @Autowired
    public LossIsInvoiceApprovedEventListener(EventBus eventBus) {
        eventBus.register(this);
    }

    @AllowConcurrentEvents
    @Subscribe
    public void onLossInvoiceApproved(LossIsInvoiceApprovedEvent event) {
        Loss loss = this.getLoss(event.getLossId());

        Map<String, Object> params = new HashMap<>();
        params.put("id", event.getLossId());

        List<String> toAddresses = this.getSupplierEmailsOfLoss(loss);

        List<String> ccAddresses = new ArrayList<>();
        ccAddresses.add(commonLossRepository.getEmailOfUser(loss.getInsertedBy()));
        if(loss.getCurrentObjection() != null) {
            ccAddresses.add(commonLossRepository.getEmailOfUser(loss.getCurrentObjection().getInsertedBy()));
        }

        String subject = "TOFAŞ - " + event.getLossId() + " Numaralı Kayıp Tarafınıza Faturalanmıştır";
        // TOFAŞ - 351 Numaralı Kayıp Tarafınıza Faturalanmıştır

        sendMail(loss, toAddresses, ccAddresses, subject, "layout/email/loss_invoiced", params);
    }

}
