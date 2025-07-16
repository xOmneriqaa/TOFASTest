package com.tofas.yky.events.scheduled;
/* t40127 @ 23.06.2016. */

import com.tofas.yky.enums.LossState;
import com.tofas.yky.enums.LossType;
import com.tofas.yky.model.dashboard.press.PressDashboardData;
import com.tofas.yky.model.decoratorbases.decorators.VSupplierDecorator;
import com.tofas.yky.model.decoratorbases.impl.VSupplier;
import com.tofas.yky.model.losses.press.PressLoss;
import com.tofas.yky.repositories.press.PressDashboardDataRepository;
import com.tofas.yky.repositories.press.PressLossRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PressDashboardDataScheduler extends  AbstractLoggableScheduledJob {

    private final PressDashboardDataRepository pressDashboardDataRepository;

    private final PressLossRepository pressLossRepository;

    @Autowired
    public PressDashboardDataScheduler(PressDashboardDataRepository pressDashboardDataRepository, PressLossRepository pressLossRepository) {
        super("Press Darshboard Data Scheduler");
        this.pressDashboardDataRepository = pressDashboardDataRepository;
        this.pressLossRepository = pressLossRepository;
    }

    @Scheduled(cron = "30 1 4 * * *")
    public void run(){
        if(!canRun()) return;

        logStartTime();

        doInTransaction();

        logEndTime();
    }

    @Transactional
    private void doInTransaction() {
        pressDashboardDataRepository.deleteAll();


        List<PressLoss> allActive = pressLossRepository
                .findByLossTypeAndStateChanges_LossStateInAndStatusOrderByLossDate(
                        LossType.PRESS, Collections.singletonList(LossState.PRESS_CLOSED), 1);

        Map<String, PressDashboardData> allDashboardData = new HashMap<>();

        for (PressLoss pressLoss : allActive) {
            Integer year = getYear(pressLoss.getLossDate());
            Optional<VSupplierDecorator> supplierOptional = Optional.ofNullable(pressLoss.getSupplier());
            String key = Integer.toString(year)
                    + "-" + supplierOptional
                    .orElseGet(() -> new VSupplierDecorator("", new VSupplier()))
                    .getSupplierCode();

            PressDashboardData pressDashboardData;

            if(allDashboardData.containsKey(key)) {
                pressDashboardData = allDashboardData.get(key);
            } else {
                pressDashboardData = new PressDashboardData();

                pressDashboardData.setId(key);
                pressDashboardData.setSupplierCode(supplierOptional.orElseGet(() -> new VSupplierDecorator("", new VSupplier()))
                        .getSupplierCode());
                pressDashboardData.setSupplierName(supplierOptional.orElseGet(() -> new VSupplierDecorator("", new VSupplier()))
                        .getName());
                pressDashboardData.setYear(year);
                pressDashboardData.setTotalCost(BigDecimal.ZERO);
                pressDashboardData.setSupplierPayment(BigDecimal.ZERO);

                allDashboardData.put(key, pressDashboardData);
            }

            pressDashboardData.setTotalCost(
                    pressDashboardData.getTotalCost().add(pressLoss.getTotalCost()));
            pressDashboardData.setSupplierPayment(
                    pressDashboardData.getSupplierPayment().add(pressLoss.getTotalCreditNoteAmount()));

        }

        pressDashboardDataRepository.save(allDashboardData.values());
    }

    private Integer getYear(Date date) {
        Calendar dateAsCal = Calendar.getInstance();
        dateAsCal.setTime(date);

        return dateAsCal.get(Calendar.YEAR);
    }


}
