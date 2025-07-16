package com.tofas.yky.controller.admin;
/* T40127 @ 13.10.2015. */

import com.tofas.core.restful.controllers.TfBaseRestCrudController;
import com.tofas.yky.model.losses.ObjectionType;
import com.tofas.yky.repositories.CommonLossRepository;
import com.tofas.yky.repositories.ObjectionTypeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/admin/api/objection-types")
public class ObjectionTypeCrudController extends TfBaseRestCrudController<ObjectionType, Long> {

    @Resource
    private ObjectionTypeRepository objectionTypeRepository;

    @Resource private CommonLossRepository commonLossRepository;



    @Override
    protected JpaRepository<ObjectionType, Long> getRepository() {
        return objectionTypeRepository;
    }

    @Override
    protected ObjectionType getUpdatedEntity(ObjectionType object, Long id) {
        ObjectionType db = objectionTypeRepository.findOne(id);
        db.setName(object.getName());
        db.setResponsibles(object.getResponsibles());
        db.setStatusBool(object.getStatusBool());

        return db;
    }

    @Override
    public List<ObjectionType> findAll() {
        List<ObjectionType> objectionTypes = super.findAll();

        // fill emails of responsibles
        for (ObjectionType objectionType : objectionTypes) {
            List<String> emails = commonLossRepository.getEmailsOfUsers(objectionType.getResponsibles());
            objectionType.setResponsibleEmails(new HashSet<>(emails));
        }

        return objectionTypes;
    }
}
