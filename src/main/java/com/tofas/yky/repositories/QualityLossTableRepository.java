package com.tofas.yky.repositories;

import com.tofas.core.common.repository.base.TfBaseRepository;
import com.tofas.yky.model.losses.quality.QualityLossTable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface QualityLossTableRepository extends TfBaseRepository<QualityLossTable, Long> {

    @Query("Select item from QualityLossTable item where item.status = 1 order by item.name")
    List<QualityLossTable> findAllActiveOrdered();

}
