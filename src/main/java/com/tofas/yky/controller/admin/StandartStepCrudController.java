package com.tofas.yky.controller.admin;

import com.tofas.core.restful.controllers.TfBaseRestCrudController;
import com.tofas.yky.model.losses.production.duration.StandartStep;
import com.tofas.yky.repositories.StandartStepRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/api/standart-steps")
public class StandartStepCrudController extends TfBaseRestCrudController<StandartStep, Long> {

    private @Resource
    StandartStepRepository standartStepRepository;

    @Override
    protected JpaRepository<StandartStep, Long> getRepository() {
        return standartStepRepository;
    }

    @Override
    protected StandartStep getUpdatedEntity(StandartStep object, Long id) {
        StandartStep dbobj = standartStepRepository.findOne(id);
        dbobj.setName(object.getName());
        dbobj.setDuration(object.getDuration());
        dbobj.setProductionSubLossTypes(object.getProductionSubLossTypes());
        dbobj.setStatusBool(object.getStatusBool());
        return dbobj;
    }
}
