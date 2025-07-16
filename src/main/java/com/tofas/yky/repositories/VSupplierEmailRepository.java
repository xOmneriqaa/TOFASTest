package com.tofas.yky.repositories;
/* T40127 @ 01.11.2015. */

import com.tofas.yky.enums.LossType;
import com.tofas.yky.model.VSupplierEMail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VSupplierEmailRepository extends JpaRepository<VSupplierEMail, Long> {

    List<VSupplierEMail> findBySupplierSapCode(String supplierSapCode);

    List<VSupplierEMail> findBySupplierSapCodeAndResponsibleType(String supplierSapCode, LossType responsibleType);

}
