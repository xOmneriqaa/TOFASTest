package com.tofas.yky.events;
/* T40127 @ 22.10.2015. */

import com.tofas.yky.model.losses.Loss;

public class LossIsBeenObjectedEvent extends AbstractLossEvent {


    public LossIsBeenObjectedEvent(Loss loss) {
        super(loss.getId());
    }

}
