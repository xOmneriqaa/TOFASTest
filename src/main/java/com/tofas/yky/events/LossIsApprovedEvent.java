package com.tofas.yky.events;
/* T40127 @ 21.10.2015. */

import com.tofas.yky.model.losses.Loss;

public class LossIsApprovedEvent extends AbstractLossEvent {

    public LossIsApprovedEvent(Loss loss) {
        super(loss.getId());
    }

}
