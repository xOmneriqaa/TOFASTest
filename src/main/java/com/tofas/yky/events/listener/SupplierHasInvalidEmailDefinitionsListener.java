package com.tofas.yky.events.listener;
/* T40127 @ 01.11.2015. */

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.tofas.yky.events.SupplierHasInvalidEmailDefinitionsEvent;
import com.tofas.yky.repositories.CommonLossRepository;
import com.tofas.yky.service.TfAppConstantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SupplierHasInvalidEmailDefinitionsListener extends AbstractMailSenderWithTemplateEventListener {

    private @Autowired
    TfAppConstantsService tfAppConstantsService;

    private @Autowired
    CommonLossRepository commonLossRepository;

    @Autowired
    public SupplierHasInvalidEmailDefinitionsListener(EventBus eventBus) {
        eventBus.register(this);
    }

    @AllowConcurrentEvents
    @Subscribe
    public void onSupplierHasInvalidMail(SupplierHasInvalidEmailDefinitionsEvent event) {
        List<String> buyerEmails = commonLossRepository.getEmailsOfBuyers(event.getDisegno());

        Map<String, Object> params = new HashMap<>();
        params.put("supplierSapCode", event.getSupplier().getSapCode());
        params.put("supplierName", event.getSupplier().getName());

        String subject = "Firma Eksik Mail Tanımı";

        sendMail(null, buyerEmails, Arrays.asList(tfAppConstantsService.getKeyUserEmail()), subject, "layout/email/supplier_email_error", params);
    }
}
