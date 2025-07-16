package com.tofas.yky.events;
/* T40127 @ 22.10.2015. */

import com.tofas.yky.model.losses.Loss;

public class LossIsWaitingForApprovalEvent extends AbstractLossEvent {

    public LossIsWaitingForApprovalEvent(Loss loss) {
        super(loss.getId());
    }
}
