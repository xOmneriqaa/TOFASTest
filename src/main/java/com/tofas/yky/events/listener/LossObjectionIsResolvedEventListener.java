package com.tofas.yky.events.listener;
/* T40127 @ 25.10.2015. */

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.tofas.yky.enums.LossType;
import com.tofas.yky.events.LossObjectionIsResolvedEvent;
import com.tofas.yky.model.VSupplierEMail;
import com.tofas.yky.model.decoratorbases.ISupplier;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.repositories.CommonLossRepository;
import com.tofas.yky.repositories.VSupplierEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
public class LossObjectionIsResolvedEventListener extends AbstractMailSenderWithTemplateEventListener {

    @Autowired
    CommonLossRepository commonLossRepository;

    @Resource
    VSupplierEmailRepository vSupplierEmailRepository;

    @Autowired
    public LossObjectionIsResolvedEventListener(EventBus eventBus) {
        eventBus.register(this);
    }

    @AllowConcurrentEvents
    @Subscribe
    public void onsLossObjectionIsResolved(LossObjectionIsResolvedEvent event) {
        Loss loss = getLoss(event);
        List<String> ccAddress = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();
        params.put("id", loss.getId());
        params.put("objectionDesc", loss.getFirstObjection().getObjectionDescription());
        params.put("objectionResultDesc", loss.getCurrentObjection().getObjectionResultDescription());

        String objectionOwner = loss.getFirstObjection().getInsertedBy();
        String objectionOwnerEmail = commonLossRepository.getEmailOfUser(objectionOwner);

        String objectionResolver = loss.getCurrentObjection().getUpdatedBy();
        String objectionResolverEmail = commonLossRepository.getEmailOfUser(objectionResolver);
        ccAddress.add(objectionResolverEmail);

        ISupplier supplier = getSupplier(loss);
        if(supplier != null) {
            List<VSupplierEMail> supplierEMails = vSupplierEmailRepository.findBySupplierSapCodeAndResponsibleType(supplier.getSapCode(), LossType.QUALITY);
            if(supplierEMails != null && supplierEMails.size() > 0) {
                for (VSupplierEMail supplierEMail : supplierEMails) {
                    ccAddress.add(supplierEMail.getEmail());
                }
            }
        }

        String subject  = "TOFAŞ - " + loss.getId() + " Numaralı Kayıp İtirazı Sonuçlanmıştır";
        // TOFAŞ - 351 Numaralı Kayıp İtirazı Sonuçlanmıştır

        sendMail(loss, Arrays.asList(objectionOwnerEmail),
                ccAddress, subject, "layout/email/loss_objection_resolved", params);
    }

}
