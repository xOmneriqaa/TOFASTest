package com.tofas.yky.repositories;

import com.tofas.yky.model.VShiftCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by T40127 on 11.10.2015.
 */
public interface VShiftCodesRepository extends JpaRepository<VShiftCode, String> {

    List<VShiftCode> findByOrgMmm(String orgMmm);
}
