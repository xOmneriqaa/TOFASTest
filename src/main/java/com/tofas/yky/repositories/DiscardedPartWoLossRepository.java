package com.tofas.yky.repositories;
/* t40127 @ 05.05.2016. */

import com.tofas.core.common.repository.base.TfBaseRepository;
import com.tofas.yky.model.losses.discards.DiscardedPartWoLoss;

import java.util.List;

public interface DiscardedPartWoLossRepository extends TfBaseRepository<DiscardedPartWoLoss, Long>,
    ILossInvoiceRepository<DiscardedPartWoLoss> {

}
