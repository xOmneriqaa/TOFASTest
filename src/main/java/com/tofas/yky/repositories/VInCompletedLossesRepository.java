package com.tofas.yky.repositories;
/* t40127 @ 28.03.2016. */

import com.tofas.yky.model.losses.views.VInCompletedLosses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VInCompletedLossesRepository extends JpaRepository<VInCompletedLosses, Long> {

    @Query("Select i.id from VInCompletedLosses i")
    List<Long> findIds();

}
