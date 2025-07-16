package com.tofas.yky.repositories;
/* t40127 @ 05.05.2016. */

import com.tofas.yky.model.losses.qualitylab.QualityLabLossTestWoLoss;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QualityLabLossTestWoLossRepository extends JpaRepository<QualityLabLossTestWoLoss, Long>
    , ILossInvoiceRepository<QualityLabLossTestWoLoss> {

}
