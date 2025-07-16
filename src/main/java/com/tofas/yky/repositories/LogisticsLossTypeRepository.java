package com.tofas.yky.repositories;

import com.tofas.core.common.repository.base.TfBaseRepository;
import com.tofas.yky.model.losses.logistics.LogisticsLossType;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by T40127 on 11.10.2015.
 */
public interface LogisticsLossTypeRepository extends TfBaseRepository<LogisticsLossType, Long> {

    @Query("Select w from LogisticsLossType w where w.status = 1 order by w.name")
    List<LogisticsLossType> findAllActiveOrdered();

}
