package com.tofas.yky.events.listener;
/* T40127 @ 23.11.2015. */

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.tofas.yky.events.LossIsCanceledDueToNotAnsweredObjectionEvent;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.repositories.CommonLossRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LossIsCanceledDueToNotAnsweredObjectionEventListener extends AbstractMailSenderWithTemplateEventListener {

    @Resource
    CommonLossRepository commonLossRepository;

    @Autowired
    public LossIsCanceledDueToNotAnsweredObjectionEventListener(EventBus eventBus) {
        eventBus.register(this);
    }

    @AllowConcurrentEvents
    @Subscribe
    public void onLossIsCanceledDueToNotAnsweredObjection(LossIsCanceledDueToNotAnsweredObjectionEvent event) {
        Map<String, Object> params = new HashMap<>();

        params.put("id", event.getLossId());
        String subject = "TOFAŞ - " + event.getLossId() + " Numaralı Kayıp İptal Edilmiştir";
        // TOFAŞ - 351 Numaralı Kayıp İptal Edilmiştir

        Loss loss = commonLossRepository.getLoss(event.getLossId());

        List<String> toUsers = new ArrayList<>();
        List<String> ccUsers = new ArrayList<>();

        toUsers.add(loss.getInsertedBy());
        if(loss.getCurrentObjection() != null) {
            toUsers.add(loss.getCurrentObjection().getInsertedBy());
            ccUsers.addAll(loss.getCurrentObjection().getObjectionType().getResponsibles());
        }

        toUsers = commonLossRepository.getEmailsOfUsers(toUsers);
        ccUsers = commonLossRepository.getEmailsOfUsers(ccUsers);


        sendMail(loss, toUsers, ccUsers,
                subject, "layout/email/loss_is_canceled_due_to_na_objection", params);
    }

}
