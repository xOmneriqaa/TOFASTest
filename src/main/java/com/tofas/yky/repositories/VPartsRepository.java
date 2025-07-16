package com.tofas.yky.repositories;

import com.tofas.yky.model.decoratorbases.impl.VPart;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.Repository;

import javax.persistence.QueryHint;
import java.util.List;

public interface VPartsRepository extends Repository<VPart, String> {

    @QueryHints({
            @QueryHint(name = org.hibernate.annotations.QueryHints.FETCH_SIZE, value = "10000")
    })
    List<VPart> findAll();

    VPart findByDisegno(String disegno);

}
