package com.tofas.yky.repositories;

import com.tofas.yky.model.decoratorbases.impl.VSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VSupplierRepository extends JpaRepository<VSupplier, Long> {

    VSupplier findBySapCode(String sapCode);

    VSupplier findByCode(Long firmCode);

    @Query(value = "SELECT * FROM V_SUPPLIERS S WHERE S.SUPPLIER_SAP_CODE IN (SELECT DS.SUPPLIER_SAP_CODE FROM V_DISEGNO_SUPPLIER DS WHERE DS.DISEGNO = :disegno)", nativeQuery = true)
    List<VSupplier> findByDisegno(@Param("disegno") String disegno);

    List<VSupplier> findBySapCodeIsNotNullOrderBySapCode();

    @Query("Select item from VSupplier item where item.sapCode is not null order by item.sapCode")
    List<VSupplier> findAllActiveOrdered();

    List<VSupplier> findBySheetFlagOrderBySapCode(String sheetFlag);

}
