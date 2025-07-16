package com.tofas.yky.repositories;


import com.tofas.core.common.repository.base.TfBaseRepository;
import com.tofas.yky.model.Model;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModelRepository extends TfBaseRepository<Model, String> {

    @Query("Select item from Model item where item.status = 1 order by item.code")
    List<Model> findAllActiveOrdered();

}
