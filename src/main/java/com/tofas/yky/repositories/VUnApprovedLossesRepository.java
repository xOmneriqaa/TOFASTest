package com.tofas.yky.repositories;
/* t40127 @ 11.05.2016. */

import com.tofas.yky.model.losses.views.VUnApprovedLosses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VUnApprovedLossesRepository extends JpaRepository<VUnApprovedLosses, String> {

    List<VUnApprovedLosses> findByLossIdInAndLossSourceIn(List<Long> lostIds, List<String> lossSources);

}
