package com.tofas.yky.repositories;

import com.tofas.core.common.repository.base.TfBaseRepository;
import com.tofas.yky.model.losses.logistics.LogisticsAcceptencePoint;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LogisticsAcceptencePointRepository extends TfBaseRepository<LogisticsAcceptencePoint, Long> {

    @Query("Select w from LogisticsAcceptencePoint w where w.status = 1 order by w.name")
    List<LogisticsAcceptencePoint> findAllActiveOrdered();

}