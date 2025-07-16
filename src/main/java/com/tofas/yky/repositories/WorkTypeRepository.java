package com.tofas.yky.repositories;

import com.tofas.core.common.repository.base.TfBaseRepository;
import com.tofas.yky.model.losses.WorkType;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkTypeRepository extends TfBaseRepository<WorkType, Long> {

    @Query("Select w.name from WorkType w where w.status = 1 order by w.name")
    List<String> getActiveWorkTypeNames();

    @Query("Select w from WorkType w where w.status = 1 order by w.name")
    List<WorkType> findAllActiveOrdered();

}
