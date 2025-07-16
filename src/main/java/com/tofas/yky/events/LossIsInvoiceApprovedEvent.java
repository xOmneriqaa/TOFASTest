package com.tofas.yky.events;
/* T40127 @ 24.10.2015. */

public class LossIsInvoiceApprovedEvent extends AbstractLossEvent {

    public LossIsInvoiceApprovedEvent(Long lossId) {
        super(lossId);
    }
}
