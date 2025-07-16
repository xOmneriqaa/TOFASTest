package com.tofas.yky.controller.admin;

import com.tofas.core.restful.controllers.TfBaseRestCrudController;
import com.tofas.yky.model.Model;
import com.tofas.yky.repositories.ModelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/admin/api/models")
public class ModelCrudController extends TfBaseRestCrudController<Model, String> {

    private @Resource
    ModelRepository modelRepository;

    @Override
    protected JpaRepository<Model, String> getRepository() {
        return modelRepository;
    }

    @Override
    protected Model getUpdatedEntity(Model object, String id) {
        Model dbobj = modelRepository.findOne(id);
        dbobj.setName(object.getName());
        dbobj.setCode(object.getCode());
        dbobj.setStatusBool(object.getStatusBool());

        return dbobj;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Model add(@RequestBody @Valid Model object) {
        return getRepository().save(object);
    }

}
