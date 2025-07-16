package com.tofas.yky.repositories;
/* T40127 @ 23.11.2015. */

import com.tofas.yky.model.losses.views.VInvoiceReadyLoss;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VInvoiceReadyLossRepository extends JpaRepository<VInvoiceReadyLoss, Long> {

    List<VInvoiceReadyLoss> findByIdIn(List<Long> ids);

}
