package com.tofas.yky.events.scheduled;
/* T40127 @ 25.11.2015. */

import com.google.common.eventbus.EventBus;
import com.tofas.yky.events.SendReminderMailOfApprovesLossesEvent;
import com.tofas.yky.model.losses.views.VInvoiceApproveReadyLoss;
import com.tofas.yky.repositories.CommonLossRepository;
import com.tofas.yky.repositories.LossObjectionRepository;
import com.tofas.yky.repositories.VInvoiceApproveReadyLossRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ApprovedEventsSheduledJob extends AbstractLoggableScheduledJob {

    private final VInvoiceApproveReadyLossRepository vInvoiceApproveReadyLossRepository;

    private final LossObjectionRepository lossObjectionRepository;

    private final EventBus eventBus;

    private final CommonLossRepository commonLossRepository;

    @Autowired
    public ApprovedEventsSheduledJob(VInvoiceApproveReadyLossRepository vInvoiceApproveReadyLossRepository, LossObjectionRepository lossObjectionRepository, EventBus eventBus, CommonLossRepository commonLossRepository) {
        super("Onayli Kayiplarin Hatirlatma Maillerini Gonder");
        this.vInvoiceApproveReadyLossRepository = vInvoiceApproveReadyLossRepository;
        this.lossObjectionRepository = lossObjectionRepository;
        this.eventBus = eventBus;
        this.commonLossRepository = commonLossRepository;
    }


    @Scheduled(cron = "0 30 8 * * *")
    public void run() {
        if(!canRun()) return;

        logStartTime();
        Map<String, List<VInvoiceApproveReadyLoss>> supplierLosses = new HashMap<>();

        List<VInvoiceApproveReadyLoss> approvedLosses = vInvoiceApproveReadyLossRepository.findAll();
        for (VInvoiceApproveReadyLoss approvedLoss : approvedLosses) {
            Long countOfObjections = lossObjectionRepository.countByLoss_id(approvedLoss.getId());

            if (countOfObjections == 0) {
                String supplierCode = approvedLoss.getSupplierCode();

                if (supplierCode != null) {
                    supplierLosses.computeIfAbsent(supplierCode, k -> new ArrayList<>())
                            .add(approvedLoss);
                }
            }

        }

        eventBus.post(new SendReminderMailOfApprovesLossesEvent(supplierLosses));

        logEndTime();
    }
}
