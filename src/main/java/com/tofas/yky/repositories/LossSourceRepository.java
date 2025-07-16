package com.tofas.yky.repositories;

import com.tofas.core.common.repository.base.TfBaseRepository;
import com.tofas.yky.model.losses.LossSource;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by T40127 on 11.10.2015.
 */
public interface LossSourceRepository extends TfBaseRepository<LossSource, Long> {

    @Query("Select w from LossSource w where w.status = 1 order by w.name")
    List<LossSource> findAllActiveOrdered();

}
