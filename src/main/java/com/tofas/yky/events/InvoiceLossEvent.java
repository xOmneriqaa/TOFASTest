package com.tofas.yky.events;
/* t40127 @ 12.05.2016. */

public class InvoiceLossEvent extends AbstractLossEvent {

    public InvoiceLossEvent(Long invoiceReadyLossId) {
        super(invoiceReadyLossId);
    }
}
