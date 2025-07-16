package com.tofas.yky.events;
/* T40127 @ 25.11.2015. */

import com.tofas.yky.model.losses.views.VInvoiceApproveReadyLoss;

import java.util.List;
import java.util.Map;

public class SendReminderMailOfApprovesLossesEvent {

    private Map<String, List<VInvoiceApproveReadyLoss>> supplierLosses;

    public SendReminderMailOfApprovesLossesEvent(Map<String, List<VInvoiceApproveReadyLoss>> supplierLosses) {
        this.supplierLosses = supplierLosses;
    }

    public Map<String, List<VInvoiceApproveReadyLoss>> getSupplierLosses() {
        return supplierLosses;
    }
}
