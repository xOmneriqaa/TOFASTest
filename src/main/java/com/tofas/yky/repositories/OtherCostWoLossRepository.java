package com.tofas.yky.repositories;
/* t40127 @ 05.05.2016. */

import org.springframework.data.jpa.repository.JpaRepository;

import com.tofas.yky.model.losses.OtherCostWoLoss;

public interface OtherCostWoLossRepository extends JpaRepository<OtherCostWoLoss, Long>,
    ILossInvoiceRepository<OtherCostWoLoss> {

}
