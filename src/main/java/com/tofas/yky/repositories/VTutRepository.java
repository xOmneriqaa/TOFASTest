package com.tofas.yky.repositories;

import com.tofas.yky.model.decoratorbases.impl.VTut;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface VTutRepository extends Repository<VTut, String> {

    List<VTut> findAll();

    @Query("Select item from VTut item  order by item.orgMmm")
    List<VTut> findAllActiveOrdered();

    VTut findByOrgMmm(String orgMmm);

    List<VTut> findByOrgMmmIn(List<String> orgMmmList);

}
