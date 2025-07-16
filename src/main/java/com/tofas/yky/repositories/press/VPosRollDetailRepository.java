package com.tofas.yky.repositories.press;
/* t40127 @ 25.04.2016. */

import com.tofas.yky.model.losses.press.pos.VPosRollDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VPosRollDetailRepository extends JpaRepository<VPosRollDetail, String> {

    public VPosRollDetail findByRollNo(String rolno);

    @Query("select i from VPosRollDetail i where i.rollNo=:rollNo ")
    VPosRollDetail vPosRollDetail(@Param("rollNo") String rollNo);



}
