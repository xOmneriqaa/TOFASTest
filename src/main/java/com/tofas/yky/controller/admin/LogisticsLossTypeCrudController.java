package com.tofas.yky.controller.admin;

import com.tofas.core.restful.controllers.TfBaseRestCrudController;
import com.tofas.yky.model.losses.logistics.LogisticsLossType;
import com.tofas.yky.repositories.LogisticsLossTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api/logistics-loss-types")
public class LogisticsLossTypeCrudController extends TfBaseRestCrudController<LogisticsLossType, Long> {

    @Autowired private LogisticsLossTypeRepository logisticsLossTypeRepository;

    @Override
    protected JpaRepository<LogisticsLossType, Long> getRepository() {
        return logisticsLossTypeRepository;
    }

    @Override
    protected LogisticsLossType getUpdatedEntity(LogisticsLossType object, Long id) {
        LogisticsLossType lt = logisticsLossTypeRepository.findOne(id);
        lt.setName(object.getName());
        lt.setStatusBool(object.getStatusBool());
        return lt;
    }
}
