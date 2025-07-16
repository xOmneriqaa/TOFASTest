package com.tofas.yky.events.listener;
/* T40127 @ 25.11.2015. */

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.tofas.yky.events.SendReminderMailOfApprovesLossesEvent;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.model.losses.views.VInvoiceApproveReadyLoss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SendReminderMailOfApprovesLossesEventListener extends AbstractMailSenderWithTemplateEventListener {

    @Autowired
    public SendReminderMailOfApprovesLossesEventListener(EventBus eventBus) {
        eventBus.register(this);
    }

    @AllowConcurrentEvents
    @Subscribe
    public void onSendReminderMailOfApprovesLossesEvent(SendReminderMailOfApprovesLossesEvent event) {
        Map<String, List<VInvoiceApproveReadyLoss>> supplierLosses = event.getSupplierLosses();

        for (String s : supplierLosses.keySet()) {
            Map<String, Object> params = new HashMap<>();
            params.put("losses", supplierLosses.get(s));

            String subject = "TOFAŞ - Firmanıza Ait Açık Kayıplar";

            List<String> supplierEmails = new ArrayList<>();
            List<String> openerEmails = new ArrayList<>();
            for (VInvoiceApproveReadyLoss vInvoiceApproveReadyLoss : supplierLosses.get(s)) {
                Loss loss = commonLossRepository.getLoss(vInvoiceApproveReadyLoss.getId());
                supplierEmails.addAll(getSupplierEmailsOfLoss(loss));

                String openerMail = commonLossRepository.getEmailOfUser(loss.getInsertedBy());
                openerEmails.add(openerMail);
            }

            sendMail(null, s, supplierEmails, openerEmails, subject, "layout/email/loss_reminder", params);
        }
    }

}
