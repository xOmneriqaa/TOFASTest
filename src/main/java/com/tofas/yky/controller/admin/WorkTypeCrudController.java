package com.tofas.yky.controller.admin;

import com.tofas.core.restful.controllers.TfBaseRestCrudController;
import com.tofas.yky.model.losses.WorkType;
import com.tofas.yky.repositories.WorkTypeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/api/work-types")
public class WorkTypeCrudController extends TfBaseRestCrudController<WorkType, Long> {

    private @Resource
    WorkTypeRepository workTypeRepository;

    @Override
    protected JpaRepository<WorkType, Long> getRepository() {
        return workTypeRepository;
    }

    @Override
    protected WorkType getUpdatedEntity(WorkType object, Long id) {
        WorkType wrType = workTypeRepository.findOne(id);
        wrType.setName(object.getName());
        wrType.setWageInMinutes(object.getWageInMinutes());
        wrType.setStatusBool(object.getStatusBool());
        return wrType;
    }
}
