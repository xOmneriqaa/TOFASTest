package com.tofas.yky.repositories;
/* T40127 @ 19.10.2015. */

import com.tofas.core.common.repository.base.TfBaseRepository;
import com.tofas.yky.enums.ObjectionStatus;
import com.tofas.yky.model.losses.LossObjection;

import java.util.List;

public interface LossObjectionRepository extends TfBaseRepository<LossObjection, Long> {

    List<LossObjection> findByObjectionStatus(ObjectionStatus objectionStatus);

    List<LossObjection> findByObjectionStatusAndObjectionType_ResponsiblesIn(ObjectionStatus status, List<String> responsibles);

    Long countByObjectionStatus(ObjectionStatus status);

    Long countByObjectionStatusAndObjectionType_ResponsiblesIn(ObjectionStatus status, List<String> responsibles);

    Long countByLoss_id(Long id);

}
