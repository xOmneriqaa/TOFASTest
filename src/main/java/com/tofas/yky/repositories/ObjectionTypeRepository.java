package com.tofas.yky.repositories;

import com.tofas.core.common.repository.base.TfBaseRepository;
import com.tofas.yky.model.losses.ObjectionType;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ObjectionTypeRepository extends TfBaseRepository<ObjectionType, Long> {

    @Query("Select w from ObjectionType w where w.status = 1 order by w.name")
    List<ObjectionType> findAllActiveOrdered();

}
