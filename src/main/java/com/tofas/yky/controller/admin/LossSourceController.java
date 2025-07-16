package com.tofas.yky.controller.admin;

import com.tofas.core.restful.controllers.TfBaseRestCrudController;
import com.tofas.yky.model.losses.LossSource;
import com.tofas.yky.repositories.LossSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api/loss-source")
public class LossSourceController extends TfBaseRestCrudController<LossSource, Long> {

    @Autowired private LossSourceRepository lossSourceRepository;

    @Override
    protected JpaRepository<LossSource, Long> getRepository() {
        return lossSourceRepository;
    }

    @Override
    protected LossSource getUpdatedEntity(LossSource object, Long id) {
        LossSource ls = lossSourceRepository.findOne(id);
        ls.setName(object.getName());
        ls.setShortName(object.getShortName());
        ls.setStatusBool(object.getStatusBool());
        return ls;
    }
}
