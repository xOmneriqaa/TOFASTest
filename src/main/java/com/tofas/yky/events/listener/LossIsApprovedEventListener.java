package com.tofas.yky.events.listener;
/* T40127 @ 21.10.2015. */

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.tofas.yky.enums.LossState;
import com.tofas.yky.events.LossIsApprovedEvent;
import com.tofas.yky.events.SupplierHasInvalidEmailDefinitionsEvent;
import com.tofas.yky.model.additional.ISupplierReferencedLoss;
import com.tofas.yky.model.decoratorbases.ISupplier;
import com.tofas.yky.model.losses.Loss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LossIsApprovedEventListener extends AbstractMailSenderWithTemplateEventListener {


    @Autowired private EventBus eventBus;

    @Autowired
    public LossIsApprovedEventListener(EventBus eventBus) {
        eventBus.register(this);
    }

    @AllowConcurrentEvents
    @Subscribe
    public void onsLossIsApproved(LossIsApprovedEvent event) {

        Loss loss = getLoss(event);

        // if loss is supply chain, do not send email to
        if(loss.getStateChanges().stream().filter(sc -> sc.getLossState().equals(LossState.SUPP_CHAIN)).count() > 0) {
            return;
        }

        Map<String, Object> map = new HashMap<>();
        loss.putApprovedMailParams(map);

        if(loss instanceof ISupplierReferencedLoss) {
            ISupplier supplier = ((ISupplierReferencedLoss) loss).getSupplier();

            if(supplier.isExtraSerie()) {
                map.put("numOfDays", tfAppConstantsService.getNumOfDaysForExtraSerieSupplier());
            } else {
                map.put("numOfDays", tfAppConstantsService.getNumOfDaysForInternalSupplier());
            }
        }

        String subject = "TOFAŞ - " + loss.getId().toString() + " Numaralı Kayıp Kaydı Oluşturulmuştur";
        // TOFAŞ 351 Numaralı Kayıp Kaydı Oluşturulmuştur

        ISupplier supplier = getSupplier(loss);

        List<String> toAddresses = getSupplierEmailsOfLoss(loss);


        // check emails
        boolean emailsAreValid = toAddresses.size() > 0;
        for (String toAddress : toAddresses) {
            if(toAddress == null || toAddress.trim().length() == 0) {
                emailsAreValid = false;
            }
        }

        if(!emailsAreValid) {
            eventBus.post(new SupplierHasInvalidEmailDefinitionsEvent(supplier, getDisegno(loss)));
        } else {
            sendMail(loss, toAddresses, Collections.<String>emptyList(), subject, "layout/email/loss_approved", map);
        }
    }



}
