package com.tofas.yky.repositories;

import com.tofas.yky.model.losses.production.ProductionLoss;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by T40127 on 11.10.2015.
 */
public interface ProductionLossRepository extends LossRepository<ProductionLoss> {


    @Query("Select prd from ProductionLoss AS prd where prd.status > 0 and prd.id not in (:ids) and prd.lossSource.shortName in(:lossSources) and prd not in (select st.loss from LossStateChange AS st where st.lossState = 'APPROVED')")
    List<ProductionLoss> findAllUnApprovedLosses(@Param("lossSources") List<String> lossSources, @Param("ids") List<Long> ids);

    @Query(value = "Select l from ProductionLoss l where l.status > 0 and (select nvl(sum(com.completionQty), 0) from LossCompletion com where com.loss = l) < l.qty")
    List<ProductionLoss> findNonFinishedLosses();
    
//    //not working cuz there is no lossId area on the ProductionLoss obj.
//    @Query("Select prd from ProductionLoss AS prd where prd.lossId=:lossId")
//    ProductionLoss findProductionLossById(@Param("lossId") Long lossId);
}
