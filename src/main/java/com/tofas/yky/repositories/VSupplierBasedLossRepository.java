package com.tofas.yky.repositories;
/* T40127 @ 19.11.2015. */

import com.tofas.yky.enums.LossType;
import com.tofas.yky.model.losses.views.VSupplierBasedLoss;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VSupplierBasedLossRepository extends JpaRepository<VSupplierBasedLoss, Long> {

    List<VSupplierBasedLoss> findBySupplierCodeAndLossType(String supplierCode, LossType lossType);

}
