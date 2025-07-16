package com.tofas.yky.controller.admin;

import com.tofas.core.restful.controllers.TfBaseRestCrudController;
import com.tofas.yky.model.losses.production.duration.Step;
import com.tofas.yky.repositories.StepRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/api/steps")
public class StepCrudController extends TfBaseRestCrudController<Step, Long> {

    private @Resource
    StepRepository stepRepository;

    @Override
    protected JpaRepository getRepository() {
        return stepRepository;
    }

    @Override
    protected Step getUpdatedEntity(Step object, Long id) {
        Step dbobj = stepRepository.findOne(id);
        dbobj.setName(object.getName());
        dbobj.setProductionSubLossTypes(object.getProductionSubLossTypes());
        dbobj.setStatusBool(object.getStatusBool());

        return dbobj;
    }

}
