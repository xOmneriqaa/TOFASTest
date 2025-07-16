package com.tofas.yky.controller.admin;

import com.tofas.core.restful.controllers.TfBaseRestCrudController;
import com.tofas.yky.model.losses.logistics.LogisticsAcceptencePoint;
import com.tofas.yky.repositories.LogisticsAcceptencePointRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/api/logistics-acceptence-points")
public class LogisticsAcceptencePointCrudController extends TfBaseRestCrudController<LogisticsAcceptencePoint, Long> {

    @Resource private LogisticsAcceptencePointRepository logisticsAcceptencePointRepository;

    @Override
    protected JpaRepository<LogisticsAcceptencePoint, Long> getRepository() {
        return logisticsAcceptencePointRepository;
    }

    @Override
    protected LogisticsAcceptencePoint getUpdatedEntity(LogisticsAcceptencePoint object, Long id) {
        LogisticsAcceptencePoint lp = logisticsAcceptencePointRepository.findOne(id);
        lp.setName(object.getName());
        lp.setStatusBool(object.getStatusBool());
        return lp;
    }
}
