package com.tofas.yky.repositories;

import com.tofas.core.common.repository.base.TfBaseRepository;
import com.tofas.yky.model.losses.qualitylab.QualityLabTest;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface QualityLabTestRepository extends TfBaseRepository<QualityLabTest, Long> {

    @Query("Select w from QualityLabTest w where w.status = 1 order by w.name")
    List<QualityLabTest> findAllActiveOrdered();

}
