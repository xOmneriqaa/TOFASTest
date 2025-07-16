package com.tofas.yky.repositories;
/* T40127 @ 15.11.2015. */

import com.tofas.yky.model.VOpenObjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VOpenObjectionRepository extends JpaRepository<VOpenObjection, Long> {

    Long countByRawResponsiblesLike(String responsible);

    List<VOpenObjection> findByRawResponsiblesLike(String responsible);

}
