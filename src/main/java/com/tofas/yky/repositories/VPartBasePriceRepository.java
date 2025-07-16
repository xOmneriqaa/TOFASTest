package com.tofas.yky.repositories;
/* T40127 @ 15.10.2015. */

import com.tofas.yky.model.VPartBasePrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VPartBasePriceRepository extends JpaRepository<VPartBasePrice, String> {

    List<VPartBasePrice> findFirstByDisegnoOrderByPriceDateDesc(String disegno);

    List<VPartBasePrice> findByDisegno(String disegno);

    List<VPartBasePrice> findByDisegnoAndFirmCode(String disegno, String firmCode);

}
