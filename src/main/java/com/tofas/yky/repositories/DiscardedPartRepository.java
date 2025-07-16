package com.tofas.yky.repositories;
/* T40127 @ 16.10.2015. */

import com.tofas.core.common.repository.base.TfBaseRepository;
import com.tofas.yky.model.losses.discards.DiscardedPart;

import java.util.List;

public interface DiscardedPartRepository extends TfBaseRepository<DiscardedPart, Long> {

    List<DiscardedPart> findByBasePriceIsNull();

    Long countByDiscardedPartVoucherNo(Long discardedVoucherNo);

    List<DiscardedPart> findByPartDisegno(String disegno);

}
