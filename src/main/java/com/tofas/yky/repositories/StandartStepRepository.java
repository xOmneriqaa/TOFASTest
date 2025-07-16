package com.tofas.yky.repositories;

import com.tofas.core.common.repository.base.TfBaseRepository;
import com.tofas.yky.enums.ProductionSubLossType;
import com.tofas.yky.model.losses.production.duration.StandartStep;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StandartStepRepository extends TfBaseRepository<StandartStep, Long> {

    List<StandartStep> findByProductionSubLossTypesIn(ProductionSubLossType[] productionSubLossTypes);

    @Query("Select item from StandartStep item where item.status = 1 order by item.name")
    List<StandartStep> findAllActiveOrdered();

}
