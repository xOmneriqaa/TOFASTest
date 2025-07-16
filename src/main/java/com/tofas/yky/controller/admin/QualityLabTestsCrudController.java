package com.tofas.yky.controller.admin;

import com.tofas.core.restful.controllers.TfBaseRestCrudController;
import com.tofas.yky.model.losses.qualitylab.QualityLabTest;
import com.tofas.yky.repositories.QualityLabTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api/quality-lab-tests")
public class QualityLabTestsCrudController extends TfBaseRestCrudController<QualityLabTest, Long> {

    @Autowired private QualityLabTestRepository qualityLabTestRepository;

    @Override
    protected JpaRepository<QualityLabTest, Long> getRepository() {
        return qualityLabTestRepository;
    }

    @Override
    protected QualityLabTest getUpdatedEntity(QualityLabTest object, Long id) {
        QualityLabTest ql = qualityLabTestRepository.findOne(id);

        ql.setName(object.getName());
        ql.setPrice(object.getPrice());
        ql.setStatusBool(object.getStatusBool());

        return ql;
    }
}
