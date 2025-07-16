package com.tofas.yky.events.listener;
/* MND @ 26.10.2016. */

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.tofas.yky.events.DiscardedPartDoNotHaveBasePriceEvent;
import com.tofas.yky.model.additional.IDiscardedPartAddableLoss;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.model.losses.discards.DiscardedPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DiscardedPartDoNotHaveBasePriceEventListener extends AbstractMailSenderWithTemplateEventListener {

    @Autowired
    public DiscardedPartDoNotHaveBasePriceEventListener(EventBus eventBus) {
        eventBus.register(this);
    }

    @AllowConcurrentEvents
    @Subscribe
    public void run(DiscardedPartDoNotHaveBasePriceEvent event) {
        Loss loss = getLoss(event);

        Map<String, Object> params = new HashMap<>();

        List<String> toAddr = new ArrayList<>();
        toAddr.add(tfAppConstantsService.getKeyUserEmail());

        params.put("id", loss.getId());

        if(loss instanceof IDiscardedPartAddableLoss) {
            Set<DiscardedPart> discardedParts = ((IDiscardedPartAddableLoss) loss).getDiscardedParts();

            for (DiscardedPart discardedPart : discardedParts) {
                if(!discardedPart.hasBasePrice()) {
                    params.put("disegno", discardedPart.getPart().getDisegno());

                    sendMail(loss, toAddr, Collections.<String>emptyList(), "Iskarta Parça - Eksik Birim Fiyat",
                            "layout/email/discarded_part_has_no_price", params);
                }
            }
        }


    }

}
