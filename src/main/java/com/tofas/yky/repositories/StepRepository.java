package com.tofas.yky.repositories;


import com.tofas.core.common.repository.base.TfBaseRepository;
import com.tofas.yky.enums.ProductionSubLossType;
import com.tofas.yky.model.losses.production.duration.Step;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StepRepository extends TfBaseRepository<Step, Long> {


    List<Step> findByProductionSubLossTypesIn(ProductionSubLossType[] productionSubLossTypes);

    @Query("Select item from Step item where item.status = 1 order by item.name")
    List<Step> findAllActiveOrdered();

}
