package com.tofas.yky.events.listener;
/* T40127 @ 25.11.2015. */

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.tofas.yky.events.SendReminderMailToObjectionResponsiblesEvent;
import com.tofas.yky.model.additional.IPartReferencedLoss;
import com.tofas.yky.model.additional.ISupplierReferencedLoss;
import com.tofas.yky.model.decoratorbases.ISupplier;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.model.losses.production.ProductionLoss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Component
public class SendReminderMailToObjectionResponsiblesEventListener extends AbstractMailSenderWithTemplateEventListener {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public SendReminderMailToObjectionResponsiblesEventListener(EventBus eventBus) {
        eventBus.register(this);
    }

    @AllowConcurrentEvents
    @Subscribe
    public void onSendReminderMailToObjectionResponsibles(SendReminderMailToObjectionResponsiblesEvent event) {
        Loss loss = getLoss(event);


        Map<String, Object> params = new HashMap<>();
        params.put("id", loss.getId());
        params.put("lossDescription", loss.getDescription());

        String disegno = "";
        if (loss instanceof IPartReferencedLoss) {
            disegno = ((IPartReferencedLoss) loss).getPart().getDisegno();
        }
        params.put("disegno", disegno);

        if (loss instanceof ISupplierReferencedLoss) {
            ISupplier supplier = ((ISupplierReferencedLoss) loss).getSupplier();
            params.put("supplier", supplier.getSapCode() + "-" + supplier.getName());
        } else {
            params.put("supplier", "");
        }

        if (loss instanceof ProductionLoss) {
            params.put("productionUnit", loss.getLossType().toString() + "/"
                    + ((ProductionLoss) loss).getSubType().toString());
        } else {
            params.put("productionUnit", loss.getLossType().toString());
        }


        Object tableCodeObj = entityManager
                .createNativeQuery("select get_disegno_table_code(:disegno) from dual")
                .setParameter("disegno", disegno)
                .getSingleResult();

        String tableCode = tableCodeObj == null ? "" : tableCodeObj.toString();
        params.put("tableCode", tableCode);

        //TODO itiraz maili burada doluyor tfs_lib.vw_employees
        String insBy=loss.getInsBy();
        Set<String> responsibles = loss.getCurrentObjection().getObjectionType().getResponsibles();
        if(insBy!=null || !insBy.equals("anonymousUser") || !insBy.isEmpty()){
        	responsibles.add(insBy);
        }
        
        List<String> emails = commonLossRepository.getEmailsOfUsers(new ArrayList<>(responsibles));

        String subject = "Kayba Ait Cevaplanmamış İtirazlar Bulunmaktadır [" + loss.getId() + "]";

        sendMail(loss, emails, Collections.emptyList(),
                subject, "layout/email/na_objection_reminder", params);
    }
}
