package com.tofas.yky.controller.admin;

import com.tofas.core.restful.controllers.TfBaseRestCrudController;
import com.tofas.yky.model.losses.quality.QualityLossTable;
import com.tofas.yky.repositories.QualityLossTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/api/quality-loss-tables")
public class QualityLossTablesCrudController extends TfBaseRestCrudController<QualityLossTable, Long> {

    private @Resource
    QualityLossTableRepository qualityLossTableRepository;

    @Override
    protected JpaRepository<QualityLossTable, Long> getRepository() {
        return qualityLossTableRepository;
    }

    @Override
    protected QualityLossTable getUpdatedEntity(QualityLossTable object, Long id) {
        QualityLossTable dbObj = qualityLossTableRepository.findOne(id);
        dbObj.setName(object.getName());
        dbObj.setStatusBool(object.getStatusBool());
        return dbObj;
    }
}
