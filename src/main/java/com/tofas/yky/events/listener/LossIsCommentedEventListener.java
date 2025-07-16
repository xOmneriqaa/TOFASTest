package com.tofas.yky.events.listener;
/* T40127 @ 25.10.2015. */

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.tofas.yky.events.LossIsCommentedEvent;
import com.tofas.yky.model.dto.LossCommentDto;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.model.losses.LossComment;
import com.tofas.yky.model.losses.LossObjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class LossIsCommentedEventListener extends AbstractMailSenderWithTemplateEventListener {

    @Autowired
    public LossIsCommentedEventListener(EventBus eventBus) {
        eventBus.register(this);
    }

    @AllowConcurrentEvents
    @Subscribe
    public void onsLossIsCommented(LossIsCommentedEvent event) {
        LossCommentDto dto = event.getComment();

        Map<String, Object> params = new HashMap<>();
        params.put("id", dto.getLossId());
        params.put("comment", dto.getMessage());

        String subject = "TOFAŞ - " + dto.getLossId() + " Numaralı Kayıp İçin İlave Bilgi Girişi";
        // 351 Numaralı Kayıp İçin İlave Bilgi Girişi

        Loss loss = getLoss(dto.getLossId());
        Set<String> relatedUsers = new HashSet<>();
        relatedUsers.add(loss.getInsertedBy());

        for (LossComment lossComment : loss.getComments()) {
            relatedUsers.add(lossComment.getInsertedBy());
        }

        for (LossObjection objection : loss.getObjections()) {
            relatedUsers.addAll(objection.getObjectionType().getResponsibles());
            relatedUsers.add(objection.getInsertedBy());
        }

        List<String> emails = commonLossRepository.getEmailsOfUsers(relatedUsers);



        sendMail(loss, emails, Collections.<String>emptyList(), subject, "layout/email/loss_commented", params);
    }

}
