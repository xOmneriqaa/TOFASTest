package com.tofas.yky.events.listener;
/* T40127 @ 25.10.2015. */

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.tofas.yky.events.LossIsBeenObjectedEvent;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.repositories.CommonLossRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class LossIsBeenObjectedEventListener extends AbstractMailSenderWithTemplateEventListener {

    @Autowired
    CommonLossRepository commonLossRepository;

    @Autowired
    public LossIsBeenObjectedEventListener(EventBus eventBus) {
        eventBus.register(this);
    }

    @AllowConcurrentEvents
    @Subscribe
    public void onsLossIsBeenObjected(LossIsBeenObjectedEvent event) {
        Loss loss = getLoss(event);
        //TODO responsibles'a kayidi acan T'li user maili de eklenmeli,ona da mail gitmeli
        Set<String> responsibles = loss.getCurrentObjection().getObjectionType().getResponsibles();
        List<String> emails = commonLossRepository.getEmailsOfUsers(new ArrayList<>(responsibles));

        String insertedUser = loss.getCurrentObjection().getInsertedBy();
        List<String> ccUsers = Arrays.asList(commonLossRepository.getEmailOfUser(insertedUser));

        // add inserted user to the cc of email
        String emailOfInsertedUser = commonLossRepository.getEmailOfUser(loss.getInsertedBy());
        if(emailOfInsertedUser != null) {
            ccUsers.add(emailOfInsertedUser);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("objectionType", loss.getCurrentObjection().getObjectionType().getName());
        params.put("objectionDesc", loss.getCurrentObjection().getObjectionDescription());
        params.put("id", loss.getId());

        String subject = "TOFAŞ - " + loss.getId().toString() + " Numaralı Kayba İtiraz Edilmiştir";
        // TOFAŞ - 351 Numaralı Kayba İtiraz Edilmiştir

        sendMail(loss, emails, ccUsers, subject, "layout/email/loss_objected", params);
    }

}
