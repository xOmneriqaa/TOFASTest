package com.tofas.yky.repositories;
/* t40127 @ 05.05.2016. */

import com.tofas.yky.model.losses.production.duration.LossStepDurationWoLoss;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LossStepDurationWoLossRepository extends JpaRepository<LossStepDurationWoLoss, Long>,
    ILossInvoiceRepository<LossStepDurationWoLoss> {

}
